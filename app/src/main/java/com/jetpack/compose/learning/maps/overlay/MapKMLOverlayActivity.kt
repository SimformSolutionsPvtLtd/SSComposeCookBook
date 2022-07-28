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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.data.kml.KmlLayer
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.maps.MapScaffold
import com.jetpack.compose.learning.maps.animateBound
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.maps.getBound
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * KML Example.
 * It includes the MapEffect side effect to get the map object and create kml layer.
 */
class MapKMLOverlayActivity : ComponentActivity() {

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
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 14f)
        }

        MapScaffold(title = "KML", onBackPressed = { onBackPressed() }, showLoading = showLoading) {
            Text(
                "KML Objects on google maps",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            MapsExample(cameraPositionState) { showLoading = false }
        }
    }

    @Composable
    fun MapsExample(cameraPositionState: CameraPositionState, onMapLoaded: () -> Unit) {
        val scope = rememberCoroutineScope()

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = onMapLoaded
        ) {
            KMLComponent {
                scope.launch {
                    delay(300)
                    cameraPositionState.animateBound(it.getBound(), 150)
                }
            }
        }
    }

    @OptIn(MapsComposeExperimentalApi::class)
    @Composable
    private fun KMLComponent(onKMLLayer: (KmlLayer) -> Unit) {
        val context = LocalContext.current
        var kmlLayer by remember { mutableStateOf<KmlLayer?>(null) }

        MapEffect(Unit) { map ->
            if (kmlLayer == null) {
                runCatching {
                    kmlLayer = KmlLayer(map, R.raw.kml_sample, context)
                }
                kmlLayer?.apply {
                    addLayerToMap()
                    setOnFeatureClickListener {
                        Toast.makeText(context, "Feature clicked ${it.id}", Toast.LENGTH_LONG)
                            .show()
                    }
                    onKMLLayer(this)
                }
            }
        }
    }
}