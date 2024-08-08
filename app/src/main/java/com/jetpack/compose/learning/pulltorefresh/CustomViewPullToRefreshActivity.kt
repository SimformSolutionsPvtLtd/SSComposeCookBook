package com.jetpack.compose.learning.pulltorefresh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.delay

class CustomViewPullToRefreshActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainContent()
            }
        }
    }

    @Preview
    @Composable
    fun MainContent() {

        val items = remember { mutableStateListOf<Int>() }
        items.addAll(0..10)
        var isRefreshing by remember { mutableStateOf(false) }
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)


        Scaffold(topBar = {
            TopAppBar(
                    title = { Text("Custom View Pull To Refresh") },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
            )
        }) { contentPadding ->
            Column(modifier = Modifier.padding(contentPadding)) {
                SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = {
                            isRefreshing = true
                        },
                        indicator = { state, trigger ->
                            CustomPullRefreshView(state, trigger)
                        }) {
                    LazyColumn(
                            contentPadding = PaddingValues(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(items.size) {
                            ColumnItem(number = items[it])
                        }
                    }
                    LaunchedEffect(isRefreshing) {
                        if (isRefreshing) {
                            delay(1000L)
                            items.clear()
                            items.addAll((1..10).map { (0..100).random() })
                            isRefreshing= false
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ColumnItem(number: Int) {
        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colors.background)
                        .border(1.dp, MaterialTheme.colors.primary),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(text = "Item Number $number", color = MaterialTheme.colors.primary)
        }
    }

    @Composable
    fun CustomPullRefreshView(
            swipeRefreshState: SwipeRefreshState,
            refreshTriggerDistance: Dp,
            color: Color = MaterialTheme.colors.primary,
    ) {
        Box(
                Modifier
                        .drawWithCache {
                            onDrawBehind {
                                val distance = refreshTriggerDistance.toPx()
                                val progress = (swipeRefreshState.indicatorOffset / distance).coerceIn(0f, 1f)
                                // We draw a translucent glow
                                val brush = Brush.verticalGradient(
                                        0f to color.copy(alpha = 0.45f),
                                        1f to color.copy(alpha = 0f)
                                )
                                // And fade the glow in/out based on the swipe progress
                                drawRect(brush = brush, alpha = FastOutSlowInEasing.transform(progress))
                            }
                        }
                        .fillMaxWidth()
                        .height(80.dp)
        ) {
            if (swipeRefreshState.isRefreshing) {
                // If we're refreshing, show an indeterminate progress indicator
                LinearProgressIndicator(Modifier.fillMaxWidth())
            } else {
                // Otherwise we display a determinate progress indicator with the current swipe progress
                val trigger = with(LocalDensity.current) { refreshTriggerDistance.toPx() }
                val progress = (swipeRefreshState.indicatorOffset / trigger).coerceIn(0f, 1f)
                LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }

}