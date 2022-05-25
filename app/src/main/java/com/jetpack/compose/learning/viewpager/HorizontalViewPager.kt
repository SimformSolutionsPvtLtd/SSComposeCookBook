package com.jetpack.compose.learning.viewpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class HorizontalViewPager : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appThemeState = appTheme.value, systemUiController = systemUiController) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(id = R.string.title_horizontal_pager)) },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed() }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                                }
                            }
                        )
                    }
                ) {
                    SimpleHorizontalViewPager()
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun SimpleHorizontalViewPager() {

        val pagerState = rememberPagerState()
        var reverseLayoutState by remember { mutableStateOf(false) }
        val imageList = listOf(
            R.drawable.dp1,
            R.drawable.dp2,
            R.drawable.dp3,
            R.drawable.dp4
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                RadioButton(
                    selected = reverseLayoutState,
                    onClick = { reverseLayoutState = !reverseLayoutState })
                Text(text = "Enable Reverse Pager")
            }
            HorizontalPager(
                count = imageList.size,
                state = pagerState,
                reverseLayout = reverseLayoutState,
                itemSpacing = 60.dp,
                modifier = Modifier,
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
}

