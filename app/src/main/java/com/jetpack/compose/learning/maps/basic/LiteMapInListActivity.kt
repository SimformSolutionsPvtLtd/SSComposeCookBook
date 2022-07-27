package com.jetpack.compose.learning.maps.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.maps.MapScaffold
import com.jetpack.compose.learning.maps.MapVerticalSpace
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Lite Map Example. It includes multiple map inside lazy list.
 */
class LiteMapInListActivity : ComponentActivity() {
    private val placesList = DataProvider.getTouristPlaceList()

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
        MapScaffold(
            title = "Lite Map",
            onBackPressed = { onBackPressed() },
            showLoading = false
        ) {
            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)) {
                items(items = placesList, key = { place -> place.id }) { place ->
                    PlaceItem(place)
                    MapVerticalSpace(15.dp)
                }
            }
        }
    }

    @Composable
    private fun PlaceItem(place: TouristPlace) {
        Column(modifier = Modifier.border(2.dp, Color.Red, RoundedCornerShape(6.dp))) {
            Text(
                place.title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, shape = RoundedCornerShape(6.dp, 6.dp))
                    .padding(vertical = 10.dp),
                textAlign = TextAlign.Center
            )
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(200.dp)
                    .background(color = Color.Transparent, shape = RoundedCornerShape(6.dp)),
                CameraPositionState(position = CameraPosition.fromLatLngZoom(place.position, 17f)),
                googleMapOptionsFactory = {
                    GoogleMapOptions().apply {
                        liteMode(true)
                    }
                }
            ) {
                Marker(state = MarkerState(place.position), title = place.title)
            }
        }
    }
}

data class TouristPlace(val id: Int, val title: String, val position: LatLng)