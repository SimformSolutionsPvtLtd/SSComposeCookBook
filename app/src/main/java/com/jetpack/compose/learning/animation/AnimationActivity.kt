package com.jetpack.compose.learning.animation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.animation.contentAnimation.ContentIconAnimationActivity
import com.jetpack.compose.learning.model.Component
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class AnimationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                val systemUiController = remember { SystemUiController(window) }
                val appTheme = remember { mutableStateOf(AppThemeState()) }
                BaseView(appTheme.value, systemUiController) {
                    Scaffold(topBar = {
                        TopAppBar(
                            title = { Text("Animations") },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed() }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                                }
                            }
                        )
                    }) { contentPadding ->
                        Spacer(Modifier.height(16.dp))
                        LazyColumn(Modifier.padding(contentPadding)) {
                            items(getComponents()) {
                                ButtonComponent(it.componentName, it.className)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ButtonComponent(buttonText: String, className: Class<*>) {
        val context = LocalContext.current
        Button(
            onClick = {
                context.startActivity(Intent(applicationContext, className))
            }, modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = buttonText,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }

    private fun getComponents(): List<Component> = listOf(
        Component("BasicAnimation", BasicAnimation::class.java),
        Component("ContentAnimation", ContentIconAnimationActivity::class.java),
        Component("GestureAnimation", GestureAnimationActivity::class.java),
        Component("InfiniteAnimation", InfiniteTransitionActivity::class.java),
        Component("ShimmerAnimation", ShimmerAnimationActivity::class.java),
        Component("TabBarAnimation", TabBarAnimationActivity::class.java)
    )
}