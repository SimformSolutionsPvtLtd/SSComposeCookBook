package com.jetpack.compose.learning.maps.place.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Polymer
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.maps.CarMarkerUIState
import com.jetpack.compose.learning.maps.DirectionRoute
import com.jetpack.compose.learning.maps.GoogleMapNavigationViewerOptions
import com.jetpack.compose.learning.maps.NavigationViewerUIState
import com.jetpack.compose.learning.maps.animateBound
import com.jetpack.compose.learning.maps.animatePosition
import com.jetpack.compose.learning.maps.getRandomColor
import com.jetpack.compose.learning.maps.place.viewmodel.NavigationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Navigation Viewer in compose.
 * You can pick the start and end location and the viewer will display the route between those two places.
 * You can switch between different routes via toolbar.
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MapsNavigationViewer(viewModel: NavigationViewModel = viewModel()) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    val isCarAnimationRunning by viewModel.isCarRunning.collectAsState()

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetContent = {
            GoogleMapNavigationViewerOptions(
                uiState,
                viewModel::showAllRoutes,
                viewModel::showNextRoute,
                viewModel::showPreviousRoute
            )
        },
    ) {
        Scaffold(topBar = {
            TopAppBar(title = { Text("Navigation Viewer") },
                actions = {
                    if (uiState.allRoutes.size > 1) {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (modalBottomSheetState.isVisible) {
                                    modalBottomSheetState.hide()
                                } else {
                                    modalBottomSheetState.show()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Tune, contentDescription = "Navigation Options")
                        }
                    }
                    if (!uiState.showLoading) {
                        IconButton(onClick = {
                            viewModel.changeOverViewPolyline()
                        }) {
                            Icon(
                                Icons.Outlined.Polymer,
                                contentDescription = "Change Overview Polyline"
                            )
                        }
                    }
                })
        }, floatingActionButton = {
            if (!uiState.showAllRoutes && !uiState.showLoading && uiState.errorText.isEmpty()) {
                FloatingActionButton(onClick = { viewModel.changeCarAnimation() }) {
                    Icon(
                        if (isCarAnimationRunning) Icons.Filled.Stop
                        else Icons.Filled.PlayArrow,
                        contentDescription = "Car Animation Icon"
                    )
                }
            }
        }) {
            Box(modifier = Modifier.padding(it), contentAlignment = Alignment.Center) {
                MapsExample(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    viewModel = viewModel
                )
                if (uiState.errorText.isNotEmpty()) {
                    Text(
                        uiState.errorText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp)
                            .background(MaterialTheme.colors.background)
                            .padding(8.dp, 4.dp)
                    )
                }
                AnimatedVisibility(
                    visible = uiState.showLoading,
                    modifier = Modifier
                        .matchParentSize()
                        .background(MaterialTheme.colors.background),
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    CircularProgressIndicator(modifier = Modifier.wrapContentSize())
                }
            }
        }
    }
}

@Composable
fun MapsExample(
    uiState: NavigationViewerUIState,
    modifier: Modifier = Modifier,
    viewModel: NavigationViewModel = viewModel()
) {
    val cameraPositionState = rememberCameraPositionState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState) {
        delay(100L)
        if (uiState.currentRoute.overviewPolylineBound != null) {
            cameraPositionState.animateBound(uiState.currentRoute.overviewPolylineBound, 150)
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        contentPadding = PaddingValues(bottom = 60.dp),
    ) {
        if (!uiState.showLoading) {
            if (uiState.showAllRoutes) {
                uiState.allRoutes.forEach { route ->
                    PolylineRoute(route, uiState.showOverviewPolyline)
                }
            } else {
                PolylineRoute(uiState.currentRoute, uiState.showOverviewPolyline)
            }
            CarMarker(viewModel) { markerState ->
                coroutineScope.launch {
                    val previousPosition = markerState.previousPosition
                    val nextPosition = markerState.nextPosition
                    val currentPosition = markerState.currentPosition
                    if (previousPosition != null && nextPosition != null) {
                        cameraPositionState.animateBound(previousPosition, nextPosition)
                    } else if (currentPosition != null) {
                        cameraPositionState.animatePosition(
                            target = currentPosition,
                            zoom = 20f,
                            bearing = markerState.rotation
                        )
                    }
                }
            }
        }
    }
}

/**
 * Displays a route.
 * If overview is selected then entire polyline is displayed.
 * If overview is not selected then steps are displayed with different color polyline for each.
 */
@Composable
fun PolylineRoute(route: DirectionRoute, showOverViewPolyline: Boolean = true) {
    if (showOverViewPolyline) {
        MarkerComponent(
            route.startLocation,
            route.endLocation,
            route.startAddress,
            route.endAddress
        )
        PolylineComponent(points = route.overviewPoints, color = getRandomColor())
    } else {
        route.directionSteps.forEach { step ->
            MarkerComponent(step.startLocation, step.endLocation)
            PolylineComponent(points = step.polylinePoints, color = getRandomColor())
        }
    }
}

/**
 * Displays a marker on for start and end location.
 * If overview is selected then only two marker will displayed for entire route.
 * If overview is not selected then for each steps start and end location will be displayed.
 */
@Composable
fun MarkerComponent(
    startPosition: LatLng,
    endPosition: LatLng,
    startSnippet: String? = null,
    endSnippet: String? = null
) {
    Marker(
        state = MarkerState(startPosition),
        snippet = startSnippet
    )
    Marker(
        state = MarkerState(endPosition),
        snippet = endSnippet
    )
}

/**
 * Actual component to draw polyline.
 */
@Composable
fun PolylineComponent(
    points: List<LatLng>,
    color: Color
) {
    Polyline(
        points = points,
        color = color,
        endCap = RoundCap(),
        jointType = JointType.ROUND,
        startCap = RoundCap(),
        width = 20f
    )
}

/**
 * Displays car marker running on map.
 */
@Composable
private fun CarMarker(
    viewModel: NavigationViewModel = viewModel(),
    cameraPositionUpdate: (CarMarkerUIState) -> Unit,
) {
    var carPosition by remember { mutableStateOf<LatLng?>(null) }
    val animatable = remember { Animatable(0f) }
    val markerState by viewModel.carMarkerUIState.collectAsState()

    LaunchedEffect(markerState) {
        val currentPosition = markerState.currentPosition
        val previousPosition = markerState.previousPosition
        cameraPositionUpdate(markerState)
        if (currentPosition != null && previousPosition != null && markerState.visible) {
            //Set the duration less then what is emitting by viewmodel. So that the animation can be finished and reset.
            //You can also emit the value from viewmodel manually once the animation is finished.
            val animationResult =
                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                ) {
                    carPosition =
                        LatLng(
                            value * currentPosition.latitude + (1 - value) * previousPosition.latitude,
                            value * currentPosition.longitude + (1 - value) * previousPosition.longitude
                        )
                }
            if (animationResult.endReason == AnimationEndReason.Finished) {
                animatable.snapTo(0f)
            }
        } else if (currentPosition != null && previousPosition == null && markerState.visible) {
            carPosition = currentPosition
        }
    }

    if (carPosition != null) {
        Marker(
            state = MarkerState(carPosition!!),
            icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_car_marker),
            rotation = markerState.rotation,
            anchor = Offset(0.5f, 0.5f),
            flat = true,
            visible = markerState.visible
        )
    }
}
