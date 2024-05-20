package com.jetpack.compose.learning.sharedelementtransition

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
                SearchBoxAnimation()
            }
        }
    }

    @SuppressLint("RememberReturnType")
    @Composable
    fun SearchBoxAnimation() {
        Box(
            modifier = Modifier
                .fillMaxSize()
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
        elevation: Dp = 3.dp,
        backgroundColor: Color = Color.White,
        onTextChange: (String) -> Unit = {},
    ) {
        var text by remember { mutableStateOf(TextFieldValue()) }
        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .height(height)
                .fillMaxWidth()
                .shadow(
                    elevation = elevation,
                    shape = if (expanded) RoundedCornerShape(
                        topStart = 10.dp, topEnd = 10.dp
                    ) else RoundedCornerShape(10.dp)
                )
                .background(
                    color = backgroundColor, shape = if (expanded) RoundedCornerShape(
                        topStart = 10.dp, topEnd = 10.dp
                    ) else RoundedCornerShape(10.dp)
                )
                .animateContentSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, indication = null
                ) {
                    expanded = !expanded
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                modifier = modifier
                    .weight(5f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
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
                    .weight(1f)
                    .size(40.dp)
                    .background(color = Color.Transparent, shape = CircleShape)
                    .clickable {
                        if (text.text.isNotEmpty()) {
                            text = TextFieldValue(text = "")
                            onTextChange("")
                        }
                    },
            ) {
                if (text.text.isNotEmpty()) {
                    Icon(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        painter = painterResource(id = R.drawable.baseline_clear_24),
                        contentDescription = "Search",
                        tint = MaterialTheme.colors.primary,
                    )
                } else {
                    Icon(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Search",
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = expanded
        ) {
            Spacer(modifier = Modifier.height(2.dp).background(Color.Gray))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    )
            ) {

                Text(text = "Recent")

                LazyColumn {
                    items(DataProvider.getSearchSuggestionsData()) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_clear_24),
                                contentDescription = ""
                            )
                            Text(it)
                        }
                    }
                }

                Text(text = "Contacts")

                LazyRow {
                    items(DataProvider.getSearchProfiles()) {
                        Column {
                            Icon(
                                painter = painterResource(id = R.drawable.dp12),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(60.dp)
                            )

                            Text(text = it)
                        }
                    }
                }
            }
        }
    }
}