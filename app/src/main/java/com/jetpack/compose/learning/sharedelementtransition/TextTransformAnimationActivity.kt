package com.jetpack.compose.learning.sharedelementtransition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

private const val boundsAnimationDurationMillis = 1000

/**
 * BoundsTransform for general shared element transitions
 * Tween animation applied to transition between initial and target bounds
 */
@OptIn(ExperimentalSharedTransitionApi::class)
private val boundsTransform = BoundsTransform { _: Rect, _: Rect ->
    tween(durationMillis = boundsAnimationDurationMillis, easing = FastOutSlowInEasing)
}

/**
 * BoundsTransform specifically designed for text elements with keyframes animation.
 * Defines how text elements transition between initial and target bounds with animation.
 * Uses keyframes to specify animation states over time.
 */
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
private val textBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    keyframes {
        durationMillis = boundsAnimationDurationMillis
        initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
        targetBounds at boundsAnimationDurationMillis
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
class TextTransformAnimationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.text_transform_animation)) },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                }) {
                    TextTransformationAnimationDemo(Modifier.padding(it))
                }
            }
        }
    }

    /**
     * Composable function demonstrating text transformation animation.
     * Manages the state to show details using SharedTransitionLayout and AnimatedContent.
     */
    @Composable
    fun TextTransformationAnimationDemo(modifier: Modifier = Modifier) {
        var showDetails by remember { mutableStateOf(false) }

        SharedTransitionLayout {
            AnimatedContent(
                targetState = showDetails,
                label = "basic_transition"
            ) { targetState ->
                if (targetState) {
                    DetailsContent(
                        modifier = modifier,
                        animatedVisibilityScope = this@AnimatedContent,
                        onBackClick = {
                            showDetails = false
                        }
                    )
                } else {
                    MainContent(
                        modifier = modifier,
                        animatedVisibilityScope = this@AnimatedContent,
                        onShowDetails = {
                            showDetails = true
                        }
                    )
                }
            }
        }
    }

    /**
     * Composable function for displaying the main content screen.
     * Handles shared element transitions for main content items.
     */
    @Composable
    private fun SharedTransitionScope.MainContent(
        modifier: Modifier = Modifier,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onShowDetails: () -> Unit
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Row(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .sharedBounds(
                        rememberSharedContentState(key = "bounds"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        enter = fadeIn(
                            tween(
                                boundsAnimationDurationMillis,
                                easing = FastOutSlowInEasing
                            )
                        ),
                        exit = fadeOut(
                            tween(
                                boundsAnimationDurationMillis,
                                easing = FastOutSlowInEasing
                            )
                        ),
                        boundsTransform = boundsTransform
                    )
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .clickable {
                        onShowDetails()
                    }
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dp10),
                    contentDescription = "Cupcake",
                    modifier = modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "image"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransform
                        )
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Emojis",
                    fontSize = 21.sp,
                    modifier = modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(key = "title"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = textBoundsTransform
                        )
                )
            }
        }
    }

    /**
     * Composable function for displaying the details content screen.
     * Handles shared element transitions for details content items.
     */
    @Composable
    private fun SharedTransitionScope.DetailsContent(
        modifier: Modifier = Modifier,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onBackClick: () -> Unit
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(key = "bounds"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        enter = fadeIn(
                            tween(
                                durationMillis = boundsAnimationDurationMillis,
                                easing = FastOutSlowInEasing
                            )
                        ),
                        exit = fadeOut(
                            tween(
                                durationMillis = boundsAnimationDurationMillis,
                                easing = FastOutSlowInEasing
                            )
                        ),
                        boundsTransform = boundsTransform
                    )
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onBackClick()
                    }
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dp10),
                    contentDescription = "Emojis",
                    modifier = modifier
                        .sharedElement(
                            rememberSharedContentState(key = "image"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = boundsTransform
                        )
                        .size(200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = stringResource(R.string.emojis),
                    fontSize = 28.sp,
                    modifier = modifier
                        .sharedBounds(
                            rememberSharedContentState(key = "title"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = textBoundsTransform
                        )
                )

                Text(
                    text = stringResource(id = R.string.album_description),
                    modifier = modifier.skipToLookaheadSize()
                )
            }
        }
    }
}