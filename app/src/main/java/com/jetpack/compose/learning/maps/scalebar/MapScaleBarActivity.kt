package com.jetpack.compose.learning.maps.scalebar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.widgets.DisappearingScaleBar
import com.google.maps.android.compose.widgets.ScaleBar
import com.jetpack.compose.learning.maps.GoogleMapScaleBarOptions
import com.jetpack.compose.learning.maps.MapScreen
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Scale bar Example.
 * ScaleBar shows the current scale of the map.
 * All the properties can be modified via toolbar.
 */
class MapScaleBarActivity : ComponentActivity() {

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
    fun MainContent(viewModel: MapScaleBarViewModel = viewModel()) {
        var showLoading by remember { mutableStateOf(true) }
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 17f)
        }
        val state by viewModel.state.collectAsState()

        MapScreen(title = "ScaleBar", onBackPressed = { onBackPressed() }, sheetContent = {
            GoogleMapScaleBarOptions(
                state,
                viewModel::setTextColor,
                viewModel::setLineColor,
                viewModel::setShadowColor
            )
        }, showLoading = showLoading) {
            Text(
                text = "ScaleBar shows the current scale of the map.",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            Box {
                MapsExample(cameraPositionState) { showLoading = false }
                ScaleBar(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 5.dp),
                    cameraPositionState = cameraPositionState,
                    textColor = state.textColor,
                    lineColor = state.lineColor,
                    shadowColor = state.shadowColor,
                )
                DisappearingScaleBar(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 5.dp, end = 15.dp),
                    cameraPositionState = cameraPositionState,
                    textColor = state.textColor,
                    lineColor = state.lineColor,
                    shadowColor = state.shadowColor,
                )
            }
        }
    }

    @Composable
    fun MapsExample(cameraPositionState: CameraPositionState, onMapLoaded: () -> Unit) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = onMapLoaded
        )
    }
}
