package com.jetpack.compose.learning.maps.cluster

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.maps.MapScaffold
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Cluster Example.
 * It includes the MapEffect side effect to get the map object and create cluster manager.
 */
class MapClusterActivity : ComponentActivity() {

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
            title = "Cluster",
            onBackPressed = { onBackPressed() },
            showLoading = showLoading
        ) {
            Text(
                "Zoom out to check the cluster of markers",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            MapsExample(cameraPositionState) { showLoading = false }
        }
    }

    @OptIn(MapsComposeExperimentalApi::class)
    @Composable
    fun MapsExample(cameraPositionState: CameraPositionState, onMapLoaded: () -> Unit) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState,
            onMapLoaded = onMapLoaded
        ) {
            val context = LocalContext.current
            val currentView = LocalView.current
            val compositionContext = rememberCompositionContext()
            var clusterManager by remember { mutableStateOf<ClusterManager<SSClusterItem>?>(null) }

            MapEffect(Unit) { map ->
                if (clusterManager == null) {
                    clusterManager = ClusterManager(context, map)
                    val markerCollection = clusterManager?.markerCollection
                    val infoWindowAdapter =
                        ClusterInfoWindowAdapter((currentView as ViewGroup), compositionContext)
                    markerCollection?.setInfoWindowAdapter(infoWindowAdapter)
                    clusterManager?.clusterMarkerCollection?.setInfoWindowAdapter(infoWindowAdapter)
                }
                clusterManager?.addItems(DataProvider.getClusterItems())
            }

            LaunchedEffect(cameraPositionState.isMoving) {
                if (!cameraPositionState.isMoving) {
                    clusterManager?.onCameraIdle()
                }
            }
        }
    }
}
