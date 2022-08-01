package com.jetpack.compose.learning.maps.place.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.maps.android.PolyUtil
import com.jetpack.compose.learning.maps.CarMarkerUIState
import com.jetpack.compose.learning.maps.DirectionRoute
import com.jetpack.compose.learning.maps.DirectionStep
import com.jetpack.compose.learning.maps.NavigationViewerUIState
import com.jetpack.compose.learning.maps.getRotation
import com.jetpack.compose.learning.maps.place.model.DirectionResponse
import com.jetpack.compose.learning.maps.place.model.PlaceResult
import com.jetpack.compose.learning.maps.place.model.Resource
import com.jetpack.compose.learning.maps.place.model.getResultAccordingToStatus
import com.jetpack.compose.learning.maps.place.retrofit.MapRetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class NavigationViewModel : ViewModel() {
    private val _startLocationResult = MutableStateFlow<PlaceResult?>(null)
    private val _endLocationResult = MutableStateFlow<PlaceResult?>(null)
    val startLocationResult: StateFlow<PlaceResult?>
        get() = _startLocationResult
    val endLocationResult: StateFlow<PlaceResult?>
        get() = _endLocationResult

    private val _isStartLocationSelection = MutableStateFlow(false)
    private val _showNavigationViewer = MutableStateFlow(false)
    val showNavigationViewer: StateFlow<Boolean>
        get() = _showNavigationViewer

    private val _uiState = MutableStateFlow(NavigationViewerUIState())
    val uiState: StateFlow<NavigationViewerUIState>
        get() = _uiState
    private val _carMarkerUIState = MutableStateFlow(CarMarkerUIState())
    val carMarkerUIState: StateFlow<CarMarkerUIState> = _carMarkerUIState

    private val _isCarAnimationRunning = MutableStateFlow(false)
    val isCarRunning: StateFlow<Boolean>
        get() = _isCarAnimationRunning

    private var routeIndex = 0
    private var carAnimationJob: Job? = null

    init {
        viewModelScope.launch {
            combine(_startLocationResult, _endLocationResult) { start, end ->
                start != null && end != null
            }.filter { it }
                .flatMapLatest {
                    getDirections()
                }
                .map {
                    it.toRouteItems()
                }
                .flowOn(Dispatchers.Main)
                .collect {
                    _uiState.value = it
                }
        }
    }

    /**
     * Selects whether the place picker is for start or end location
     */
    fun setStartLocationSelection(isStartLocation: Boolean) {
        _isStartLocationSelection.value = isStartLocation
    }

    /**
     * Selects the location for start and end.
     * If both location are not null then navigation viewer is displayed.
     */
    fun selectPlace(placeResult: PlaceResult) {
        if (_isStartLocationSelection.value) {
            _startLocationResult.value = placeResult
        } else {
            _endLocationResult.value = placeResult
        }
        _showNavigationViewer.value =
            _startLocationResult.value != null && _endLocationResult.value != null
    }

    fun showAllRoutes(showAllRoute: Boolean) {
        stopCarAnimation()
        _uiState.value = _uiState.value.copy(showAllRoutes = showAllRoute)
    }

    fun showNextRoute() {
        stopCarAnimation()
        var newIndex = routeIndex + 1
        val allRouteSize = _uiState.value.allRoutes.size
        if (newIndex >= allRouteSize) {
            newIndex = 0
        }
        _uiState.value =
            _uiState.value.copy(currentRoute = _uiState.value.allRoutes[newIndex])
        routeIndex = newIndex
    }

    fun showPreviousRoute() {
        stopCarAnimation()
        var newIndex = routeIndex - 1
        val allRouteSize = _uiState.value.allRoutes.size
        if (newIndex < 0) {
            newIndex = allRouteSize - 1
        }
        _uiState.value =
            _uiState.value.copy(currentRoute = _uiState.value.allRoutes[newIndex])
        routeIndex = newIndex
    }

    fun changeOverViewPolyline() {
        val showOverviewPolyline = !_uiState.value.showOverviewPolyline
        _uiState.value =
            _uiState.value.copy(showOverviewPolyline = showOverviewPolyline)
    }

    fun changeCarAnimation() {
        if (_isCarAnimationRunning.value) {
            stopCarAnimation()
        } else {
            _isCarAnimationRunning.value = true
            startCarAnimation()
        }
    }

    private fun stopCarAnimation() {
        if (_isCarAnimationRunning.value) {
            _isCarAnimationRunning.value = false
            carAnimationJob?.cancel()
        }
        hideCarMarker()
    }

    private fun hideCarMarker() {
        viewModelScope.launch {
            val carMarkerUiState = _carMarkerUIState.value
            if (carMarkerUiState.visible) {
                _carMarkerUIState.value = carMarkerUiState.copy(visible = false)
            }
        }
    }

    private fun startCarAnimation() {
        var currentPositionIndex = 0
        val latLngList =
            if (_uiState.value.showOverviewPolyline) {
                _uiState.value.currentRoute.overviewPoints
            } else {
                _uiState.value.currentRoute.directionSteps.flatMap { it.polylinePoints }
            }
        carAnimationJob = viewModelScope.launch {
            repeat(latLngList.size) {
                val previousPosition =
                    if (currentPositionIndex - 1 >= 0) {
                        latLngList[currentPositionIndex - 1]
                    } else {
                        null
                    }
                val nextPosition =
                    if (currentPositionIndex >= latLngList.size - 1) {
                        null
                    } else {
                        latLngList[currentPositionIndex + 1]
                    }
                val currentPosition = latLngList[currentPositionIndex]
                val bearing = if (previousPosition != null) {
                    getRotation(previousPosition, currentPosition)
                } else {
                    0f
                }
                _carMarkerUIState.value =
                    CarMarkerUIState(currentPosition, previousPosition, nextPosition, bearing)
                if (currentPositionIndex != 0) {
                    delay(1100)
                }
                currentPositionIndex++
            }
        }
        carAnimationJob?.invokeOnCompletion {
            if (it == null) {
                _isCarAnimationRunning.value = false
            }
        }
    }

    /**
     * Direction API call. It fetches the direction from maps api.
     */
    private fun getDirections(): Flow<Resource<DirectionResponse>> {
        val originPlaceId = _startLocationResult.value?.place_id
        val destinationPlaceId = _endLocationResult.value?.place_id
        return flow {
            val response = MapRetrofitClient.getInstance().apiService.getDirections(
                "place_id:$originPlaceId",
                "place_id:$destinationPlaceId"
            )
            emit(getResultAccordingToStatus(response, response.status))
        }.onStart {
            emit(Resource.Loading)
        }.catch {
            emit(Resource.Error("Error Occurred, Try again later", it))
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Convert the API result into ui state.
     */
    private fun Resource<DirectionResponse>.toRouteItems(): NavigationViewerUIState {
        return when (this) {
            is Resource.Error -> _uiState.value.copy(
                showLoading = false,
                errorText = message
            )
            is Resource.Loading -> _uiState.value.copy(showLoading = true)
            is Resource.Success -> {
                if (data.routes.isNotEmpty()) {
                    val routeList = mutableListOf<DirectionRoute>()
                    data.routes.forEach { route ->
                        //From documentation- A route with no waypoints will contain exactly one leg within the legs array.
                        //So accessing the first index for the lag to get information
                        //https://developers.google.com/maps/documentation/directions/get-directions#DirectionsRoute
                        val legItem = route.legs[0]
                        val overViewLatLngPoints = PolyUtil.decode(route.overview_polyline.points)
                        val stepList = mutableListOf<DirectionStep>()
                        legItem.steps.forEach { stepsItem ->
                            val directionStep = DirectionStep(
                                stepsItem.distance?.text ?: "",
                                stepsItem.duration.text,
                                stepsItem.start_location.getLatLng(),
                                stepsItem.end_location.getLatLng(),
                                stepsItem.html_instructions,
                                PolyUtil.decode(stepsItem.polyline.points)
                            )
                            stepList.add(directionStep)
                        }
                        val directionRoute = DirectionRoute(
                            overviewPolylineBound = route.getPlaceLatLngBound(),
                            overviewPoints = overViewLatLngPoints,
                            distanceText = legItem.distance?.text ?: "",
                            durationText = legItem.duration?.text ?: "",
                            startAddress = legItem.start_address,
                            endAddress = legItem.end_address,
                            startLocation = legItem.start_location.getLatLng(),
                            endLocation = legItem.end_location.getLatLng(),
                            directionSteps = stepList
                        )
                        routeList.add(directionRoute)
                    }
                    if (routeIndex >= routeList.size) {
                        routeIndex = 0
                    }
                    val currentRoute = routeIndex
                    _uiState.value.copy(
                        showLoading = false,
                        currentRoute = routeList[currentRoute],
                        allRoutes = routeList
                    )
                } else {
                    _uiState.value.copy(
                        showLoading = false,
                        errorText = "Failed to load navigation"
                    )
                }
            }
        }
    }
}
