package com.jetpack.compose.learning.maps

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.maps.basic.BasicMapActivity
import com.jetpack.compose.learning.maps.basic.LiteMapInListActivity
import com.jetpack.compose.learning.maps.basic.MapInScrollingActivity
import com.jetpack.compose.learning.maps.circle.MapCircleActivity
import com.jetpack.compose.learning.maps.cluster.MapClusterActivity
import com.jetpack.compose.learning.maps.geojson.MapGeoJsonActivity
import com.jetpack.compose.learning.maps.indoorlevel.MapIndoorActivity
import com.jetpack.compose.learning.maps.interoperability.MapsInXMLActivity
import com.jetpack.compose.learning.maps.marker.MapMarkerActivity
import com.jetpack.compose.learning.maps.overlay.MapGroundOverlayActivity
import com.jetpack.compose.learning.maps.overlay.MapHeatMapOverlayActivity
import com.jetpack.compose.learning.maps.overlay.MapKMLOverlayActivity
import com.jetpack.compose.learning.maps.overlay.MapTileOverlayActivity
import com.jetpack.compose.learning.maps.place.ui.MapsNavigationActivity
import com.jetpack.compose.learning.maps.place.ui.MapsPlaceActivity
import com.jetpack.compose.learning.maps.polygon.MapPolygonActivity
import com.jetpack.compose.learning.maps.polyline.MapPolylineActivity
import com.jetpack.compose.learning.maps.projection.MapProjectionActivity
import com.jetpack.compose.learning.maps.scalebar.MapScaleBarActivity
import com.jetpack.compose.learning.maps.snapshot.MapSnapShotActivity
import com.jetpack.compose.learning.model.Component
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class GoogleMapsActivity : ComponentActivity() {
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
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Google Maps") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        }) {
            LazyColumn(modifier = Modifier.padding(it)) {
                items(getComponents(), key = { item -> item.className.name }) { item ->
                    ButtonComponent(item)
                }
            }
        }
    }

    @Composable
    fun ButtonComponent(component: Component) {
        OutlinedButton(
            onClick = {
                startActivity(Intent(this, component.className))
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary.copy(0.04f)
            ),
            border = BorderStroke(2.dp, MaterialTheme.colors.primary.copy(0.5f)),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = component.componentName,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )
        }
    }

    private fun getComponents(): List<Component> = listOf(
        Component("Basic Map", BasicMapActivity::class.java),
        Component("Markers", MapMarkerActivity::class.java),
        Component("Polyline", MapPolylineActivity::class.java),
        Component("Polygon", MapPolygonActivity::class.java),
        Component("Circle", MapCircleActivity::class.java),
        Component("Ground Overlay", MapGroundOverlayActivity::class.java),
        Component("Tile Overlay", MapTileOverlayActivity::class.java),
        Component("Indoor Level", MapIndoorActivity::class.java),
        Component("Lite Map", LiteMapInListActivity::class.java),
        Component("Map in scroll screen", MapInScrollingActivity::class.java),
        Component("Place Picker", MapsPlaceActivity::class.java),
        Component("Navigation Viewer", MapsNavigationActivity::class.java),
        Component("Projection", MapProjectionActivity::class.java),
        Component("Compose Map In XML", MapsInXMLActivity::class.java),
        Component("Cluster", MapClusterActivity::class.java),
        Component("Heat Map", MapHeatMapOverlayActivity::class.java),
        Component("GeoJSON", MapGeoJsonActivity::class.java),
        Component("KML", MapKMLOverlayActivity::class.java),
        Component("ScaleBar", MapScaleBarActivity::class.java),
        Component("Snapshot", MapSnapShotActivity::class.java),
    )
}
