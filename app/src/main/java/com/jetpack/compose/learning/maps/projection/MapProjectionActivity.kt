package com.jetpack.compose.learning.maps.projection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.compose.learning.maps.MapScaffold
import com.jetpack.compose.learning.maps.MapVerticalSpace
import com.jetpack.compose.learning.maps.animatePosition
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

/**
 * Projection Example.
 * It includes visible region of map and camera movement reason.
 * You can get the visible region of map from the cameraPositionState of GoogleMaps composable function.
 */
class MapProjectionActivity : ComponentActivity() {

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
    fun MainContent() {
        var showLoading by remember { mutableStateOf(true) }
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 17f)
        }

        MapScaffold(
            title = "Projection",
            onBackPressed = { onBackPressed() },
            showLoading = showLoading
        ) {
            MapInfoView(cameraPositionState)
            MapsExample(cameraPositionState) { showLoading = false }
        }
    }

    @Composable
    private fun MapInfoView(cameraPositionState: CameraPositionState) {
        var farLeftPosition by remember { mutableStateOf("") }
        var farRightPosition by remember { mutableStateOf("") }
        var nearLeftPosition by remember { mutableStateOf("") }
        var nearRightPosition by remember { mutableStateOf("") }
        var cameraMovementReason by remember { mutableStateOf(CameraMoveStartedReason.NO_MOVEMENT_YET) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(cameraPositionState.isMoving) {
            val visibleRegion = cameraPositionState.projection?.visibleRegion
            if (visibleRegion != null) {
                farLeftPosition = visibleRegion.farLeft.value()
                farRightPosition = visibleRegion.farRight.value()
                nearLeftPosition = visibleRegion.nearLeft.value()
                nearRightPosition = visibleRegion.nearRight.value()
            }
            cameraMovementReason = cameraPositionState.cameraMoveStartedReason
        }
        Text(
            "Far Left - $farLeftPosition",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.subtitle2,
        )
        MapVerticalSpace(5.dp)
        Text(
            "Far Right - $farRightPosition",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.subtitle2,
        )
        MapVerticalSpace(5.dp)
        Text(
            "Near Right - $nearRightPosition",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.subtitle2,
        )
        MapVerticalSpace(5.dp)
        Text(
            "Near Left - $nearLeftPosition",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.subtitle2,
        )
        MapVerticalSpace(5.dp)
        Text(
            "Camera movement start reason - $cameraMovementReason",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.subtitle2,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(onClick = {
                scope.launch {
                    cameraPositionState.animatePosition(target = LatLng(40.785091, -73.968285))
                }
            }) {
                Text("Central Park")
            }
            OutlinedButton(onClick = {
                scope.launch {
                    cameraPositionState.animatePosition(target = LatLng(38.897957, -77.036560))
                }
            }) {
                Text("White House")
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

    private fun LatLng.value(): String {
        return "${"%.4f".format(latitude)} / ${"%.4f".format(longitude)}"
    }
}