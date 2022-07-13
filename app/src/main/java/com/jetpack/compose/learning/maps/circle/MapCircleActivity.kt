package com.jetpack.compose.learning.maps.circle

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.compose.learning.maps.CircleMapUIState
import com.jetpack.compose.learning.maps.GoogleMapCircleOptions
import com.jetpack.compose.learning.maps.MapScreen
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Circle shape Example.
 * It includes all the properties and customization for the Circle() composable function.
 * All the options can be modified via clicking toolbar icon.
 */
class MapCircleActivity : ComponentActivity() {
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
    fun MainContent(mapsViewModel: MapCircleViewModel = viewModel()) {
        var showLoading by remember { mutableStateOf(true) }
        val uiState by mapsViewModel.state.collectAsState()
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 17f)
        }

        MapScreen(
            title = "Circle",
            onBackPressed = { onBackPressed() },
            showLoading = showLoading,
            sheetContent = {
                GoogleMapCircleOptions(
                    uiState,
                    mapsViewModel::setMarkerDraggable,
                    mapsViewModel::setMarkerVisible,
                    mapsViewModel::setFillColor,
                    mapsViewModel::setFillColorAlpha,
                    mapsViewModel::setRadius,
                    mapsViewModel::setClickable,
                    mapsViewModel::setStrokeColor,
                    mapsViewModel::setPatternType,
                    mapsViewModel::setVisible,
                    mapsViewModel::setStrokeWidth,
                    mapsViewModel::setStrokeAlpha
                )
            },
        ) {
            Text(
                "Tap on map to add marker. A Circle will be added on the marker's position. Change circle options from toolbar.",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            MapsExample(uiState, cameraPositionState) { showLoading = false }
        }
    }

    @Composable
    fun MapsExample(
        uiState: CircleMapUIState,
        cameraPositionState: CameraPositionState,
        onMapLoaded: () -> Unit
    ) {
        val markerStateList =
            remember { mutableStateListOf(MarkerState(currentMarkerLatLong)) }
        val context = LocalContext.current

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState,
            onMapLoaded = onMapLoaded,
            onMapClick = {
                val isPositionExist = markerStateList.any { state -> state.position == it }
                if (!isPositionExist) {
                    markerStateList.add(MarkerState(it))
                }
            }
        ) {
            markerStateList.forEach {
                Marker(it, draggable = uiState.isMarkerDraggable, visible = uiState.isMarkerVisible)
                Circle(
                    center = it.position,
                    radius = uiState.radius.toDouble(),
                    fillColor = uiState.fillColor.copy(alpha = uiState.fillColorAlpha),
                    clickable = uiState.clickable,
                    strokeColor = uiState.strokeColor.copy(alpha = uiState.strokeColorAlpha),
                    strokePattern = uiState.getPatternType(),
                    visible = uiState.visible,
                    strokeWidth = uiState.strokeWidth,
                    onClick = {
                        Toast.makeText(context, "On Circle Click", Toast.LENGTH_LONG).show()
                    })
            }
        }
    }
}
