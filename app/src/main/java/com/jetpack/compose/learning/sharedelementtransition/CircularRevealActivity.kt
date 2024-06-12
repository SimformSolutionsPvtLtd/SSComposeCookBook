package com.jetpack.compose.learning.sharedelementtransition

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Button
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
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class CircularRevealActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Shared Element Transition") },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed() }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "")
                                }
                            }
                        )
                    }
                ) {
                    CircularRevealAnimation()
                }
            }
        }
    }

    @Composable
    fun CircularRevealAnimation() {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            var visible by remember { mutableStateOf(false) }

            val animationProgress by animateFloatAsState(
                targetValue = if (visible) 1f else 0f,
                animationSpec = tween(durationMillis = 200, easing = LinearEasing)
            )
            val transition = updateTransition(targetState = animationProgress, label = "")

            val animatedShape by transition.animateValue(
                TwoWayConverter(
                    convertToVector = { AnimationVector1D(0f) },
                    convertFromVector = { GenericShape { _, _ -> } }
                ),
                label = ""
            ) { progress ->
                GenericShape { size, _ ->
                    val centerH = size.width / 2f
                    val multiplierW = 1.5f + size.height / size.width

                    moveTo(
                        x = centerH - centerH * progress * multiplierW,
                        y = size.height,
                    )

                    val currentWidth = (centerH * progress * multiplierW * 2.5f)

                    cubicTo(
                        x1 = centerH - centerH * progress * 1.5f,
                        y1 = size.height - currentWidth * 0.5f,
                        x2 = centerH + centerH * progress * 1.5f,
                        y2 = size.height - currentWidth * 0.5f,
                        x3 = centerH + centerH * progress * multiplierW,
                        y3 = size.height,
                    )

                    close()
                }
            }

            if(animationProgress != 0f) {
                BottomSheet(
                    modifier = Modifier
                        .padding(8.dp)
                        .graphicsLayer {
                            clip = true
                            shape = animatedShape
                        }
                )
            }

            Button(
                onClick = { visible = !visible },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Expand")
            }
        }
    }
}