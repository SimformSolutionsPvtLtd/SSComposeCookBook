package com.jetpack.compose.learning.viewpager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
                        TopAppBar(
                            title = { Text(getString(R.string.title_view_pager)) },
                            navigationIcon = {
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
        Column(modifier = Modifier.padding(15.dp)) {
            ButtonComponent(
                text = stringResource(R.string.title_horizontal_pager),
                screen = HorizontalViewPager()
            )
            ButtonComponent(
                text = stringResource(R.string.title_horizontal_pager_with_tabs),
                screen = ViewPagerWithTabActivity()
            )
            ButtonComponent(
                text = stringResource(R.string.title_vertical_pager),
                screen = VerticalViewPagerWithIndicatorActivity()
            )
            ButtonComponent(
                text = stringResource(R.string.title_horizontal_pager_with_indicator),
                screen = HorizontalPagerWithIndicator()
            )
            ButtonComponent(
                text = stringResource(R.string.title_pager_with_zoom_effect),
                screen = ViewPagerWithSwipeAnimationActivity()
            )
            ButtonComponent(
                text = stringResource(R.string.title_add_or_remove_page_in_pager),
                screen = AddRemovePagerActivity()
            )
        }
    }

    @Composable
    fun ButtonComponent(text: String, screen: ComponentActivity) {
        Button(
            shape = RoundedCornerShape(12.dp),
            elevation = elevation(
                defaultElevation = 2.dp
            ),
            onClick = {
                startActivity(
                    Intent(
                        this@ViewPagerActivity,
                        screen::class.java
                    )
                )
            },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(text, modifier = Modifier.padding(8.dp))
        }
    }
}