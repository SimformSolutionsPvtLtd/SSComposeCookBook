package com.jetpack.compose.learning.maps.overlay

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlay
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.heatmaps.Gradient
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.maps.GoogleMapHeatMapOptions
import com.jetpack.compose.learning.maps.MapScreen
import com.jetpack.compose.learning.maps.TileOverlayMapUIState
import com.jetpack.compose.learning.maps.createPosition
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import org.json.JSONArray
import java.util.Scanner

/**
 * Heat Map Example.
 * It includes the MapEffect side effect to get the map object and create heat map layer.
 * All the properties can be modified via toolbar.
 */
class MapHeatMapOverlayActivity : ComponentActivity() {

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
    fun MainContent(mapsViewModel: MapTileOverlayViewModel = viewModel()) {
        var showLoading by remember { mutableStateOf(true) }
        val uiState by mapsViewModel.state.collectAsState()
        val cameraPositionState = rememberCameraPositionState {
            position = createPosition(LatLng(40.776676, -73.971321), 11f)
        }

        MapScreen(title = "Heat Map", onBackPressed = { onBackPressed() }, sheetContent = {
            GoogleMapHeatMapOptions(
                uiState,
                mapsViewModel::setTransparency,
                mapsViewModel::setFadeIn,
                mapsViewModel::setVisible,
                mapsViewModel::setHeatMapRadius,
                mapsViewModel::setHeatMapColors,
                mapsViewModel::setHeatMapOpacity
            )
        }, showLoading = showLoading) {
            Text(
                "Heat map via Tile Overlay. This heat map shows italian restaurants in Manhattan",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            MapsExample(uiState, cameraPositionState) { showLoading = false }
        }
    }

    @Composable
    fun MapsExample(
        uiState: TileOverlayMapUIState,
        cameraPositionState: CameraPositionState,
        onMapLoaded: () -> Unit
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState,
            onMapLoaded = onMapLoaded
        ) {
            HeatMap(uiState)
        }
    }

    @OptIn(MapsComposeExperimentalApi::class)
    @Composable
    private fun HeatMap(uiState: TileOverlayMapUIState) {
        var heatMapProvider by remember { mutableStateOf<HeatmapTileProvider?>(null) }
        var tileOverlay by remember { mutableStateOf<TileOverlay?>(null) }
        val latLngList by loadItems()

        MapEffect(latLngList, uiState) { map ->
            val startPoints = floatArrayOf(0f, 0.35f, 0.7f)
            val colors = uiState.heatMapGradient.map { it.toArgb() }.toIntArray()
            val gradient = Gradient(colors, startPoints)

            if (heatMapProvider == null) {
                heatMapProvider = HeatmapTileProvider.Builder()
                    .gradient(gradient)
                    .radius(uiState.heatMapRadius.toInt())
                    .opacity(uiState.heatMapOpacity.toDouble())
                    .weightedData(latLngList).build()
                val tileOverlayOptions =
                    TileOverlayOptions().tileProvider(heatMapProvider!!)
                        .visible(uiState.visible)
                        .transparency(uiState.transparency)
                        .fadeIn(uiState.fadeIn)
                tileOverlay = map.addTileOverlay(tileOverlayOptions)
            } else {
                heatMapProvider?.apply {
                    setWeightedData(latLngList)
                    setGradient(gradient)
                    setRadius(uiState.heatMapRadius.toInt())
                    setOpacity(uiState.heatMapOpacity.toDouble())
                }
                tileOverlay?.apply {
                    fadeIn = uiState.fadeIn
                    isVisible = uiState.visible
                    transparency = uiState.transparency
                }
            }
            tileOverlay?.clearTileCache()
        }

        //You can also add the tile over lay for the heat map via compose function.
        //The difference between compose function and adding via map is that we get to access of clearTileCache method
        //While the compose function works but any update in tile provider will require clearing of previous cache
        //So we are using the tile overlay for heat map via adding it manually in the map.
        /*if (heatMapProvider != null) {
            TileOverlay(
                tileProvider = heatMapProvider!!,
                transparency = uiState.transparency,
                visible = uiState.visibility,
                fadeIn = uiState.fadeIn,
            )
        }*/
    }

    /**
     * Using side effect produceState to create the list of lat-lng.
     */
    @Composable
    fun loadItems(context: Context = LocalContext.current): State<List<WeightedLatLng>> {
        return produceState<List<WeightedLatLng>>(initialValue = arrayListOf(), context) {
            val result = mutableListOf<WeightedLatLng>()
            val inputStream = context.resources.openRawResource(R.raw.manhattan_italian_restaurants)
            val beginningOfInput = "\\A"
            val json = Scanner(inputStream).useDelimiter(beginningOfInput).next()
            val array = JSONArray(json)
            for (i in 0 until array.length()) {
                val data = array.getJSONObject(i)
                val lat = data.getDouble("latitude")
                val lng = data.getDouble("longitude")
                val price = data.getDouble("Price")
                result.add(WeightedLatLng(LatLng(lat, lng), price))
            }
            value = result
        }
    }
}