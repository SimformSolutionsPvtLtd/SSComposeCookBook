package com.jetpack.compose.learning.sharedelementtransition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class SearchBoxAnimationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Search box and Animated Visibility Shared Element") },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                }) {
                    SearchBoxAnimation(Modifier.padding(it))
                }
            }
        }
    }

    @Composable
    fun SearchBoxAnimation(modifier: Modifier = Modifier) {
        Column(modifier = modifier.fillMaxSize()) {
            SearchComponent(modifier)
            AnimatedVisibilitySharedElement()
        }
    }

    @Composable
    fun SearchComponent(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .background(Color.LightGray.copy(0.5f))
                .padding(16.dp)
        ) {
            Column {
                SearchBar(hint = "Search Box")
            }
        }
    }

    @Composable
    fun SearchBar(
        hint: String,
        modifier: Modifier = Modifier,
        isEnabled: (Boolean) = true,
        height: Dp = 50.dp,
        backgroundColor: Color = Color.White,
        onTextChange: (String) -> Unit = {},
    ) {
        var text by remember { mutableStateOf(TextFieldValue()) }
        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier = modifier
                .height(height)
                .clip(
                    if (expanded)
                        RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                    else
                        RoundedCornerShape(30.dp)
                )
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = modifier
                    .padding(start = 20.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        expanded = !expanded
                    },
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = "Search",
                tint = MaterialTheme.colors.primary,
            )
            BasicTextField(
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                value = text,
                onValueChange = {
                    text = it
                    onTextChange(it.text)
                },
                enabled = isEnabled,
                textStyle = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                decorationBox = { innerTextField ->
                    if (text.text.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color.Gray.copy(alpha = 0.5f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    innerTextField()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {}),
                singleLine = true
            )
            Box(
                modifier = modifier
                    .size(50.dp)
                    .background(color = Color.Transparent)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (text.text.isNotEmpty()) {
                            text = TextFieldValue(text = "")
                            onTextChange("")
                        }
                    },
            ) {
                if (text.text.isNotEmpty()) {
                    Icon(
                        modifier = modifier
                            .padding(10.dp),
                        painter = painterResource(id = R.drawable.baseline_clear_24),
                        contentDescription = "Search",
                        tint = MaterialTheme.colors.primary,
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.dp8),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            SearchSuggestions(backgroundColor = backgroundColor)
        }
    }

    @Composable
    fun SearchSuggestions(
        modifier: Modifier = Modifier,
        backgroundColor: Color
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                .background(
                    color = backgroundColor
                )
        ) {
            Divider(color = Color.LightGray.copy(alpha = 0.9f))
            Column(modifier = modifier.padding(10.dp)) {
                Text(
                    text = "Recent",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = modifier.height(10.dp))

                LazyColumn(modifier = modifier.padding(10.dp)) {
                    items(DataProvider.getSearchSuggestionsData()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_clear_24),
                                contentDescription = ""
                            )
                            Text(it)
                        }
                    }
                }

                Spacer(modifier = modifier.height(10.dp))

                Text(
                    text = "Contacts",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = modifier.height(10.dp))

                LazyRow {
                    items(DataProvider.getSearchProfiles()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = modifier.padding(5.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dp12),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = modifier
                                    .clip(RoundedCornerShape(60.dp))
                                    .size(80.dp)
                            )
                            Text(
                                text = it,
                                modifier = modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}