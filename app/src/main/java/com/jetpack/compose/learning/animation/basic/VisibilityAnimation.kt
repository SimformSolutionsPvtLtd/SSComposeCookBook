package com.jetpack.compose.learning.animation.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

// Experimental APIs can change in the future or may be removed entirely.
@ExperimentalAnimationApi
@Composable
fun AnimatedVisibilityDemo(withFadeIn: Boolean) {
    var visible by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = { visible = !visible }) {
            Text(text = if (visible) "Hide" else "Show")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (withFadeIn) {
            AnimatedVisibility(
                visible = visible, enter = slideInVertically(
                    // Slide in from 40 dp from the top.
                    initialOffsetY = { with(density) { -10.dp.roundToPx() } }) + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ), exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.Black)
                )
            }
        } else {
            AnimatedVisibility(visible) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.Black)
                )
            }
        }
    }
}