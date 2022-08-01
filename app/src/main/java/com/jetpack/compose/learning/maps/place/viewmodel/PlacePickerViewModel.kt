package com.jetpack.compose.learning.maps.place.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpack.compose.learning.maps.PlacePickerUIState
import com.jetpack.compose.learning.maps.place.model.AutoCompleteItem
import com.jetpack.compose.learning.maps.place.model.PlaceDetail
import com.jetpack.compose.learning.maps.place.model.Resource
import com.jetpack.compose.learning.maps.place.model.getResultAccordingToStatus
import com.jetpack.compose.learning.maps.place.retrofit.MapRetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class PlacePickerViewModel : ViewModel() {
    private val _searchString = MutableStateFlow("")
    private val _searchResultState =
        MutableStateFlow<Resource<List<AutoCompleteItem>>>(Resource.Loading)
    private val _selectedPlaceId = MutableStateFlow("")
    private val _placeDetailState =
        MutableStateFlow<Resource<PlaceDetail>>(Resource.Loading)
    private val _uiState = MutableStateFlow(PlacePickerUIState())

    val searchString: StateFlow<String>
        get() = _searchString
    val searchResultState: StateFlow<Resource<List<AutoCompleteItem>>>
        get() = _searchResultState
    val placeResultState: StateFlow<Resource<PlaceDetail>>
        get() = _placeDetailState
    val uiState: StateFlow<PlacePickerUIState>
        get() = _uiState

    init {
        viewModelScope.launch {
            _searchString.debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    searchForPlace(query)
                }.flowOn(Dispatchers.Main)
                .collect {
                    _searchResultState.value = it
                }
        }
        viewModelScope.launch {
            _selectedPlaceId.flatMapLatest {
                getPlaceDetail(it)
            }.flowOn(Dispatchers.Main)
                .collect {
                    _placeDetailState.value = it
                }
        }
    }

    fun searchText(searchString: String) {
        _searchString.value = searchString
    }

    /**
     * Search API call. Uses google maps API to auto complete the places search.
     */
    private suspend fun searchForPlace(query: String): Flow<Resource<List<AutoCompleteItem>>> {
        return flow {
            if (query.isNotEmpty() && query.isNotBlank()) {
                val autoCompletePlaces =
                    MapRetrofitClient.getInstance().apiService.getAutoCompletePlaces(query.trim())
                emit(
                    getResultAccordingToStatus(
                        autoCompletePlaces.predictions,
                        autoCompletePlaces.status
                    )
                )
            } else {
                _uiState.value = _uiState.value.copy(showSearchResult = false)
            }
        }.onStart {
            _uiState.value = _uiState.value.copy(showSearchResult = true)
            emit(Resource.Loading)
        }.catch {
            emit(Resource.Error("Error Occurred, Try again later", it))
        }.flowOn(Dispatchers.IO)
    }

    fun selectPlace(placeId: String) {
        _selectedPlaceId.value = placeId
    }

    /**
     * Place detail API call. Uses google maps API to give details regarding a place.
     */
    private suspend fun getPlaceDetail(placeId: String): Flow<Resource<PlaceDetail>> {
        return flow {
            if (placeId.isEmpty()) {
                _uiState.value = _uiState.value.copy(showPlaceDetail = false)
            } else {
                val placeResponse =
                    MapRetrofitClient.getInstance().apiService.getPlaceDetail(placeId.trim())
                if (placeResponse.result != null) {
                    emit(getResultAccordingToStatus(placeResponse.result, placeResponse.status))
                } else {
                    emit(Resource.Error("Cannot fetch the place"))
                }
            }
        }.onStart {
            emit(Resource.Loading)
            _uiState.value =
                _uiState.value.copy(showPlaceDetail = true, showSearchResult = false)
        }.catch {
            emit(Resource.Error("Error Occurred, Try again later", it))
        }.flowOn(Dispatchers.IO)
    }
}
