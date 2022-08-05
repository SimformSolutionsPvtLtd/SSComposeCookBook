package com.jetpack.compose.learning.viewpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.Text
import androidx.compose.material.IconButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerDefaults
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import dev.chrisbanes.snapper.ExperimentalSnapperApi

/**
 * This class contain example of FlingBehavior to Update viewpager scroll sensibility
 */
class HorizontalPagerWithFlingBehaviorActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appThemeState = appTheme.value, systemUiController = systemUiController) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(getString(R.string.title_pager_with_fling_behavior)) },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed() }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                                }
                            }
                        )
                    }
                ) {
                    PagerWithFlingBehavior(Modifier.padding(it))
                }
            }
        }
    }

    /**
     * This method contain the Horizontal pager with slider
     * when slider value change the new slider value pass to [rememberFlingBehaviorMultiplier]
     * to update the velocity
     */
    @OptIn(ExperimentalPagerApi::class, ExperimentalSnapperApi::class)
    @Composable
    fun PagerWithFlingBehavior(modifier: Modifier = Modifier) {
        val pagerState = rememberPagerState()
        var sliderPosition by remember { mutableStateOf(0f) }
        val imageList = DataProvider.getViewPagerImages()

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            SliderWithLabel(
                value = sliderPosition,
                valueRange = 0f..10f,
                finiteEnd = true,
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                sliderPosition = it.toFloat()
            }
            Text(
                stringResource(R.string.text_slider_description),
                modifier = Modifier.padding(start = 20.dp, bottom = 10.dp)
            )
            HorizontalPager(
                count = imageList.size,
                state = pagerState,
                itemSpacing = 60.dp,
                modifier = Modifier,
                flingBehavior = rememberFlingBehaviorMultiplier(
                    multiplier = sliderPosition,
                    baseFlingBehavior = PagerDefaults.flingBehavior(pagerState)
                )
            ) {
                Image(
                    painter = painterResource(id = imageList[it]),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    /**
     * in FlingBehaviourMultiplier we have multiply the initialVelocity with selected multiplier
     * to increase the scroll sensibility
     */
    private class FlingBehaviourMultiplier(
        private val multiplier: Float,
        private val baseFlingBehavior: FlingBehavior
    ) : FlingBehavior {
        override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
            return with(baseFlingBehavior) {
                performFling(initialVelocity * multiplier)
            }
        }
    }

    @Composable
    fun rememberFlingBehaviorMultiplier(
        multiplier: Float,
        baseFlingBehavior: FlingBehavior
    ): FlingBehavior = remember(multiplier, baseFlingBehavior) {
        FlingBehaviourMultiplier(multiplier, baseFlingBehavior)
    }
}