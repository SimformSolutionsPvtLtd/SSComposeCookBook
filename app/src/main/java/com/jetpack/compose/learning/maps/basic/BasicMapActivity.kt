package com.jetpack.compose.learning.maps.basic

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import com.jetpack.compose.learning.maps.BasicMapUiState
import com.jetpack.compose.learning.maps.GoogleMapUIOptions
import com.jetpack.compose.learning.maps.statueOfLiberty
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

class BasicMapActivity : ComponentActivity() {
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

    @OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
    @Composable
    fun MainContent(
        mapsViewModel: BasicMapsViewModel = viewModel(
            factory = AndroidViewModelFactory(
                application
            )
        )
    ) {

        val modalBottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        var isMapLoaded by remember { mutableStateOf(false) }

        val uiState by mapsViewModel.state.collectAsState()

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(statueOfLiberty, 17f)
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { results ->
            val anyGranted = results.values.any { it }
            if (anyGranted) {
                mapsViewModel.updateMyLocationLayer()
            }
        }

        val coroutineScope = rememberCoroutineScope()
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
            sheetContent = {
                GoogleMapUIOptions(
                    uiState,
                    mapsViewModel::changeMapType,
                    mapsViewModel::changeMapStyle,
                    mapsViewModel::setBuildingEnable,
                    mapsViewModel::setIndoorEnable,
                    {
                        mapsViewModel.setMyLocationLayerEnable(it)
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        )
                    },
                    mapsViewModel::setTrafficEnable,
                    mapsViewModel::setCompassEnable,
                    mapsViewModel::setMapToolBarEnable,
                    mapsViewModel::setMyLocationButtonEnable,
                    mapsViewModel::setRotationGestureEnable,
                    mapsViewModel::setTiltGestureEnable,
                    mapsViewModel::setZoomControlEnable,
                    mapsViewModel::setZoomGestureEnable,
                )
            },
        ) {
            Scaffold(topBar = {
                TopAppBar(
                    title = { Text("Basic Map") },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (modalBottomSheetState.isVisible)
                                    modalBottomSheetState.hide()
                                else
                                    modalBottomSheetState.show()
                            }
                        }) {
                            Icon(Icons.Filled.Tune, contentDescription = "Map UI Option")
                        }
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
                    }
                )
            }) {
                Box(modifier = Modifier.padding(it), contentAlignment = Alignment.Center) {
                    MapsExample(uiState, cameraPositionState) { isMapLoaded = true }
                    AnimatedVisibility(
                        visible = !isMapLoaded,
                        modifier = Modifier
                            .matchParentSize()
                            .background(MaterialTheme.colors.background),
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .wrapContentSize()
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun MapsExample(
        uiState: BasicMapUiState,
        cameraPositionState: CameraPositionState,
        onMapLoaded: () -> Unit
    ) {
        val markerState = rememberMarkerState(position = cameraPositionState.position.target)
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