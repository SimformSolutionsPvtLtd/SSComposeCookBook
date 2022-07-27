package com.jetpack.compose.learning.maps.interoperability

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.maps.MapScaffold
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Compose Maps in XML views.
 * Include example of how to use compose map in activity or fragment.
 */
class MapsInXMLActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_maps_in_xml)
        val composeView: ComposeView = findViewById(R.id.composeView)
        composeView.setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }

            BaseView(appTheme.value, systemUiController) {
                var showLoading by remember { mutableStateOf(true) }
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 15f)
                }

                MapScaffold(
                    title = "Compose Maps In XML",
                    onBackPressed = { onBackPressed() },
                    showLoading = showLoading
                ) {
                    GoogleMap(
                        cameraPositionState = cameraPositionState,
                        onMapLoaded = { showLoading = false }
                    ) {
                        Marker(rememberMarkerState(position = currentMarkerLatLong))
                    }
                }
            }
        }
    }
}