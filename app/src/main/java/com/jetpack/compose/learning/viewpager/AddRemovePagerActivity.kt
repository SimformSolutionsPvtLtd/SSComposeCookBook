package com.jetpack.compose.learning.viewpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

class AddRemovePagerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appThemeState = appTheme.value, systemUiController = systemUiController) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.title_add_or_remove_page_in_pager)) },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed() }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                                }
                            }
                        )
                    }
                ) {
                    PagerWithSwipeAnimation()
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun PagerWithSwipeAnimation() {

        val pageList = remember { mutableStateListOf(1, 2, 3) }
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        HorizontalPager(
            count = pageList.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 60.dp),
        ) { page ->
            Card(
                shape = RoundedCornerShape(6.dp)
            ) {
                when (page) {
                    0, pageList.size - 1 -> {
                        AddItem(page) {
                            if (it == 0) {
                                scope.launch {
                                    pagerState.scrollToPage(1)
                                }
                            }
                            pageList.add(it, it)
                        }
                    }
                    else -> {
                        DynamicAddedItem(number = page) {
                            pageList.removeAt(it)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun DynamicAddedItem(number: Int, removeItem: (Int) -> Unit) {
        Box(
            modifier = Modifier
                .width(220.dp)
                .height(220.dp)
                .background(MaterialTheme.colors.background)
                .border(1.dp, MaterialTheme.colors.primary)
        ) {
            Text(
                text = "Page $number",
                color = MaterialTheme.colors.primary,
                modifier = Modifier.align(Alignment.Center),
            )
            Icon(
                Icons.Filled.Delete,
                "",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .clickable {
                        removeItem(number)
                    }
            )
        }
    }

    @Composable
    fun AddItem(number: Int, addItem: (Int) -> Unit) {
        Card(
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            modifier = Modifier
                .width(220.dp)
                .height(220.dp)
                .clickable {
                    addItem(number)
                }
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
        }
    }
}