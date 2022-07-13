package com.jetpack.compose.learning.maps.polyline

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
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.compose.learning.maps.GoogleMapPolylineOptions
import com.jetpack.compose.learning.maps.MapScreen
import com.jetpack.compose.learning.maps.PolylineMapUIState
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Polyline shape Example.
 * It includes all the properties and customization for the Polyline() composable function.
 * All the options can be modified via clicking toolbar icon.
 */
class MapPolylineActivity : ComponentActivity() {
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
    fun MainContent(mapsViewModel: MapPolylineViewModel = viewModel()) {
        var showLoading by remember { mutableStateOf(true) }
        val uiState by mapsViewModel.state.collectAsState()
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 17f)
        }

        MapScreen(
            title = "Polyline",
            onBackPressed = { onBackPressed() },
            showLoading = showLoading,
            sheetContent = {
                GoogleMapPolylineOptions(
                    uiState,
                    mapsViewModel::setMarkerDraggable,
                    mapsViewModel::setMarkerVisible,
                    mapsViewModel::setPolylineClickable,
                    mapsViewModel::setPolylineColor,
                    mapsViewModel::setPolylineStartCap,
                    mapsViewModel::setPolylineEndCap,
                    mapsViewModel::setPolylineGeodesic,
                    mapsViewModel::setPolylineJointType,
                    mapsViewModel::setPolylinePatternType,
                    mapsViewModel::setPolyLineVisible,
                    mapsViewModel::setPolyLineWidth,
                    mapsViewModel::setStrokeAlpha
                )
            },
        ) {
            Text(
                "Tap on map to add marker. A Polyline will be added between markers. Change polyline options from toolbar.",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            MapsExample(uiState, cameraPositionState) { showLoading = false }
        }
    }

    @Composable
    fun MapsExample(
        uiState: PolylineMapUIState,
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
            }
            Polyline(
                points = markerStateList.map { it.position },
                clickable = uiState.clickable,
                color = uiState.strokeColor.copy(alpha = uiState.strokeColorAlpha),
                endCap = uiState.getPolylineEndCap(),
                geodesic = uiState.geodesic,
                jointType = uiState.jointType.type,
                pattern = uiState.getPatternType(),
                startCap = uiState.getPolylineStartCap(),
                visible = uiState.visible,
                width = uiState.strokeWidth,
                onClick = {
                    Toast.makeText(context, "On Polyline Click", Toast.LENGTH_LONG).show()
                })
        }
    }
}
