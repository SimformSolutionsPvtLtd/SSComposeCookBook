package com.jetpack.compose.learning.viewpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

/**
 * This class contain example of Auto Scroll ViewPager with zoom-in transformation
 */
class ViewPagerWithSwipeAnimationActivity : ComponentActivity() {

    private val maxAutoScrollDelay = 2000
    private val minAutoScrollDelay = 400
    private val autoScrollChangeValue = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    stringResource(id = R.string.title_pager_with_zoom_effect),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed() }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                                }
                            }
                        )
                    }
                ) {
                    PagerWithSwipeAnimation(Modifier.padding(it))
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun PagerWithSwipeAnimation(modifier: Modifier = Modifier) {
        val pagerState = rememberPagerState(initialPage = 0)
        var scrollDelay by remember { mutableStateOf(minAutoScrollDelay) }
        val imageList = DataProvider.getViewPagerImages()

        /**
         * We have use the [LaunchedEffect] to scroll viewpager after certain delay
         */
        LaunchedEffect(pagerState.currentPage) {
            delay(scrollDelay.toLong())
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }

        Box {
            PreviewHorizontalPager(
                pageCount = Int.MAX_VALUE,
                pagerState = pagerState,
                modifier = modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
            ) {
                val index = it.infiniteScrollIndex(imageList.size)
                Card(
                    modifier = Modifier
                        .graphicsLayer {
                            val pagerOffset =
                                calculateCurrentOffsetForPage(currentPage).absoluteValue
                            lerp(
                                start = 0.85.dp,
                                stop = 1.dp,
                                fraction = 1f - pagerOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale.value
                                scaleY = scale.value
                            }
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
            ChangeAutoScrollDelayComponent(
                scrollDelay,
                Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
                    .align(Alignment.TopCenter)
            ) {
                scrollDelay = it
            }
        }
    }

    @Composable
    fun ChangeAutoScrollDelayComponent(
        scrollDelay: Int,
        modifier: Modifier = Modifier,
        onDelayChange: (Int) -> Unit,
    ) {
        val context = LocalContext.current
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            IconComponent(Icons.Default.Add) {
                if (scrollDelay < maxAutoScrollDelay) {
                    onDelayChange(scrollDelay + autoScrollChangeValue)
                } else {
                    showToastMessage(context, context.getString(R.string.validation_max_auto_scroll_delay))
                }
            }
            Text("Delay :- $scrollDelay", modifier = Modifier.padding(10.dp), fontSize = 16.sp)
            IconComponent(Icons.Default.Remove) {
                if (scrollDelay > minAutoScrollDelay) {
                    onDelayChange(scrollDelay - autoScrollChangeValue)
                } else {
                    showToastMessage(context, context.getString(R.string.validation_min_auto_scroll_delay))
                }
            }
        }
    }

    @Composable
    private fun IconComponent(icon: ImageVector, updateDelay: () -> Unit) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary)
        ) {
            Icon(icon, contentDescription = "", modifier = Modifier
                .padding(10.dp)
                .clickable {
                    updateDelay()
                })
        }
    }
}