package com.jetpack.compose.learning.floatingactionbutton

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class FloatingActionButtonActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }

    @Preview
    @Composable
    private fun MainContent() {
        val systemUiController = remember { SystemUiController(window) }
        val appTheme = remember { mutableStateOf(AppThemeState()) }
        BaseView(appTheme.value, systemUiController) {
            Scaffold(topBar = {
                TopAppBar(
                    title = { Text("Floating Action Buttons") },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            },  //FAB Button inside Scaffold
                floatingActionButton = {
                    FloatingActionButton(onClick = { onClick("Fab Button using Scaffold") }) {
                        Icon(Icons.Filled.Add, "")
                    }
                }
            ) { contentPadding ->
                FabButtons(modifier = Modifier.padding(contentPadding))
            }
        }
    }

    @Composable
    private fun FabButtons(modifier: Modifier = Modifier) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            val spaceModifier = Modifier.height(30.dp)

            //Simple FAB
            FloatingActionButton(onClick = { onClick("Simple FAB") }) {
                Icon(Icons.Filled.Add, "")
            }

            Spacer(modifier = spaceModifier)

            //FAB custom color
            FloatingActionButton(
                onClick = { onClick("Fab with custom color") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, "")
            }

            Spacer(modifier = spaceModifier)

            //Square FAB
            FloatingActionButton(
                onClick = { onClick("Square FAB") },
                shape = RectangleShape
            ) {
                Icon(Icons.Filled.Add, "")
            }

            Spacer(modifier = spaceModifier)

            //Simple FAB with custom content (similar of Exntended FAB)
            FloatingActionButton(onClick = { onClick("Simple FAB with custom content") },
                contentColor = Color.White,
                backgroundColor = MaterialTheme.colors.primary,
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    ) {
                        Icon(Icons.Filled.Add, "")
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Simple FAB with custom content")
                    }
                })

            Spacer(modifier = spaceModifier)

            //EXTENDED FAB
            ExtendedFloatingActionButton(
                text = { Text(text = "Extended FAB with icon") },
                onClick = { onClick("Extended FAB with icon") },
                icon = { Icon(Icons.Filled.Add, "") }
            )

            Spacer(modifier = spaceModifier)

            //EXTENDED FAB WITHOUT ICON
            ExtendedFloatingActionButton(
                text = {
                    Text(text = "Extended FAB without icon")
                },
                onClick = { onClick("Extended FAB without Icon") }
            )

            Spacer(modifier = spaceModifier)

            //EXTENDED FAB WITH SQUARE SHAPE
            ExtendedFloatingActionButton(
                text = {
                    Text(text = "Extended FAB with square shape")
                },
                onClick = { onClick("Extended FAB with square shape") },
                shape = RectangleShape
            )
        }
    }

    fun onClick(textMessage: String) {
        Toast.makeText(applicationContext, textMessage, Toast.LENGTH_SHORT).show()
    }
}