package com.jetpack.compose.learning.sharedelementtransition.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.sharedelementtransition.composables.TabPreviewDetailScreen
import com.jetpack.compose.learning.sharedelementtransition.composables.TabPreviewScreen
import com.jetpack.compose.learning.sharedelementtransition.model.SharedElementModel
import com.jetpack.compose.learning.sharedelementtransition.states.TABPREVIEW
import com.jetpack.compose.learning.sharedelementtransition.states.TABPREVIEWDETAIL
import com.jetpack.compose.learning.sharedelementtransition.states.rememberScreenState
import com.jetpack.compose.learning.sharedelementtransition.toDp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

class SharedElementTransitionActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                TabPreview()
            }
        }
    }

    @Composable
    fun TabPreview() {
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
        ) {
            val screenState = rememberScreenState(constraints)
            val animationScope = rememberCoroutineScope()
            var tabPreviewDetailTransition by remember { mutableStateOf(TABPREVIEWDETAIL.NONE) }
            var isSharedElementTransitioned by remember { mutableStateOf(false) }
            var sharedElementModel by remember { mutableStateOf(SharedElementModel.NONE) }
            val animationProgress = remember { Animatable(0f) }

            fun animate(targetValue: Float) {
                animationScope.launch {
                    animationProgress.animateTo(
                        targetValue = targetValue,
                        animationSpec = tween(500),
                    )
                }
            }

            val handleBackFromTabPreviewDetailScreen: () -> Unit = remember {
                {
                    isSharedElementTransitioned = false
                    animate(0f)
                }
            }

            // Display Tab Preview Screen
            if (screenState.currentScreen == TABPREVIEW.PREVIEW) {
                val density = LocalDensity.current
                TabPreviewScreen(
                    tabInfoList = DataProvider.getTabPreviewItems(),
                    sharedProgress = animationProgress.value,
                    onInfoClick = { data, x, y, size ->
                        sharedElementModel = SharedElementModel(
                            data, x.toDp(density), y.toDp(density), size.toDp(density)
                        )
                        isSharedElementTransitioned = true
                        tabPreviewDetailTransition = TABPREVIEWDETAIL.PREVIEWDETAIL
                        animate(1f)
                    }
                )
            }

            // Display Tab Preview Detail Screen
            if (tabPreviewDetailTransition == TABPREVIEWDETAIL.PREVIEWDETAIL) {
                TabPreviewDetailScreen(
                    maxContentWidth = screenState.maxContentWidth,
                    sharedElementModel = sharedElementModel,
                    transitioned = isSharedElementTransitioned,
                    onTransitionFinished = {
                        if (!isSharedElementTransitioned) {
                            tabPreviewDetailTransition = TABPREVIEWDETAIL.NONE
                        }
                    },
                    onBackClick = handleBackFromTabPreviewDetailScreen
                )
            }
        }
    }
}