package com.jetpack.compose.learning.sharedelementtransition.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.sharedelementtransition.lerp
import com.jetpack.compose.learning.sharedelementtransition.model.TabInfoModel
import com.jetpack.compose.learning.sharedelementtransition.model.HeaderParams
import com.jetpack.compose.learning.sharedelementtransition.model.SharedElementModel
import com.jetpack.compose.learning.sharedelementtransition.model.SharedElementParams
import com.jetpack.compose.learning.sharedelementtransition.toPx
import kotlinx.coroutines.launch

const val ANIM_DURATION = 500

@Composable
fun TabPreviewDetailScreen(
    maxContentWidth: Int,
    sharedElementModel: SharedElementModel,
    transitioned: Boolean,
    onTransitionFinished: () -> Unit,
    onBackClick: () -> Unit
) {
    val sharedElementTargetSize = 230.dp

    TabPreviewDetailScreen(
        tabInfo = sharedElementModel.tabInfo,
        isAppearing = transitioned,
        sharedElementParams = SharedElementParams(
            initialOffset = Offset(
                sharedElementModel.offsetX.toPx(LocalDensity.current).toFloat(),
                sharedElementModel.offsetY.toPx(LocalDensity.current).toFloat(),
            ),
            targetOffset = Offset(
                x = (maxContentWidth - sharedElementTargetSize.toPx(LocalDensity.current)) / 2f,
                y = 0f
            ),
            initialSize = sharedElementModel.size,
            targetSize = sharedElementTargetSize,
            initialCornerRadius = 10.dp,
            targetCornerRadius = sharedElementTargetSize / 2
        ),
        onBackClick = onBackClick,
        onTransitionFinished = onTransitionFinished
    )
}

@Composable
fun TabPreviewDetailScreen(
    modifier: Modifier = Modifier,
    tabInfo: TabInfoModel,
    sharedElementParams: SharedElementParams,
    isAppearing: Boolean,
    onTransitionFinished: () -> Unit,
    onBackClick: () -> Unit
) {
    val density = LocalDensity.current

    val sharedElementProgress = remember { Animatable(if (isAppearing) 0f else 1f) }
    val titleProgress = remember { Animatable(if (isAppearing) 0f else 1f) }
    val bgColorProgress = remember { Animatable(if (isAppearing) 0f else 1f) }
    val offsetProgress = remember { Animatable(if (isAppearing) 0f else 1f) }
    val cornersProgress = remember { Animatable(if (isAppearing) 0f else 1f) }

    val headerParams = remember {
        HeaderParams(
            sharedElementParams = sharedElementParams,
            coverId = tabInfo.cover,
            title = tabInfo.title,
            author = tabInfo.author
        )
    }

    LaunchedEffect(key1 = isAppearing) {
        launch {
            sharedElementProgress.animateTo(
                if (isAppearing) 1f else 0f,
                animationSpec = tween(ANIM_DURATION)
            )
            onTransitionFinished()
        }

        launch {
            titleProgress.animateTo(
                if (isAppearing) 1f else 0f,
                animationSpec = tween(
                    durationMillis = ANIM_DURATION / 2,
                    delayMillis = if (isAppearing) ANIM_DURATION / 2 else 0
                )
            )
        }
        launch {
            bgColorProgress.animateTo(
                if (isAppearing) 1f else 0f,
                animationSpec = tween(
                    durationMillis = ANIM_DURATION,
                )
            )
        }
        launch {
            offsetProgress.animateTo(
                targetValue = if (isAppearing) 1f else 0f,
                animationSpec = tween(ANIM_DURATION),
            )
            onTransitionFinished()
        }
        launch {
            cornersProgress.animateTo(
                targetValue = if (isAppearing) 1f else 0f,
                animationSpec = tween(2 * ANIM_DURATION / 3),
            )
        }
    }

    val surfaceMaterialColor = MaterialTheme.colors.surface
    val surfaceMaterialColorTransparent = surfaceMaterialColor.copy(alpha = 0f)
    val surfaceColor = remember {
        derivedStateOf {
            lerp(
                surfaceMaterialColorTransparent,
                surfaceMaterialColor,
                bgColorProgress.value
            )
        }
    }

    val contentAlphaState = titleProgress.asState()

    val cornersSize = lerp(
        headerParams.sharedElementParams.initialCornerRadius,
        headerParams.sharedElementParams.targetCornerRadius,
        cornersProgress.value,
    )

    val currentSize = lerp(
        headerParams.sharedElementParams.initialSize,
        headerParams.sharedElementParams.targetSize,
        offsetProgress.value
    )

    val currentOffset = lerp(
        headerParams.sharedElementParams.initialOffset.copy(y = headerParams.sharedElementParams.initialOffset.y),
        Offset(
            x = (LocalConfiguration.current.screenWidthDp / 2).toFloat(),
            y = 128.dp.toPx(density).toFloat()
        ),
        offsetProgress.value
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor.value,
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SharedElementContainer(
                coverOffset = currentOffset,
                coverSize = currentSize,
                coverCornersRadius = cornersSize,
                topMenu = {
                    TopMenu(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .graphicsLayer { alpha = contentAlphaState.value }
                            .align(Alignment.TopCenter)
                            .padding(top = 24.dp)
                            .statusBarsPadding(),
                        title = stringResource(id = R.string.tab_preview_detail_title),
                        iconsTint = MaterialTheme.colors.onSurface,
                        endIconResId = R.drawable.baseline_share_24,
                        onStartIconClick = onBackClick
                    )
                },
                labels = {
                    Labels(
                        title = headerParams.title,
                        author = headerParams.author,
                        alphaProvider = contentAlphaState
                    )
                },
                sharedElement = {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f),
                        painter = painterResource(id = headerParams.coverId),
                        contentDescription = "",
                    )
                }
            )
        }
    }
}

@Composable
fun BoxScope.Labels(
    title: String,
    author: String,
    alphaProvider: State<Float>,
) {
    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.graphicsLayer { alpha = alphaProvider.value },
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = author,
            modifier = Modifier.graphicsLayer { alpha = alphaProvider.value },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface,
        )
    }
}
