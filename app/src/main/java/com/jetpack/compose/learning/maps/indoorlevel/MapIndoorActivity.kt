package com.jetpack.compose.learning.maps.indoorlevel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Indoor level example.
 * Indoor level represents a level in a building.
 */
class MapIndoorActivity : ComponentActivity() {
    private val indoorBuildingPosition = LatLng(40.7504861, -73.9935901)

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

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun MainContent() {
        var showLoading by remember { mutableStateOf(true) }
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(indoorBuildingPosition, 18f)
        }

        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Indoor Level") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }) {
            Box(modifier = Modifier.padding(it), contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        "Example to show indoor level apis on google map.",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.Center
                    )
                    MapsExample(cameraPositionState) { showLoading = false }
                }
                AnimatedVisibility(
                    visible = showLoading,
                    modifier = Modifier
                        .matchParentSize()
                        .background(MaterialTheme.colors.background),
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    CircularProgressIndicator(modifier = Modifier.wrapContentSize())
                }
            }
        }
    }

    @Composable
    fun MapsExample(
        cameraPositionState: CameraPositionState,
        onMapLoaded: () -> Unit
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState,
            onMapLoaded = onMapLoaded,
            properties = MapProperties(isIndoorEnabled = true)
        )
    }
}