package com.jetpack.compose.learning.xmls

import android.content.Context
import android.util.AttributeSet
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.pink500

/*This is custom toolbar we can use in XML*/
class CustomComposeToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    var onBackPressed: () -> Unit = {}

    // The Content function works as a Composable function so we can now define our Compose UI components to render.
    @Composable
    override fun Content() {
        val appTheme = remember { mutableStateOf(AppThemeState()) }
        TopAppBar(
            title = { Text("Custom Compose Toolbar") },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            backgroundColor = pink500,
            contentColor = Color.White
        )
    }
}