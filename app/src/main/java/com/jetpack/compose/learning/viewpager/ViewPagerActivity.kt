package com.jetpack.compose.learning.viewpager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.model.Component
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
                                IconButton(onClick = { onBackPressed() })
                                { Icon(Icons.Filled.ArrowBack, contentDescription = "") }
                            }
                        )
                    }
                ) {
                    ViewPagerExample(Modifier.padding(it))
                }
            }
        }
    }

    @Composable
    fun ViewPagerExample(modifier: Modifier = Modifier) {
        LazyColumn(
            modifier = modifier
        ) {
            items(getComponent().size) {
                ButtonComponent(
                    text = getComponent()[it].componentName,
                    screen = getComponent()[it].className
                )
            }
        }
    }

    @Composable
    fun ButtonComponent(text: String, screen: Class<*>) {
        Button(
            shape = RoundedCornerShape(12.dp),
            elevation = elevation(
                defaultElevation = 2.dp
            ),
            onClick = {
                startActivity(
                    Intent(
                        this@ViewPagerActivity,
                        screen
                    )
                )
            },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = text,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }

    private fun getComponent(): List<Component> = listOf(
        Component("Horizontal Pager", HorizontalViewPager::class.java),
        Component("Horizontal Pager with Tabs", ViewPagerWithTabActivity::class.java),
        Component("Vertical Pager With Indicator", VerticalViewPagerWithIndicatorActivity::class.java),
        Component("Horizontal Pager With Indicator", HorizontalPagerWithIndicator::class.java),
        Component("Pager with Zoom-in Transformation", ViewPagerWithSwipeAnimationActivity::class.java),
        Component("Pager with Fling Behavior", HorizontalPagerWithFlingBehaviorActivity::class.java),
        Component("Add/Remove Page In Pager", AddRemovePagerActivity::class.java),
    )
}