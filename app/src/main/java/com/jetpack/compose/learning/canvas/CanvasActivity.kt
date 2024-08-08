package com.jetpack.compose.learning.canvas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.model.Component
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class CanvasActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                val systemUiController = remember { SystemUiController(window) }
                val appTheme = remember { mutableStateOf(AppThemeState()) }
                BaseView(appTheme.value, systemUiController) {
                    Scaffold(topBar = {
                        TopAppBar(
                            title = { Text("Canvas") },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed() }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                                }
                            }
                        )
                    }) { contentPadding ->
                        LazyColumn(modifier = Modifier.padding(contentPadding)) {
                            items(getComponents()) { component ->
                                ButtonComponent(component)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ButtonComponent(component: Component) {
        Button(
            onClick = {
                startActivity(Intent(this, component.className))
            }, modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = component.componentName,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }

    private fun getComponents(): List<Component> = listOf(
        Component("Shapes", CanvasShapesActivity::class.java),
        Component("Text and Image", CanvasTextImageActivity::class.java),
        Component("Path", CanvasPathsActivity::class.java),
        Component("Path Operation", CanvasPathOperationActivity::class.java),
        Component("DrawScope Helpers", CanvasDrawScopeOperationActivity::class.java),
        Component("Canvas + Touch", CanvasTouchOperationActivity::class.java),
        Component("Blend Modes", CanvasBlendModesActivity::class.java),
        Component("Basic Example", BasicCanvasExampleActivity::class.java),
        Component("Android 11 Easter Egg", CanvasAndroidEasterEggActivity::class.java),
        Component("Drawing Board", CanvasDrawingBoardActivity::class.java),
    )
}
