package com.jetpack.compose.learning.maps.polygon

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.jetpack.compose.learning.maps.MapScreen
import com.jetpack.compose.learning.maps.PolygonMapUIState
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

/**
 * Polygon shape Example.
 * It includes all the properties and customization for the Polygon() composable function.
 * All the options can be modified via clicking toolbar icon.
 */
class MapPolygonActivity : ComponentActivity() {
    private val polygonLatLng = listOf(
        LatLng(40.748817, -73.985428),
        LatLng(40.749607, -73.9728967),
        LatLng(40.7450187, -73.9827565),
        LatLng(40.7441246, -73.9871124),
    )
    private val polygonHolesLatLng = listOf(
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

    @Composable
    fun MainContent(mapsViewModel: MapPolygonViewModel = viewModel()) {
        var showLoading by remember { mutableStateOf(true) }
        val uiState by mapsViewModel.state.collectAsState()
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 15f)
        }
        val coroutineScope = rememberCoroutineScope()

        MapScreen(
            title = "Polygon",
            onBackPressed = { onBackPressed() },
            showLoading = showLoading,
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
            Text(
                "A Polygon is added. You can add hole in polygon also. Change polygon options from toolbar.",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            MapsExample(uiState, cameraPositionState) {
                val latLngBounds = LatLngBounds.Builder().apply {
                    polygonLatLng.forEach { position ->
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
                showLoading = false
            }
        }
    }

    @Composable
    fun MapsExample(
        uiState: PolygonMapUIState,
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
                points = polygonLatLng,
                holes = polygonHolesLatLng,
                clickable = uiState.clickable,
                fillColor = uiState.fillColor.copy(alpha = uiState.fillColorAlpha),
                geodesic = uiState.geodesic,
                strokeJointType = uiState.jointType.type,
                strokePattern = uiState.getPatternType(),
                visible = uiState.visible,
                strokeWidth = uiState.strokeWidth,
                strokeColor = uiState.strokeColor.copy(alpha = uiState.strokeColorAlpha),
                onClick = {
                    Toast.makeText(context, "On Polygon Click", Toast.LENGTH_LONG).show()
                })
        }
    }
}
