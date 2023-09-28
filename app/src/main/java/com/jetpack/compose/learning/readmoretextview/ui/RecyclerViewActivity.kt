package com.jetpack.compose.learning.readmoretextview.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.readmoretextview.components.ReadMoreTextView
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Example of ReadMoreTextView as Android RecyclerView
 */
class RecyclerViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }

            BaseView(appTheme.value, systemUiController) {
                RecyclerViewComposable()
            }
        }
    }

    @Composable
    fun RecyclerViewComposable() {
        /*
            showing it in static 20 items LazyColumn with enableActionText `true` and enableActionText `true`
            to show Expand/Collapse text and icons
         */
        LazyColumn {
            item {
                TopAppBar(
                    title = { Text(stringResource(R.string.recycler_view)) },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
            items(20) {
                ReadMoreTextView(
                    text = stringResource(R.string.expandable_text),
                    enableActionIcons = true,
                    enableActionText = true
                )
            }
        }
    }
}