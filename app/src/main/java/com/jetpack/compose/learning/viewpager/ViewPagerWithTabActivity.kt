package com.jetpack.compose.learning.viewpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

class ViewPagerWithTabActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appThemeState = appTheme.value, systemUiController = systemUiController) {
                ViewPagerWithTab()
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun ViewPagerWithTab() {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        val tabIndex = pagerState.currentPage
        val tabList = listOf(
            "MUSIC" to Icons.Filled.Home,
            "MARKET" to Icons.Filled.ShoppingCart,
            "FILMS" to Icons.Filled.AccountBox,
            "BOOK" to Icons.Filled.Settings
        )

        Column {
            AppBar()
            TabRow(selectedTabIndex = tabIndex,
                indicator = { tabPosition ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPosition[tabIndex])
                    )
                }
            ) {
                tabList.forEachIndexed { index, pair ->
                    Tab(selected = index == tabIndex,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(pair.first, color = Color.White)
                        },
                        icon = {
                            pair.second
                        }
                    )
                }
            }
            HorizontalPager(
                count = tabList.count(),
                state = pagerState,
            ) {
                Text(
                    tabList[pagerState.currentPage].first,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

    }

    @Composable
    fun AppBar() {
        TopAppBar(
            title = { Text(stringResource(id = R.string.title_horizontal_pager_with_tabs)) },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
    }
}