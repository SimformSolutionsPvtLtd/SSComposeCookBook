package com.jetpack.compose.learning.maps.geojson

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
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.data.geojson.GeoJsonFeature
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonPoint
import com.google.maps.android.data.geojson.GeoJsonPointStyle
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.maps.MapScaffold
import com.jetpack.compose.learning.maps.animateBound
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.maps.getFeatureBound
import com.jetpack.compose.learning.maps.setFeatureStyle
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * GeoJson Example.
 * It includes the MapEffect side effect to get the map object and create geo json layer.
 */
class MapGeoJsonActivity : ComponentActivity() {

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

        MapScaffold(
            title = "GeoJSON",
            onBackPressed = { onBackPressed() },
            showLoading = showLoading
        ) {
            Text(
                "Long click on map to add your custom feature point.",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            MapsExample { showLoading = false }
        }
    }

    @OptIn(MapsComposeExperimentalApi::class)
    @Composable
    fun MapsExample(onMapLoaded: () -> Unit) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 17f)
        }
        var geoJsonLayer by remember { mutableStateOf<GeoJsonLayer?>(null) }
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState,
            onMapLoaded = onMapLoaded,
            onMapLongClick = {
                if (geoJsonLayer != null) {
                    val point = GeoJsonPoint(it)
                    val properties = hashMapOf("title" to "My Custom Location")
                    val pointFeature = GeoJsonFeature(point, "CU-$it", properties, null)
                    val pointStyle = GeoJsonPointStyle()
                    pointStyle.title = properties["title"]
                    pointFeature.pointStyle = pointStyle
                    geoJsonLayer?.addFeature(pointFeature)
                }
            }
        ) {
            MapEffect(Unit) { map ->
                if (geoJsonLayer == null) {
                    runCatching {
                        geoJsonLayer = GeoJsonLayer(map, R.raw.geo_json_sample, context)
                    }
                    geoJsonLayer?.apply {
                        addLayerToMap()
                        setFeatureStyle()
                        val featureBound = geoJsonLayer?.getFeatureBound()
                        featureBound?.let {
                            scope.launch {
                                delay(100)
                                cameraPositionState.animateBound(it, 100)
                            }
                        }
                        setOnFeatureClickListener {
                            Toast.makeText(
                                context,
                                "Feature Click ${it.geometry.geometryType}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}
