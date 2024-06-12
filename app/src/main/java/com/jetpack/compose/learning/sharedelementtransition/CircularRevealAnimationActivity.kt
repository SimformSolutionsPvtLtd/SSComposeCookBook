package com.jetpack.compose.learning.sharedelementtransition

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlin.math.hypot

class CircularRevealAnimationActivity: ComponentActivity() {

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
                    CircularRevealLayout(modifier = Modifier)
                }
            }
        }
    }

    @SuppressLint("RememberReturnType")
    @Composable
    fun CircularRevealLayout(
        modifier: Modifier = Modifier,
        isLightTheme: Boolean = !isSystemInDarkTheme()
    ) {
        var isLight by remember { mutableStateOf(isLightTheme) }
        var radius by remember { mutableFloatStateOf(0f) }

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .drawBehind {
                    drawCircle(
                        color = if (isLight) Color.Cyan.copy(0.5f) else Color.Black.copy(0.7f),
                        radius = radius,
                        center = Offset(size.width, size.height),
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            SwitchButton(
                modifier = Modifier
                    .size(72.dp, 48.dp)
                    .semantics {
                        contentDescription =
                            if (isLight) "Switch to dark theme" else "Switch to light theme"
                    },
                checked = !isLight,
                onCheckedChange = {
                    isLight = !isLight
                }
            )
        }
        val animatedRadius = remember { Animatable(0f) }
        val (width, height) = with(LocalConfiguration.current) {
            with(LocalDensity.current) { screenWidthDp.dp.toPx() to screenHeightDp.dp.toPx() }
        }
        val maxRadiusPx = hypot(width, height)
        LaunchedEffect(isLight) {
            animatedRadius.animateTo(maxRadiusPx, animationSpec = tween(1000)) {
                radius = value
            }
            animatedRadius.snapTo(0f)
        }
    }

    @Composable
    fun SwitchButton(
        checked: Boolean,
        modifier: Modifier = Modifier,
        onCheckedChange: () -> Unit
    ) {
        Switch(
            checked = checked,
            modifier = modifier,
            onCheckedChange = { onCheckedChange() }
        )
    }
}