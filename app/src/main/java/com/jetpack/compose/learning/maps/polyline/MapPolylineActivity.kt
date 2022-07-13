package com.jetpack.compose.learning.maps.polyline

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import com.jetpack.compose.learning.maps.GoogleMapPolylineOptions
import com.jetpack.compose.learning.maps.PolyLineMapUiState
import com.jetpack.compose.learning.maps.statueOfLiberty
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

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

    @OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
    @Composable
    fun MainContent(mapsViewModel: MapPolylineViewModel = viewModel()) {

        val modalBottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        var isMapLoaded by remember { mutableStateOf(false) }

        val uiState by mapsViewModel.state.collectAsState()

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(statueOfLiberty, 17f)
        }
        val coroutineScope = rememberCoroutineScope()

        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
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
            Scaffold(topBar = {
                TopAppBar(
                    title = { Text("Polyline") },
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
                    }
                )
            }) {
                Box(modifier = Modifier.padding(it), contentAlignment = Alignment.Center) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            "Tap on map to add marker. A Polyline will be added between markers. Change polyline options from toolbar.",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.subtitle1,
                            textAlign = TextAlign.Center
                        )
                        MapsExample(uiState, cameraPositionState) { isMapLoaded = true }
                    }
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
        uiState: PolyLineMapUiState,
        cameraPositionState: CameraPositionState,
        onMapLoaded: () -> Unit
    ) {
        val markerStateList =
            remember { mutableStateListOf(MarkerState(statueOfLiberty)) }

        val context = LocalContext.current

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState,
            onMapLoaded = onMapLoaded,
            onMapClick = {
                val anyPresent = markerStateList.any { state -> state.position == it }
                if (!anyPresent)
                    markerStateList.add(MarkerState(it))
            }
        ) {
            markerStateList.forEach {
                Marker(it, draggable = uiState.markerDraggable, visible = uiState.markerVisibility)
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
                visible = uiState.visibility,
                width = uiState.strokeWidth,
                onClick = {
                    Toast.makeText(context, "On Polyline Click", Toast.LENGTH_LONG).show()
                })
        }
    }
}