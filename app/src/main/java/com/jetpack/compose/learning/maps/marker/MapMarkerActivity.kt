package com.jetpack.compose.learning.maps.marker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import com.jetpack.compose.learning.maps.GoogleMapMarkerOptions
import com.jetpack.compose.learning.maps.MarkerMapUiState
import com.jetpack.compose.learning.maps.MarkerStyle
import com.jetpack.compose.learning.maps.statueOfLiberty
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

class MapMarkerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainContent()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
    @Composable
    fun MainContent(mapsViewModel: MapMarkerViewModel = viewModel()) {

        val modalBottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        var isMapLoaded by remember { mutableStateOf(false) }

        val uiState by mapsViewModel.state.collectAsState()

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(statueOfLiberty, 17f)
        }
        val coroutineScope = rememberCoroutineScope()

        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
            sheetContent = {
                GoogleMapMarkerOptions(
                    uiState,
                    mapsViewModel::changeMarkerType,
                    mapsViewModel::setMarkerIconStyle,
                    mapsViewModel::setMarkerColorHue,
                    mapsViewModel::changeMarkerAlpha,
                    mapsViewModel::setMarkerRotation,
                    mapsViewModel::setMarkerDraggable,
                    mapsViewModel::setMarkerFlat,
                    mapsViewModel::setMarkerVisible
                )
            },
        ) {
            Scaffold(topBar = {
                TopAppBar(
                    title = { Text("Markers") },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (modalBottomSheetState.isVisible)
                                    modalBottomSheetState.hide()
                                else
                                    modalBottomSheetState.show()
                            }
                        }) {
                            Icon(Icons.Filled.Tune, contentDescription = "Map UI Option")
                        }
                    }
                )
            }) {
                Box(modifier = Modifier.padding(it), contentAlignment = Alignment.Center) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            "Long press on map to add marker. Change marker options from toolbar.",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.subtitle1,
                            textAlign = TextAlign.Center
                        )
                        MapsExample(uiState, cameraPositionState) { isMapLoaded = true }
                    }
                    AnimatedVisibility(
                        visible = !isMapLoaded,
                        modifier = Modifier
                            .matchParentSize()
                            .background(MaterialTheme.colors.background),
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .wrapContentSize()
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun MapsExample(
        uiState: MarkerMapUiState,
        cameraPositionState: CameraPositionState,
        onMapLoaded: () -> Unit
    ) {
        val markerStateList =
            remember { mutableStateListOf(Pair(uiState.markerStyle, MarkerState(statueOfLiberty))) }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState,
            onMapLoaded = onMapLoaded,
            onMapLongClick = {
                val anyPresent = markerStateList.any { state -> state.second.position == it }
                if (!anyPresent)
                    markerStateList.add(Pair(uiState.markerStyle, MarkerState(it)))
            }
        ) {
            markerStateList.forEach {
                MarkerComponent(uiState, it)
            }
        }
    }

    @Composable
    fun MarkerComponent(uiState: MarkerMapUiState, markerDetails: Pair<MarkerStyle, MarkerState>) {
        val context = LocalContext.current
        when (markerDetails.first) {
            MarkerStyle.DEFAULT -> Marker(
                state = markerDetails.second,
                alpha = uiState.alpha,
                draggable = uiState.draggable,
                flat = uiState.flat,
                icon = uiState.getMarkerIcon(context),
                rotation = uiState.rotation,
                title = "Your Marker Title",
                snippet = "Your Snippet",
                visible = uiState.visible,
                onInfoWindowClick = {
                    Toast.makeText(context, "Default info window click", Toast.LENGTH_LONG).show()
                },
                onInfoWindowLongClick = {
                    Toast.makeText(context, "Default info window long click", Toast.LENGTH_LONG)
                        .show()
                }
            )
            MarkerStyle.CUSTOM_INFO_WINDOW_CONTENT -> MarkerInfoWindowContent(
                state = markerDetails.second,
                alpha = uiState.alpha,
                draggable = uiState.draggable,
                flat = uiState.flat,
                icon = uiState.getMarkerIcon(context),
                rotation = uiState.rotation,
                visible = uiState.visible,
                onInfoWindowClick = {
                    Toast.makeText(context, "Custom content info window click", Toast.LENGTH_LONG)
                        .show()
                },
                onInfoWindowLongClick = {
                    Toast.makeText(
                        context,
                        "Custom content info window long click",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.Cyan, RoundedCornerShape(4.dp))
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Custom Info Window Content.\nYou can add any content here. The content will have the bubble background.\nAny click event inside info window will be handle by onInfoWindowClick")
                }
            }
            MarkerStyle.CUSTOM_INFO_WINDOW -> MarkerInfoWindow(
                state = markerDetails.second,
                alpha = uiState.alpha,
                draggable = uiState.draggable,
                flat = uiState.flat,
                icon = uiState.getMarkerIcon(context),
                rotation = uiState.rotation,
                visible = uiState.visible,
                onInfoWindowClick = {
                    Toast.makeText(context, "Custom info window click", Toast.LENGTH_LONG).show()
                },
                onInfoWindowLongClick = {
                    Toast.makeText(context, "Custom info window long click", Toast.LENGTH_LONG)
                        .show()
                }
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Entire Custom Info Window.\nYou can add any content here.\nAny click event inside info window will be handle by onInfoWindowClick")
                }
            }
        }
    }
}