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
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

private const val boundsAnimationDurationMillis = 700

@OptIn(ExperimentalSharedTransitionApi::class)
private val boundsTransform = BoundsTransform { _: Rect, _: Rect ->
    tween(durationMillis = boundsAnimationDurationMillis, easing = FastOutSlowInEasing)
}


class SheetAnimationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                SheetAnimation()
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SheetAnimation() {
        var showDetails by remember { mutableStateOf(false) }

        SharedTransitionLayout(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxSize()
        ) {
            AnimatedContent(targetState = showDetails, label = "transition") { targetState ->
                if (targetState) {
                    DetailContent(this@AnimatedContent) {
                        showDetails = false
                    }
                } else {
                    MainContent(this@AnimatedContent) {
                        showDetails = true
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .height(100.dp)
                .background(Color.White)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onShowDetails()
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .sharedBounds(
                        rememberSharedContentState(key = "bounds"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = boundsTransform
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_post_image_6),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .size(height = 100.dp, width = 80.dp)
                        .sharedElement(
                            rememberSharedContentState(key = "image"),
                            animatedVisibilityScope
                        ),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.padding(vertical = 15.dp)) {
                    Text(
                        text = "Angel Beach",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Trilogy",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif,
                    )
                }
                Spacer(modifier = Modifier.weight(2f))

                Row {
                    Image(imageVector = Icons.Filled.SkipPrevious, contentDescription = "")
                    Image(imageVector = Icons.Filled.PlayCircleFilled, contentDescription = "")
                    Image(imageVector = Icons.Filled.SkipNext, contentDescription = "")
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.DetailContent(
        animatedVisibilityScope: AnimatedVisibilityScope,
        onShowDetails: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .clickable { onShowDetails() }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.sharedBounds(
                    rememberSharedContentState(key = "bounds"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform
                )
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_post_image_6),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .size(300.dp)
                        .sharedElement(
                            rememberSharedContentState(key = "image"),
                            animatedVisibilityScope, boundsTransform
                        ),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Angel Beach",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp
                )

                Text(
                    text = "Trilogy",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Image(
                        imageVector = Icons.Filled.SkipPrevious,
                        contentDescription = "",
                        modifier = Modifier.size(80.dp)
                    )
                    Image(
                        imageVector = Icons.Filled.PlayCircleFilled,
                        contentDescription = "",
                        modifier = Modifier.size(80.dp)
                    )
                    Image(
                        imageVector = Icons.Filled.SkipNext,
                        contentDescription = "",
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }
    }
}