package com.jetpack.compose.learning.maps.place.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.jetpack.compose.learning.maps.MapVerticalSpace
import com.jetpack.compose.learning.maps.place.component.MapPlaceScreens
import com.jetpack.compose.learning.maps.place.component.PlacePicker
import com.jetpack.compose.learning.maps.place.model.PlaceResult
import com.jetpack.compose.learning.maps.place.viewmodel.MapsPlaceActivityViewModel
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class MapsPlaceActivity : ComponentActivity() {
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
    fun MainContent(viewModel: MapsPlaceActivityViewModel = viewModel()) {
        val navController = rememberNavController()
        NavHost(navController, startDestination = MapPlaceScreens.Main.route) {
            composable(MapPlaceScreens.Main.route) {
                MainScreen(navController, viewModel)
            }
            composable(MapPlaceScreens.PlacePicker.route) {
                PlacePicker {
                    viewModel.selectPlace(it)
                    navController.popBackStack()
                }
            }
        }
    }

    @Composable
    fun MainScreen(navController: NavController, viewModel: MapsPlaceActivityViewModel) {
        val placeResult by viewModel.placeResult.collectAsState()

        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Place") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        }) {
            PlacePickerExample(modifier = Modifier.padding(it), placeResult) {
                navController.navigate(MapPlaceScreens.PlacePicker.route)
            }
        }
    }

    @Composable
    private fun PlacePickerExample(
        modifier: Modifier,
        placeResult: PlaceResult?,
        onSelectPlaceClick: () -> Unit
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(onClick = onSelectPlaceClick) {
                Text("Open Place Picker")
            }
            if (placeResult != null) {
                MapVerticalSpace(5.dp)
                Text(placeResult.address)
                MapVerticalSpace()
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = CameraPositionState(
                        position = CameraPosition.fromLatLngZoom(
                            placeResult.geometry.location.getLatLng(), 18f
                        )
                    ),
                    googleMapOptionsFactory = {
                        GoogleMapOptions().apply {
                            liteMode(true)
                        }
                    }
                ) {
                    Marker(state = MarkerState(placeResult.geometry.location.getLatLng()))
                }
            }
        }
    }
}