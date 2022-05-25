package com.jetpack.compose.learning.viewpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                SimpleHorizontalViewPager()
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
    @Composable
    fun SimpleHorizontalViewPager() {
        var swipePagerState by remember { mutableStateOf(false) }
        var reverseSwipePagerState by remember { mutableStateOf(false) }
        val numberPattern = remember { Regex("[0-9]*") }
        val pagerState = rememberPagerState()
        var itemSpacingText by remember { mutableStateOf(TextFieldValue()) }
        val keyBoardController = LocalSoftwareKeyboardController.current

        Column {
            TopAppBar(
                title = { Text(stringResource(id = R.string.title_horizontal_pager)) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            Row {
                RadioButtonSwipeEnable("Enable swipe", selected = swipePagerState) {
                    swipePagerState = !swipePagerState
                }
                RadioButtonSwipeEnable(
                    text = "Enable Reverse Swipe",
                    selected = reverseSwipePagerState
                ) {

                    reverseSwipePagerState = !reverseSwipePagerState
                }
            }
            OutlinedTextField(
                value = itemSpacingText,
                onValueChange = {
                    if (it.text.length <= 2 && it.text.matches(numberPattern)) {
                        itemSpacingText = it
                    }
                },
                placeholder = {
                    Text("Enter Item Spacing")
                },

                modifier = Modifier.align(Alignment.CenterHorizontally),
                keyboardActions = KeyboardActions(
                    onDone = { keyBoardController?.hide() }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )
            HorizontalPager(
                count = 4,
                state = pagerState,
                userScrollEnabled = swipePagerState || reverseSwipePagerState,
                reverseLayout = reverseSwipePagerState,
                itemSpacing = if (itemSpacingText.text.isEmpty()) 0.dp else itemSpacingText.text.toInt().dp,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxSize(),
            ) {
                Text(
                    text = "Page $currentPage",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Gray),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    fun RadioButtonSwipeEnable(text: String, selected: Boolean, onStateChange: () -> Unit) {
        Row {
            RadioButton(
                selected = selected, onClick = { onStateChange() },
                enabled = true, colors = RadioButtonDefaults.colors()
            )
            Text(
                text, modifier = Modifier
                    .padding(vertical = 11.dp)
                    .clickable {
                        onStateChange()
                    }
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

