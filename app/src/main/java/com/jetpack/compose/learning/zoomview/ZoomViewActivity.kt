package com.jetpack.compose.learning.zoomview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class ZoomViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }

    @Composable
    private fun MainContent() {
        val systemUiController = remember { SystemUiController(window) }
        val appTheme = remember { mutableStateOf(AppThemeState()) }
        BaseView(appTheme.value, systemUiController) {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("ZoomView") }, navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    })
                }
            ) { contentPadding ->
                ZoomViewComposable(Modifier.padding(contentPadding))
            }
        }
    }

    @Composable
    fun ZoomViewComposable(modifier: Modifier = Modifier) {
        // Reacting to state changes is the core behavior of Compose. We use the state composable
        // that is used for holding a state value in this composable for representing the current
        // value scale(for zooming in the image) & translation(for panning across the image). Any
        // composable that reads the value of counter will be recomposed any time the value changes.
        // This ensures that only the composables that depend on this will be redraw while the
        // rest remain unchanged. This ensures efficiency and is a performance optimization. It
        // is inspired from existing frameworks like React.
        var scale by remember { mutableStateOf(1f) }
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        val height =
            with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.toDp().toPx() }

        // Column is a composable that places its children in a vertical sequence. You
        // can think of it similar to a LinearLayout with the vertical orientation.
        // In addition we also pass a few modifiers to it.

        // You can think of Modifiers as implementations of the decorators pattern that are used to
        // modify the composable that its applied to. In the example below, we make the Column composable
        // zoomable by leveraging the Modifier.pointerInput modifier
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown()
                            do {
                                val event = awaitPointerEvent()
                                scale = (scale * event.calculateZoom()).coerceIn(1F, 3F)
                                val offset = event.calculatePan()
                                //when zooms in or out image so here [coerceIn] coerces image width and height
                                //and limiting its zoom.
                                offsetX = (offsetX + offset.x)
                                    .coerceIn(
                                        -((scale - 1F).coerceIn(
                                            0F,
                                            1F
                                        ) * (size.width.toFloat() * .33F) * scale),
                                        ((scale - 1F).coerceIn(
                                            0F,
                                            1F
                                        ) * (size.width.toFloat() * .33F) * scale)
                                    )
                                offsetY = (offsetY + offset.y)
                                    .coerceIn(
                                        -((scale - 1F).coerceIn(0F, 1F) * (height * .33F) * scale),
                                        ((scale - 1F).coerceIn(0F, 1F) * (height * .33F) * scale)
                                    )
                            } while (event.changes.any { it.pressed })
                        }
                    }
                }
        ) {
            // There are multiple methods available to load an image resource in Compose.
            // However, it would be advisable to use the painterResource method as it loads
            // an image resource asynchronously
            val imagepainter = painterResource(id = R.drawable.jetpack)
            // Image is a pre-defined composable that lays out and draws a given [ImageBitmap].
            // We use the graphicsLayer modifier to modify the scale & translation of the image.
            // This is read from the state properties that we created above.
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offsetX,
                        translationY = offsetY
                    ),
                painter = imagepainter,
                contentDescription = "Landscape Image"
            )
        }
    }

    @Preview
    @Composable
    fun ZoomViewComposablePreview() {
        ZoomViewComposable()
    }
}