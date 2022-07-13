package com.jetpack.compose.learning.maps.basic

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.jetpack.compose.learning.maps.BasicMapUIState
import com.jetpack.compose.learning.maps.GoogleMapUIOptions
import com.jetpack.compose.learning.maps.MapScreen
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

/**
 * Basic Map Example. It includes map ui settings and map properties usage.
 * All the options can be modified via clicking toolbar icons.
 */
class BasicMapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                val viewModel: BasicMapsViewModel =
                    viewModel(factory = AndroidViewModelFactory(application))
                MainContent(viewModel)
            }
        }
    }

    @Composable
    fun MainContent(viewModel: BasicMapsViewModel) {
        var showLoading by remember { mutableStateOf(true) }
        val uiState by viewModel.state.collectAsState()
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 17f)
        }
        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { results ->
            val anyGranted = results.values.any { it }
            if (anyGranted) {
                viewModel.updateMyLocationLayer()
            }
        }
        val coroutineScope = rememberCoroutineScope()

        MapScreen(
            title = "Basic Map",
            onBackPressed = { onBackPressed() },
            showLoading = showLoading,
            actionItems = {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            cameraPositionState.animate(CameraUpdateFactory.zoomIn())
                        }
                    },
                    enabled = cameraPositionState.position.zoom < uiState.mapsProperties.maxZoomPreference
                ) {
                    Icon(Icons.Filled.ZoomIn, contentDescription = "Zoom In Map")
                }
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            cameraPositionState.animate(CameraUpdateFactory.zoomOut())
                        }
                    },
                    enabled = cameraPositionState.position.zoom > uiState.mapsProperties.minZoomPreference
                ) {
                    Icon(Icons.Filled.ZoomOut, contentDescription = "Zoom Out Map")
                }
            },
            sheetContent = {
                GoogleMapUIOptions(
                    uiState,
                    viewModel::changeMapType,
                    viewModel::changeMapStyle,
                    viewModel::setBuildingEnable,
                    viewModel::setIndoorEnable,
                    {
                        viewModel.setMyLocationLayerEnable(it)
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        )
                    },
                    viewModel::setTrafficEnable,
                    viewModel::setCompassEnable,
                    viewModel::setMapToolBarEnable,
                    viewModel::setMyLocationButtonEnable,
                    viewModel::setRotationGestureEnable,
                    viewModel::setTiltGestureEnable,
                    viewModel::setZoomControlEnable,
                    viewModel::setZoomGestureEnable,
                )
            },
        ) {
            MapsExample(uiState, cameraPositionState) { showLoading = false }
        }
    }

    @Composable
    fun MapsExample(
        uiState: BasicMapUIState,
        cameraPositionState: CameraPositionState,
        onMapLoaded: () -> Unit
    ) {
        val markerState = rememberMarkerState(position = currentMarkerLatLong)
        val context = LocalContext.current
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState,
            properties = uiState.mapsProperties,
            uiSettings = uiState.mapUiSettings,
            onMapLoaded = onMapLoaded,
            onPOIClick = {
                Toast.makeText(context, "POI - ${it.name}", Toast.LENGTH_LONG).show()
            },
            onMyLocationButtonClick = {
                Toast.makeText(context, "My Location Button Clicked", Toast.LENGTH_LONG).show()
                false
            },
            onMyLocationClick = {
                Toast.makeText(
                    context,
                    "My Location ${it.latitude}-${it.longitude}",
                    Toast.LENGTH_LONG
                ).show()
            },
            onMapLongClick = {
                Toast.makeText(
                    context,
                    "Long Press ${it.latitude}-${it.longitude}",
                    Toast.LENGTH_LONG
                ).show()
            },
        ) {
            Marker(markerState, title = "Statue Of Liberty")
        }
    }
}
