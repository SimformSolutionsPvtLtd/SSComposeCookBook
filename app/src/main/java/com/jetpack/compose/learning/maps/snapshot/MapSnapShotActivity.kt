package com.jetpack.compose.learning.maps.snapshot

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.ktx.awaitSnapshot
import com.jetpack.compose.learning.maps.MapScaffold
import com.jetpack.compose.learning.maps.MapVerticalSpace
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

/**
 * Snap shot Example.
 * It includes the MapEffect side effect to get the map object and remember it.
 * Bitmap of map will be captured via map object.
 */
class MapSnapShotActivity : ComponentActivity() {

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
        var showLoading by remember { mutableStateOf(false) }

        MapScaffold(
            title = "SnapShot",
            onBackPressed = { onBackPressed() },
            showLoading = showLoading
        ) {
            MapsExample { showLoading = false }
        }
    }

    @OptIn(MapsComposeExperimentalApi::class)
    @Composable
    fun MapsExample(onMapLoaded: () -> Unit) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 17f)
        }
        var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
        var bitmap by remember { mutableStateOf<Bitmap?>(null) }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GoogleMap(
                modifier = Modifier.weight(1f),
                cameraPositionState,
                onMapLoaded = onMapLoaded
            ) {
                MapEffect(Unit) { map ->
                    googleMap = map
                }
            }
            MapVerticalSpace()
            OutlinedButton(onClick = {
                scope.launch {
                    bitmap = googleMap?.awaitSnapshot()
                }
            }) {
                Text("Take Snapshot")
            }
            MapVerticalSpace()
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .background(Color.Transparent, RoundedCornerShape(8.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (bitmap != null) {
                    Image(bitmap = bitmap!!.asImageBitmap(), contentDescription = "Snapshot Map")
                }
            }
        }
    }
}