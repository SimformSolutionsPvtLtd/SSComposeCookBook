package com.jetpack.compose.learning.maps.circle

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
import com.jetpack.compose.learning.maps.CircleMapUiState
import com.jetpack.compose.learning.maps.GoogleMapCircleOptions
import com.jetpack.compose.learning.maps.statueOfLiberty
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

class MapCircleActivity : ComponentActivity() {
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
    fun MainContent(mapsViewModel: MapCircleViewModel = viewModel()) {

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
                GoogleMapCircleOptions(
                    uiState,
                    mapsViewModel::setMarkerDraggable,
                    mapsViewModel::setMarkerVisible,
                    mapsViewModel::setFillColor,
                    mapsViewModel::setFillColorAlpha,
                    mapsViewModel::setRadius,
                    mapsViewModel::setClickable,
                    mapsViewModel::setStrokeColor,
                    mapsViewModel::setPatternType,
                    mapsViewModel::setVisible,
                    mapsViewModel::setStrokeWidth,
                    mapsViewModel::setStrokeAlpha
                )
            },
        ) {
            Scaffold(topBar = {
                TopAppBar(
                    title = { Text("Circle") },
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
                            "Tap on map to add marker. A Circle will be added on the marker's position. Change circle options from toolbar.",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.subtitle1,
                            textAlign = TextAlign.Center
                        )
                        MapsExample(uiState, cameraPositionState) {
                            isMapLoaded = true
                        }
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
        uiState: CircleMapUiState,
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
                Circle(
                    center = it.position,
                    radius = uiState.radius.toDouble(),
                    fillColor = uiState.fillColor.copy(alpha = uiState.fillColorAlpha),
                    clickable = uiState.clickable,
                    strokeColor = uiState.strokeColor.copy(alpha = uiState.strokeColorAlpha),
                    strokePattern = uiState.getPatternType(),
                    visible = uiState.visibility,
                    strokeWidth = uiState.strokeWidth,
                    onClick = {
                        Toast.makeText(context, "On Circle Click", Toast.LENGTH_LONG).show()
                    })
            }
        }
    }
}