package com.jetpack.compose.learning.maps.overlay

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
import com.google.maps.android.compose.GroundOverlay
import com.google.maps.android.compose.GroundOverlayPosition
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.compose.learning.maps.GoogleMapGroundOverlayOptions
import com.jetpack.compose.learning.maps.GroundOverlayMapUIState
import com.jetpack.compose.learning.maps.MapScreen
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Ground Overlay example.
 * All the options can be modified via clicking toolbar icon.
 */
class MapGroundOverlayActivity : ComponentActivity() {

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
    fun MainContent(mapsViewModel: MapGroundOverlayViewModel = viewModel()) {
        var showLoading by remember { mutableStateOf(true) }
        val uiState by mapsViewModel.state.collectAsState()
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 15f)
        }

        MapScreen(
            title = "Ground Overlay",
            onBackPressed = { onBackPressed() },
            showLoading = showLoading,
            sheetContent = {
                GoogleMapGroundOverlayOptions(
                    uiState,
                    mapsViewModel::nextImage,
                    mapsViewModel::previousImage,
                    mapsViewModel::setBearing,
                    mapsViewModel::setTransparency,
                    mapsViewModel::setClickable,
                    mapsViewModel::setVisible
                )
            },
        ) {
            Text(
                "A ground overlay is an image which is bound to a position. Change overlay options from toolbar.",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            MapsExample(uiState, cameraPositionState) { showLoading = false }
        }
    }

    @Composable
    fun MapsExample(
        uiState: GroundOverlayMapUIState,
        cameraPositionState: CameraPositionState,
        onMapLoaded: () -> Unit
    ) {
        val context = LocalContext.current
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState,
            onMapLoaded = onMapLoaded
        ) {
            GroundOverlay(
                //The width and height is in meters.
                position = GroundOverlayPosition.create(currentMarkerLatLong, 1000f),
                image = uiState.getImageBitmap(context.resources),
                bearing = uiState.bearing,
                transparency = uiState.transparency,
                visible = uiState.visible,
                clickable = uiState.clickable,
                onClick = {
                    Toast.makeText(context, "On ground overlay click", Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}
