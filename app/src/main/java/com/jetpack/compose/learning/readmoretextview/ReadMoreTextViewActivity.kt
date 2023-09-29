package com.jetpack.compose.learning.readmoretextview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccessAlarm
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Scanner
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.readmoretextview.components.ExpandableTextView
import com.jetpack.compose.learning.readmoretextview.data.CollapseConfig
import com.jetpack.compose.learning.readmoretextview.data.ExpandConfig
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController


class ReadMoreTextViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .verticalScroll(rememberScrollState())
                ) {
                    TopAppBar(
                        title = { Text("ReadMoreTextView") },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )

                    Column {
                        Text(text = "Only Action Text", Modifier.padding(start = 10.dp, top = 10.dp), fontSize = 18.sp)
                        ExpandableTextView(
                            text = getString(R.string.expandable_text),
                            enableActionIcons = false,
                            enableActionText = true
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "Only Action Icon", Modifier.padding(horizontal = 10.dp), fontSize = 18.sp)
                        ExpandableTextView(
                            text = getString(R.string.expandable_text),
                            enableActionIcons = true,
                            enableActionText = false
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "Action Text+Icon", Modifier.padding(horizontal = 10.dp), fontSize = 18.sp)
                        ExpandableTextView(
                            text = getString(R.string.expandable_text),
                            enableActionIcons = true,
                            enableActionText = true
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "Custom Action Icon", Modifier.padding(horizontal = 10.dp), fontSize = 18.sp)
                        ExpandableTextView(
                            text = getString(R.string.expandable_text),
                            enableActionIcons = true,
                            enableActionText = true,
                            expandConfig = ExpandConfig(
                                expandText = "Read More",
                                expandTextSize = 16.sp,
                                expandTextColor = Color.Blue,
                                showUnderline = true,
                                expandIcon = Icons.Outlined.AccessAlarm
                            ),
                            collapseConfig = CollapseConfig(
                                collapseText = "Read Less", collapseTextSize = 16.sp, collapseTextColor = Color.Blue, showUnderline = true, collapseIcon = Icons.Outlined.Scanner
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "Custom Action Text", Modifier.padding(horizontal = 10.dp), fontSize = 18.sp)
                        ExpandableTextView(
                            text = getString(R.string.expandable_text),
                            enableActionIcons = true,
                            enableActionText = true,
                            expandConfig = ExpandConfig(
                                expandText = "Show More",
                                expandTextSize = 16.sp,
                                expandTextColor = Color.Blue,
                                showUnderline = true,
                                expandIcon = Icons.Outlined.KeyboardArrowDown
                            ),
                            collapseConfig = CollapseConfig(
                                collapseText = "Show Less", collapseTextSize = 16.sp, collapseTextColor = Color.Blue, showUnderline = true, collapseIcon = Icons.Outlined.KeyboardArrowUp
                            )
                        )




                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "Custom Action Text Color", Modifier.padding(horizontal = 10.dp), fontSize = 18.sp)
                        ExpandableTextView(
                            text = getString(R.string.expandable_text),
                            enableActionIcons = true,
                            enableActionText = true,
                            expandConfig = ExpandConfig(
                                expandText = "Show More",
                                expandTextSize = 16.sp,
                                expandTextColor = Color.Red,
                                showUnderline = true,
                                expandIcon = Icons.Outlined.KeyboardArrowDown
                            ),
                            collapseConfig = CollapseConfig(
                                collapseText = "Show Less", collapseTextSize = 16.sp, collapseTextColor = Color.Green, showUnderline = true, collapseIcon = Icons.Outlined.KeyboardArrowUp
                            )
                        )
                    }
                }
            }
        }
    }
}


