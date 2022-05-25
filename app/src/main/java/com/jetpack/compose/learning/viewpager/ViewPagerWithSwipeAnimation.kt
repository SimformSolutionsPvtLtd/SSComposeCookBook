package com.jetpack.compose.learning.viewpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.math.absoluteValue


class ViewPagerWithSwipeAnimation : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Column {
                    TopBar()
                    PagerWithSwipeAnimation()
                }
            }
        }
    }

    @Composable
    fun TopBar() {
        TopAppBar(
            title = { Text(stringResource(id = R.string.title_pager_with_zoom_effect)) },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
    }

    @OptIn(ExperimentalPagerApi::class, InternalCoroutinesApi::class)
    @Composable
    fun PagerWithSwipeAnimation() {
        val imageList = listOf(
            R.drawable.dp1,
            R.drawable.dp2,
            R.drawable.dp3,
            R.drawable.dp4,
            R.drawable.dp5,
            R.drawable.dp6,
            R.drawable.dp7
        )
        val pagerState = rememberPagerState(initialPage = 0)

        HorizontalPager(
            count = Int.MAX_VALUE,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 60.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            val index = it.infiniteScrollIndex(imageList.size)
            Card(
                modifier = Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pagerOffset = calculateCurrentOffsetForPage(currentPage).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85.dp,
                            stop = 1.dp,
                            fraction = 1f - pagerOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale.value
                            scaleY = scale.value
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5.dp,
                            stop = 1.dp,
                            fraction = 1f - pagerOffset.coerceIn(0f, 1f)
                        ).value
                    }
                    .width(if (index == (currentPage % imageList.size)) 260.dp else 220.dp)
                    .height(if (index == (currentPage % imageList.size)) 260.dp else 220.dp)
            ) {
                Image(
                    painter = painterResource(id = imageList[index]),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    private fun Int.infiniteScrollIndex(itemCount: Int): Int = when (itemCount) {
        0 -> this
        else -> this - floorDiv(itemCount) * itemCount
    }
}