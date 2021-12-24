package com.jetpack.compose.learning.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.animation.basic.AnimateAsStateChangeColor
import com.jetpack.compose.learning.animation.basic.AnimateAsStateChangeColorWithSize
import com.jetpack.compose.learning.animation.basic.AnimatedVisibilityDemo
import com.jetpack.compose.learning.animation.basic.RotatingSquareComponent
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class BasicAnimation : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                InitView()
            }
        }
    }

    @ExperimentalAnimationApi
    @Composable
    fun InitView() {
        Column(Modifier.background(MaterialTheme.colors.background)) {
            TopAppBar(title = { Text(text = "Basic Animation", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                })
            Spacer(Modifier.height(16.dp))

            LazyColumn {
                item {
                    // Change only box color with animation
                    AnimateAsStateChangeColor()

                    Spacer(Modifier.height(30.dp))

                    // Change box color and size with animation
                    AnimateAsStateChangeColorWithSize()

                    Spacer(Modifier.height(30.dp))

                    // View visible/hide without fadIn animation
                    // Experimental APIs can change in the future or may be removed entirely. (AnimatedVisibility)
                    AnimatedVisibilityDemo(false)

                    Spacer(Modifier.height(30.dp))

                    // View visible/hide with fadIn animation
                    // Experimental APIs can change in the future or may be removed entirely. (AnimatedVisibility)
                    AnimatedVisibilityDemo(true)

                    Spacer(Modifier.height(50.dp))

                    RotatingSquareComponent()

                    Spacer(Modifier.height(20.dp))
                }
            }
        }
    }
}