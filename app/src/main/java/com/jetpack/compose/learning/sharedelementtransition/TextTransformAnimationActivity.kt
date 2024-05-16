package com.jetpack.compose.learning.sharedelementtransition

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

private const val boundsAnimationDurationMillis = 500

@OptIn(ExperimentalSharedTransitionApi::class)
private val boundsTransform = BoundsTransform { _: Rect, _: Rect ->
    tween(durationMillis = boundsAnimationDurationMillis, easing = FastOutSlowInEasing)
}
@OptIn(ExperimentalSharedTransitionApi::class)
class TextTransformAnimationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                TextTransformationExample()
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun TextTransformationExample() {
        var showDetails by remember {
            mutableStateOf(false)
        }
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Text Transform Animation") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }) {
            SharedTransitionLayout {
                AnimatedContent(
                    showDetails,
                    label = "basic_transition"
                ) { targetState ->
                    if (!targetState) {
                        MainContent(
                            onShowDetails = {
                                showDetails = true
                            },
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout
                        )
                    } else {
                        DetailsContent(
                            onBack = {
                                showDetails = false
                            },
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("RememberReturnType")
    @OptIn(ExperimentalAnimationSpecApi::class)
    @Composable
    private fun MainContent(
        onShowDetails: () -> Unit,
        modifier: Modifier = Modifier,
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope
    ) {
        with(sharedTransitionScope) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
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
                        .border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .background(Color.Gray, RoundedCornerShape(8.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onShowDetails()
                        }
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dp10),
                        contentDescription = "Cupcake",
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = "image"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = boundsTransform
                            )
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    val textBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
                        keyframes {
                            durationMillis = boundsAnimationDurationMillis
                            initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
                            targetBounds at boundsAnimationDurationMillis
                        }
                    }
                    Text(
                        "Cupcake", fontSize = 21.sp,
                        modifier = Modifier.sharedBounds(
                            rememberSharedContentState(key = "title"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = textBoundsTransform
                        )
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalAnimationSpecApi::class)
    @Composable
    private fun DetailsContent(
        modifier: Modifier = Modifier,
        onBack: () -> Unit,
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope
    ) {
        with(sharedTransitionScope) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .padding(top = 200.dp, start = 16.dp, end = 16.dp)
                        .sharedBounds(
                            rememberSharedContentState(key = "bounds"),
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
                        .border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .background(Color.Gray, RoundedCornerShape(8.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onBack()
                        }
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dp10),
                        contentDescription = "Cupcake",
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = "image"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = boundsTransform
                            )
                            .size(200.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    // [START android_compose_shared_element_text_bounds_transform]
                    val textBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
                        keyframes {
                            durationMillis = boundsAnimationDurationMillis
                            initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
                            targetBounds at boundsAnimationDurationMillis
                        }
                    }
                    Text(
                        "Cupcake", fontSize = 28.sp,
                        modifier = Modifier.sharedBounds(
                            rememberSharedContentState(key = "title"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = textBoundsTransform
                        )
                    )
                    // [END android_compose_shared_element_text_bounds_transform]
                    Text(
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sit amet lobortis velit. " +
                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                                " Curabitur sagittis, lectus posuere imperdiet facilisis, nibh massa " +
                                "molestie est, quis dapibus orci ligula non magna. Pellentesque rhoncus " +
                                "hendrerit massa quis ultricies. Curabitur congue ullamcorper leo, at maximus",
                        modifier = Modifier.skipToLookaheadSize()
                    )
                }
            }
        }
    }
}