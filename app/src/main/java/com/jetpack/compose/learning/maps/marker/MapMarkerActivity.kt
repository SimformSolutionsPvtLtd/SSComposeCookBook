package com.jetpack.compose.learning.maps.marker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.compose.learning.maps.GoogleMapMarkerOptions
import com.jetpack.compose.learning.maps.MapScreen
import com.jetpack.compose.learning.maps.MarkerMapUIState
import com.jetpack.compose.learning.maps.MarkerStyle
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Marker Example.
 * It includes all the properties and customization for the Marker and InfoWindow composable functions.
 * All the options can be modified via clicking toolbar icon.
 */
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

    @Composable
    fun MainContent(mapsViewModel: MapMarkerViewModel = viewModel()) {
        var showLoading by remember { mutableStateOf(true) }
        val uiState by mapsViewModel.state.collectAsState()
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 17f)
        }

        MapScreen(
            title = "Markers",
            onBackPressed = { onBackPressed() },
            showLoading = showLoading,
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
            Text(
                "Long press on map to add marker. Change marker options from toolbar.",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            MapsExample(uiState, cameraPositionState) { showLoading = false }
        }
    }

    @Composable
    fun MapsExample(
        uiState: MarkerMapUIState,
        cameraPositionState: CameraPositionState,
        onMapLoaded: () -> Unit
    ) {
        val markerStateList =
            remember { mutableStateListOf(Pair(uiState.markerStyle, MarkerState(currentMarkerLatLong))) }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState,
            onMapLoaded = onMapLoaded,
            onMapLongClick = {
                val isPositionExist = markerStateList.any { state -> state.second.position == it }
                if (!isPositionExist) {
                    markerStateList.add(Pair(uiState.markerStyle, MarkerState(it)))
                }
            }
        ) {
            markerStateList.forEach {
                MarkerComponent(uiState, it)
            }
        }
    }

    @Composable
    fun MarkerComponent(uiState: MarkerMapUIState, markerDetails: Pair<MarkerStyle, MarkerState>) {
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
                    ).show()
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
