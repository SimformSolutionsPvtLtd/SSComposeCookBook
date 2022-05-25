package com.jetpack.compose.learning.viewpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.VerticalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import com.jetpack.compose.learning.R

class VerticalViewPagerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                VerticalViewPager()
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun VerticalViewPager() {
        val pagerState = rememberPagerState()
        val verticalPagerList = listOf(
            "Page 0" to Color.White,
            "Page 1" to Color.Blue,
            "Page 2" to Color.Gray
        )

        Column {
            AppBar()
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                VerticalPager(count = verticalPagerList.size, state = pagerState) {
                    Text(
                        text = verticalPagerList[pagerState.currentPage].first,
                        modifier = Modifier
                            .background(color = verticalPagerList[pagerState.currentPage].second)
                            .fillMaxHeight()
                            .fillMaxWidth(fraction = 0.95f),
                        fontSize = 18.sp
                    )
                }
                VerticalPagerIndicator(pagerState = pagerState)
            }
        }
    }

    @Composable
    fun AppBar() {
        TopAppBar(
            title = { Text(stringResource(id = R.string.title_vertical_pager)) },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
    }
}