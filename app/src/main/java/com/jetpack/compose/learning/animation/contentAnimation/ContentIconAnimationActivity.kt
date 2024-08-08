package com.jetpack.compose.learning.animation.contentAnimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class ContentIconAnimationActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
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

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @Composable
    fun InitView() {
        Column(Modifier.background(MaterialTheme.colors.background)) {
            TopAppBar(title = { Text(text = "Content Animation", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                })
            Spacer(Modifier.height(16.dp))

            ContentWithIconAnimation()

            Scaffold(floatingActionButton = {
                FabButtonWithContentAnimation()
            }) { contentPadding ->
                ContentAnimation(
                    modifier = Modifier
                        .padding(contentPadding)
                        .wrapContentHeight()
                )
            }
        }
    }

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @Composable
    fun ContentWithIconAnimation() {
        var expanded by remember { mutableStateOf(false) }
        Surface(
            onClick = { expanded = !expanded },
            border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant),
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp,
            modifier = Modifier.padding(10.dp)
        ) {
            AnimatedContent(targetState = expanded, transitionSpec = {
                fadeIn(
                    animationSpec = tween(
                        150, 150
                    )
                ) with fadeOut(animationSpec = tween(150)) using SizeTransform { initialSize, targetSize ->
                    if (targetState) {
                        keyframes {
                            // Expand horizontally first.
                            IntSize(targetSize.width, initialSize.height) at 150
                            durationMillis = 300
                        }
                    } else {
                        keyframes {
                            // Shrink vertically first.
                            IntSize(initialSize.width, targetSize.height) at 150
                            durationMillis = 300
                        }
                    }
                }
            }) { targetExpanded ->
                if (targetExpanded) {
                    Text(
                        text = getString(R.string.expand_text), modifier = Modifier.padding(15.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone",
                        Modifier.size(50.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun FabButtonWithContentAnimation() {
        var expanded by remember { mutableStateOf(false) }

        TabFloatingActionButton(extended = expanded, onClick = {
            expanded = !expanded
        })
    }
}