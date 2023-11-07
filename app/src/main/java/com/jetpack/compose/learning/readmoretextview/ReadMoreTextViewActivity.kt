package com.jetpack.compose.learning.readmoretextview

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.readmoretextview.components.ReadMoreTextView
import com.jetpack.compose.learning.readmoretextview.components.TitleTextView
import com.jetpack.compose.learning.readmoretextview.data.CollapseConfig
import com.jetpack.compose.learning.readmoretextview.data.ExpandConfig
import com.jetpack.compose.learning.readmoretextview.ui.RecyclerViewActivity
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.Josefin
import com.jetpack.compose.learning.theme.SystemUiController

class ReadMoreTextViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            val mContext = LocalContext.current

            BaseView(appTheme.value, systemUiController) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .verticalScroll(rememberScrollState())
                ) {
                    TopAppBar(
                        title = { Text(stringResource(id = R.string.read_more_text_view)) },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )

                    // RecyclerView Example
                    Button(
                        onClick = {
                            mContext.startActivity(Intent(applicationContext, RecyclerViewActivity::class.java))
                        }, modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = stringResource(id = R.string.recyclerview_usage_example),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }

                    Column {

                        // Expand & Collapse with only Text
                        TitleTextView(titleText = stringResource(id = R.string.only_action_text))
                        ReadMoreTextView(
                            text = stringResource(id = R.string.expandable_text),
                            enableActionIcons = false,
                            enableActionText = true
                        )

                        // Expand & Collapse with only icons
                        Spacer(modifier = Modifier.height(20.dp))
                        TitleTextView(titleText = stringResource(id = R.string.only_action_icon))
                        ReadMoreTextView(
                            text = stringResource(id = R.string.expandable_text),
                            enableActionIcons = true,
                            enableActionText = false
                        )

                        // Expand & Collapse with Text + Icon
                        Spacer(modifier = Modifier.height(20.dp))
                        TitleTextView(titleText = stringResource(id = R.string.action_text_icon))
                        ReadMoreTextView(
                            text = stringResource(id = R.string.expandable_text),
                            enableActionIcons = true,
                            enableActionText = true
                        )

                        // Custom icons for Expand & Collapse
                        Spacer(modifier = Modifier.height(20.dp))
                        TitleTextView(titleText = stringResource(id = R.string.custom_action_icon))
                        ReadMoreTextView(
                            text = stringResource(id = R.string.expandable_text),
                            enableActionIcons = true,
                            enableActionText = true,
                            expandConfig = ExpandConfig(
                                expandText = stringResource(id = R.string.read_more),
                                expandTextSize = 16.sp,
                                expandTextColor = Color.Blue,
                                showUnderline = true,
                                expandIcon = Icons.Outlined.AccessAlarm
                            ),
                            collapseConfig = CollapseConfig(
                                collapseText = stringResource(id = R.string.read_less),
                                collapseTextSize = 16.sp,
                                collapseTextColor = Color.Blue,
                                showUnderline = true,
                                collapseIcon = Icons.Outlined.Scanner
                            )
                        )

                        // Custom text for Expand & Collapse
                        Spacer(modifier = Modifier.height(20.dp))
                        TitleTextView(titleText = stringResource(id = R.string.custom_action_text))
                        ReadMoreTextView(
                            text = stringResource(id = R.string.expandable_text),
                            enableActionIcons = true,
                            enableActionText = true,
                            expandConfig = ExpandConfig(
                                expandText = stringResource(id = R.string.show_more),
                                expandTextSize = 16.sp,
                                expandTextColor = Color.Blue,
                                showUnderline = true,
                                expandIcon = Icons.Outlined.KeyboardArrowDown
                            ),
                            collapseConfig = CollapseConfig(
                                collapseText = stringResource(id = R.string.show_less),
                                collapseTextSize = 16.sp,
                                collapseTextColor = Color.Blue,
                                showUnderline = true,
                                collapseIcon = Icons.Outlined.KeyboardArrowUp
                            )
                        )

                        // Custom text color for Expand & Collapse
                        Spacer(modifier = Modifier.height(20.dp))
                        TitleTextView(titleText = stringResource(id = R.string.custom_action_text_color))
                        ReadMoreTextView(
                            text = stringResource(id = R.string.expandable_text),
                            enableActionIcons = true,
                            enableActionText = true,
                            expandConfig = ExpandConfig(
                                expandText = stringResource(id = R.string.show_more),
                                expandTextSize = 16.sp,
                                expandTextColor = Color.Red,
                                showUnderline = true,
                                expandIcon = Icons.Outlined.KeyboardArrowDown
                            ),
                            collapseConfig = CollapseConfig(
                                collapseText = stringResource(id = R.string.show_less),
                                collapseTextSize = 16.sp,
                                collapseTextColor = Color.Green,
                                showUnderline = true,
                                collapseIcon = Icons.Outlined.KeyboardArrowUp
                            )
                        )

                        // Custom font family for Expand & Collapse
                        Spacer(modifier = Modifier.height(20.dp))
                        TitleTextView(titleText = stringResource(id = R.string.custom_font_for_expand_collapse))
                        ReadMoreTextView(
                            text = stringResource(id = R.string.expandable_text),
                            enableActionIcons = true,
                            enableActionText = true,
                            expandConfig = ExpandConfig(
                                expandText = stringResource(id = R.string.read_more),
                                expandTextSize = 16.sp,
                                expandTextColor = Color.Blue,
                                showUnderline = true,
                                expandIcon = Icons.Outlined.KeyboardArrowDown,
                                expandFontFamily = Josefin
                            ),
                            collapseConfig = CollapseConfig(
                                collapseText = stringResource(id = R.string.read_less),
                                collapseTextSize = 16.sp,
                                collapseTextColor = Color.Blue,
                                showUnderline = true,
                                collapseIcon = Icons.Outlined.KeyboardArrowUp,
                                collapseFontFamily = Josefin
                            )
                        )
                    }
                }
            }
        }
    }
}