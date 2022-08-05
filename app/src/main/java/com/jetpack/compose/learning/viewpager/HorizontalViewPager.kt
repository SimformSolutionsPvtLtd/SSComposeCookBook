package com.jetpack.compose.learning.viewpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.Checkbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

/**
 * this class contain example of horizontal viewpager, enable/disable swipe,
 * change swipe direction and swipe horizontal viewpager with controllers
 */
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
                                IconButtonComponent(icon = Icons.Filled.ArrowBack) {
                                    onBackPressed()
                                }
                            }
                        )
                    }
                ) {
                    SimpleHorizontalViewPager(Modifier.padding(it))
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun SimpleHorizontalViewPager(modifier: Modifier = Modifier) {
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()
        var isReversePageChecked by remember { mutableStateOf(false) }
        var isDisableSwipeChecked by remember { mutableStateOf(false) }
        val imageList = DataProvider.getViewPagerImages()

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                CheckBoxComponent(
                    text = stringResource(id = R.string.text_enable_reverse_pager),
                    checked = isReversePageChecked
                ) {
                    isReversePageChecked = !isReversePageChecked
                }
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                CheckBoxComponent(
                    text = stringResource(id = R.string.text_disable_swipe),
                    checked = isDisableSwipeChecked
                ) {
                    isDisableSwipeChecked = !isDisableSwipeChecked
                }
            }
            HorizontalPager(
                count = imageList.size,
                state = pagerState,
                reverseLayout = isReversePageChecked,
                itemSpacing = 60.dp,
                modifier = Modifier.disabledHorizontalPointerInputScroll(isDisableSwipeChecked)
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
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)
            ) {
                IconButtonComponent(Icons.Filled.SkipPrevious) {
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
                IconButtonComponent(Icons.Filled.ArrowBackIos) {
                    scope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                }
                IconButtonComponent(Icons.Filled.ArrowForwardIos) {
                    scope.launch {
                        if (pagerState.currentPage < imageList.size - 1)
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
                IconButtonComponent(Icons.Filled.SkipNext) {
                    scope.launch {
                        pagerState.animateScrollToPage(imageList.size - 1)
                    }
                }
            }
        }
    }

    @Composable
    fun CheckBoxComponent(text: String, checked: Boolean, onCheckChange: () -> Unit) {
        Checkbox(checked = checked, onCheckedChange = { onCheckChange() })
        Text(text = text)
    }

    @Composable
    fun IconButtonComponent(icon: ImageVector, onIconClick: () -> Unit) {
        IconButton(onClick = { onIconClick() }) {
            Icon(icon, "")
        }
    }
}