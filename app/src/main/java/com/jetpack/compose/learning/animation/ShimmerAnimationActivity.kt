package com.jetpack.compose.learning.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.animation.ui.theme.ShimmerColorShades
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class ShimmerAnimationActivity : ComponentActivity() {
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

    @Composable
    fun InitView() {
        Column(Modifier.background(MaterialTheme.colors.background)) {
            TopAppBar(title = { Text(text = "Shimmer Animation", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                })
            Spacer(Modifier.height(16.dp))

            /**
             * Lazy column as I am adding multiple items for display purpose
             * create you UI according to requirement
             */
            LazyColumn {

                /**
                 * Lay down the Shimmer Animated item 5 time
                 * [repeat] is like a loop which executes the body according to the number specified
                 */
                repeat(5) {
                    item {
                        ShimmerAnimation()
                    }
                }
            }
        }
    }

    @Composable
    fun ShimmerAnimation(
    ) {

        /**
        Create InfiniteTransition
        which holds child animation like [Transition]
        animations start running as soon as they enter
        the composition and do not stop unless they are removed
         */
        val transition = rememberInfiniteTransition()
        val translateAnim by transition.animateFloat(
            /**
            Specify animation positions,
            initial Values 0F means it starts from 0 position
             */
            initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(

                /**
                 * Tween Animates between values over specified [durationMillis]
                 */
                tween(durationMillis = 1200, easing = FastOutSlowInEasing), RepeatMode.Reverse
            )
        )

        /**
         * Create a gradient using the list of colors
         * Use Linear Gradient for animating in any direction according to requirement
         * start=specifies the position to start with in cartesian like system
         *       Offset(10f,10f) means x(10,0) , y(0,10)
         * end= Animate the end position to give the shimmer effect using the transition created above
         */
        val brush = Brush.linearGradient(
            colors = ShimmerColorShades,
            start = Offset(10f, 10f),
            end = Offset(translateAnim, translateAnim)
        )

        ShimmerItem(brush = brush)

    }

    @Composable
    fun ShimmerItem(
        brush: Brush
    ) {

        /**
         * Column composable shaped like a rectangle,
         * set the [background]'s [brush] with the brush receiving from [ShimmerAnimation]
         * which will get animated.
         * Add few more Composable to test
         */
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
                    .background(
                        brush = brush
                    )
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(vertical = 8.dp)
                    .background(brush = brush)
            )
        }
    }
}