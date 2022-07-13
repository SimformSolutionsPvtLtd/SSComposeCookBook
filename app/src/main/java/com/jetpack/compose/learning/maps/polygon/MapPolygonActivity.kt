package com.jetpack.compose.learning.maps.polygon

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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.compose.learning.maps.GoogleMapPolygonOptions
import com.jetpack.compose.learning.maps.PolygonMapUiState
import com.jetpack.compose.learning.maps.statueOfLiberty
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

class MapPolygonActivity : ComponentActivity() {

    private val polyGonLatLng = listOf(
        LatLng(40.748817, -73.985428),
        LatLng(40.749607, -73.9728967),
        LatLng(40.7450187, -73.9827565),
        LatLng(40.7441246, -73.9871124),
    )

    private val polyGonHolesLatLng = listOf(
        listOf(
            LatLng(40.7475017, -73.9847417),
            LatLng(40.747648, -73.9812007),
            LatLng(40.7470303, -73.9845052),
        )
    )

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
    fun MainContent(mapsViewModel: MapPolygonViewModel = viewModel()) {

        val modalBottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        var isMapLoaded by remember { mutableStateOf(false) }

        val uiState by mapsViewModel.state.collectAsState()

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(statueOfLiberty, 15f)
        }
        val coroutineScope = rememberCoroutineScope()

        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
            sheetContent = {
                GoogleMapPolygonOptions(
                    uiState,
                    mapsViewModel::setFillColor,
                    mapsViewModel::setFillColorAlpha,
                    mapsViewModel::setGeodesic,
                    mapsViewModel::setPolygonClickable,
                    mapsViewModel::setStrokeColor,
                    mapsViewModel::setJointType,
                    mapsViewModel::setPatternType,
                    mapsViewModel::setVisible,
                    mapsViewModel::setStrokeWidth,
                    mapsViewModel::setStrokeAlpha
                )
            },
        ) {
            Scaffold(topBar = {
                TopAppBar(
                    title = { Text("Polygon") },
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
                            "A Polygon is added. You can add hole in polygon also. Change polygon options from toolbar.",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.subtitle1,
                            textAlign = TextAlign.Center
                        )
                        MapsExample(uiState, cameraPositionState) {
                            val latLngBounds = LatLngBounds.Builder().apply {
                                polyGonLatLng.forEach { position ->
                                    include(position)
                                }
                            }.build()
                            coroutineScope.launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngBounds(
                                        latLngBounds,
                                        50
                                    )
                                )
                            }
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
        uiState: PolygonMapUiState,
        cameraPositionState: CameraPositionState,
        onMapLoaded: () -> Unit
    ) {
        val context = LocalContext.current

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState,
            onMapLoaded = onMapLoaded
        ) {
            Polygon(
                points = polyGonLatLng,
                holes = polyGonHolesLatLng,
                clickable = uiState.clickable,
                fillColor = uiState.fillColor.copy(alpha = uiState.fillColorAlpha),
                geodesic = uiState.geodesic,
                strokeJointType = uiState.jointType.type,
                strokePattern = uiState.getPatternType(),
                visible = uiState.visibility,
                strokeWidth = uiState.strokeWidth,
                strokeColor = uiState.strokeColor.copy(alpha = uiState.strokeColorAlpha),
                onClick = {
                    Toast.makeText(context, "On Polygon Click", Toast.LENGTH_LONG).show()
                })
        }
    }
}