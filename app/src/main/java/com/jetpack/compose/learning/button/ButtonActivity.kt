package com.jetpack.compose.learning.button

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class ButtonActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                ButtonSample()
            }
        }
    }

    @Preview
    @Composable
    fun ButtonSample() {
        val spaceHeight = 24.dp
        val buttonPadding = 10.dp
        val textPadding = 5.dp
        Column(Modifier.background(MaterialTheme.colors.background).fillMaxHeight()) {
            TopAppBar(
                title = { Text(text = "Button") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            Column(Modifier.padding(20.dp)) {
                Row {
                    // Simple Button
                    Button(
                        onClick = {
                            showToast("Simple Button Clicked")
                        },
                        modifier = Modifier.padding(buttonPadding)
                    ) {
                        Text(text = "Simple Button", Modifier.padding(textPadding))
                    }

                    // Button with Border
                    Button(
                        onClick = {
                            showToast("Simple Button with Border Clicked")
                        },
                        modifier = Modifier.padding(buttonPadding),
                        border = BorderStroke(width = 2.dp, Color.Black),
                    ) {
                        Text(text = "Button with Border", Modifier.padding(textPadding), fontSize = 12.sp)
                    }
                }
                Spacer(Modifier.requiredHeight(spaceHeight))
                Row {
                    // Rounded Button
                    Button(
                        onClick = {
                            showToast("Rounded Button Clicked")
                        },
                        modifier = Modifier.padding(buttonPadding),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(text = "Rounded Button", Modifier.padding(textPadding))
                    }

                    // Disable Button
                    Button(
                        onClick = {
                            showToast("Simple Disable Button Clicked") // It will not show Toast as it's disabled (enabled = false)
                        }, enabled = false,
                        modifier = Modifier.padding(buttonPadding),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(text = "Disable Button", Modifier.padding(textPadding))
                    }
                }
                Spacer(Modifier.requiredHeight(spaceHeight))
                Row {
                    // Outlined Button
                    OutlinedButton(
                        onClick = {
                            showToast("Outlined Button Clicked")
                        },
                        modifier = Modifier.padding(buttonPadding)
                    ) {
                        Text("Outlined Button", Modifier.padding(textPadding))
                    }

                    // Text Button
                    TextButton(onClick = {
                        showToast("Text Button Clicked")
                    }, modifier = Modifier.padding(buttonPadding)) {
                        Text("Text Button", Modifier.padding(textPadding))
                    }
                }
                Spacer(Modifier.requiredHeight(spaceHeight))
                Row {
                    // Icon Button
                    IconButton(
                        onClick = {
                            showToast("Icon Button Clicked")
                        },
                        modifier = Modifier.padding(buttonPadding)
                    ) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Icon Button",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    // Icon Button
                    Button(
                        onClick = {
                            showToast("Icon Button with Text Clicked")
                        }, modifier = Modifier.padding(buttonPadding),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PersonAdd,
                            contentDescription = "Icon with Text Button",
                            tint = Color.White,
                            modifier = Modifier.padding(all = 5.dp)
                        )
                        Text(text = "Icon Button with Text")
                    }
                }

                Spacer(Modifier.requiredHeight(spaceHeight))

                val horizontalGradientBrush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xffD16BA5),
                        Color(0xff86A8E7),
                        Color(0xff5FFBF1)
                    )
                )
                val verticalGradientBrush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xffD16BA5),
                        Color(0xff86A8E7),
                        Color(0xff5FFBF1)
                    )
                )

                // Horizontal Gradient Button
                TextButton(onClick = {
                    showToast("Horizontal Gradient Button Clicked")
                }, Modifier.background(brush = horizontalGradientBrush)) {
                    Text(text = "Horizontal Gradient Button", color = Color.White, modifier = Modifier.padding(10.dp))
                }

                Spacer(Modifier.requiredHeight(spaceHeight))

                // Vertical Gradient Button
                TextButton(onClick = {
                    showToast("Vertical Gradient Button Clicked")
                }, Modifier.background(brush = verticalGradientBrush)) {
                    Text(text = "Vertical Gradient Button", color = Color.White, modifier = Modifier.padding(10.dp))
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}