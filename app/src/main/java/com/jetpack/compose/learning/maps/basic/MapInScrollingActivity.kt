package com.jetpack.compose.learning.maps.basic

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpack.compose.learning.maps.currentMarkerLatLong
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Map in scroll view Example.
 * Example of how function map can be used inside scrollable view.
 */
class MapInScrollingActivity : ComponentActivity() {
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
                title = { Text("Map in scrolling") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }) {
            BodyContent(Modifier.padding(it))
        }
    }

    @Composable
    private fun BodyContent(modifier: Modifier) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentMarkerLatLong, 16f)
        }
        var columnScrollEnabled by remember { mutableStateOf(true) }

        LaunchedEffect(cameraPositionState.isMoving) {
            if (!cameraPositionState.isMoving) {
                columnScrollEnabled = true
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState(), columnScrollEnabled),
        ) {
            repeat(15) {
                TextComponent(it + 1)
            }
            MapComponent(cameraPositionState) {
                columnScrollEnabled = false
            }
            repeat(15) {
                TextComponent(it + 16)
            }
        }
    }

    @Composable
    fun TextComponent(index: Int) {
        Text(
            text = "Component $index",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            textAlign = TextAlign.Center
        )
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun MapComponent(cameraPositionState: CameraPositionState, onMapTouch: () -> Unit) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(250.dp)
                .pointerInteropFilter(
                    onTouchEvent = {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                onMapTouch()
                                false
                            }
                            else -> {
                                true
                            }
                        }
                    }),
            cameraPositionState = cameraPositionState
        ) {
            Marker(state = MarkerState(currentMarkerLatLong))
        }
    }
}