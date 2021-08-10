package com.jetpack.compose.learning.animation.basic

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RotatingSquareComponent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            // rememberInfiniteTransition is used to create a transition that uses infinite
            // child animations. Animations typically get invoked as soon as they enter the
            // composition so don't need to be explicitly started.
            val infiniteTransition = rememberInfiniteTransition()

            // Create a value that is altered by the transition based on the configuration. We use
            // the animated float value the returns and updates a float from the initial value to
            // target value and repeats it (as its called on the infiniteTransition).
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 3000,
                        easing = FastOutLinearInEasing,
                    ),
                )
            )
            // We use the Canvas composable that gives you access to a canvas that you can draw
            // into. We also pass it a modifier.

            // You can think of Modifiers as implementations of the decorators pattern that are used
            // to modify the composable that its applied to. In this example, we assign a size
            // of 200dp to the Canvas using Modifier.preferredSize(200.dp).
            Canvas(modifier = Modifier.size(200.dp)) {
                // As the Transition is changing the interpolating the value of the animated float
                // "rotation", you get access to all the values including the intermediate values as
                // its  being updated. The value of "rotation" goes from 0 to 360 and transitions
                // infinitely due to the infiniteRepeatable animationSpec used above.
                rotate(rotation) {
                    drawRect(color = Color(255, 178, 198))
                }
            }
        })
}

@Preview
@Composable
fun RotatingSquareComponentPreview() {
    RotatingSquareComponent()
}