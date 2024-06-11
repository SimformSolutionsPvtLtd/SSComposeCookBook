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
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.sharedelementtransition.model.ProfileModel
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

private const val boundsAnimationDurationMillis = 600

@OptIn(ExperimentalSharedTransitionApi::class)
private val boundsTransform = BoundsTransform { _: Rect, _: Rect ->
    tween(durationMillis = boundsAnimationDurationMillis, easing = FastOutSlowInEasing)
}

class FabComponentAnimationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appThemeState = appTheme.value, systemUiController = systemUiController) {
                FabComponentAnimation()
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun FabComponentAnimation() {
        var showDetails by remember { mutableStateOf(false) }

        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Fab Component Animation") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }) {
            SharedTransitionLayout {
                AnimatedContent(
                    targetState = showDetails,
                    label = "basic_transition"
                ) { targetState ->
                    if (targetState) {
                        DetailsContent(animatedVisibilityScope = this) {
                            showDetails = false
                        }
                    } else {
                        MainContent(animatedVisibilityScope = this) {
                            showDetails = true
                        }
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.MainContent(
        animatedVisibilityScope: AnimatedVisibilityScope,
        onShowDetails: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, top = 20.dp)
                .size(70.dp)
                .background(Color.Cyan, shape = RoundedCornerShape(20.dp))
                .skipToLookaheadSize()
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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onShowDetails()
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(50.dp)
                    .skipToLookaheadSize()
                    .sharedBounds(
                        rememberSharedContentState(key = "card_bounds"),
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
                    ),
                backgroundColor = Color.Cyan,
                elevation = 0.dp
            ) {
                Image(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = ""
                )
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.DetailsContent(
        animatedVisibilityScope: AnimatedVisibilityScope,
        onBack: () -> Unit
    ) {
        Column(modifier = Modifier
            .padding(top = 16.dp, start = 16.dp)
            .skipToLookaheadSize()
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
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onBack()
            }
        ) {
            LazyColumn(modifier = Modifier
                .width(160.dp)
                .skipToLookaheadSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.LightGray.copy(alpha = 0.5f))
                .clickable {
                    onBack()
                }
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                            .height(70.dp)
                            .fillMaxWidth()
                            .background(Color.Cyan.copy(0.5f))
                            .clickable { onBack() }
                            .skipToLookaheadSize()
                            .sharedBounds(
                                rememberSharedContentState(key = "card_bounds"),
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
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(20.dp))

                        Image(
                            modifier = Modifier
                                .size(50.dp),
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        Text(text = "Edit", fontSize = 14.sp)
                    }
                }
                items(DataProvider.getFabProfiles()) { profile: ProfileModel ->
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(25.dp)),
                            painter = painterResource(id = profile.image),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = profile.name, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}