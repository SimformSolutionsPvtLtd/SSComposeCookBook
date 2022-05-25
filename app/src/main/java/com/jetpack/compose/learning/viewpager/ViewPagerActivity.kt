package com.jetpack.compose.learning.viewpager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class ViewPagerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("View Pager") }, navigationIcon = {
                            IconButton(
                                onClick = { onBackPressed() },
                                enabled = true
                            ) { Icon(Icons.Filled.ArrowBack, contentDescription = "") }
                        }
                        )
                    }
                ) {
                    ViewPagerExample()
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun ViewPagerExample() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            NavigationButton(text = stringResource(R.string.title_horizontal_pager) , screen = HorizontalViewPager())
            NavigationButton(text = stringResource(R.string.title_horizontal_pager_with_tabs) , screen = ViewPagerWithTabActivity())
            NavigationButton(text = stringResource(R.string.title_vertical_pager) , screen = VerticalViewPagerActivity())
            NavigationButton(text = stringResource(R.string.title_pager_with_zoom_effect), screen = ViewPagerWithSwipeAnimation())
        }
    }

    @Composable
    fun NavigationButton(text: String, screen: ComponentActivity) {
        OutlinedButton(
            onClick = {
                startActivity(
                    Intent(
                        this@ViewPagerActivity,
                        screen::class.java
                    )
                )
            },
            modifier = Modifier.padding(vertical = 15.dp)
        ) {
            Text(text, modifier = Modifier.padding(6.dp))
        }
    }
}