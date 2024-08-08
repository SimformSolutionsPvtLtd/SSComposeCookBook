package com.jetpack.compose.learning.snackbar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

class SnackBarActivity : ComponentActivity() {

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
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("Snackbars") }, navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    })
                }, scaffoldState = scaffoldState
            ) { contentPadding ->
                SnackBarExample(modifier = Modifier.padding(contentPadding), scaffoldState)
            }
        }
    }

    @Composable
    private fun SnackBarExample(modifier: Modifier, scaffoldState: ScaffoldState) {

        val spaceModifier = Modifier.height(30.dp)

        Column(modifier) {

            val coroutineScope = rememberCoroutineScope()

            Spacer(modifier = spaceModifier)

            Text(text = "Snackbars", style = typography.h6, modifier = Modifier.padding(8.dp))

            // Basic Snackbar
            Snackbar(modifier = Modifier.padding(4.dp)) {
                Text(text = "Basic snackbar")
            }

            Spacer(modifier = spaceModifier)

            // Snackbar with action button
            Snackbar(modifier = Modifier.padding(4.dp), action = {
                TextButton(onClick = {
                    Toast.makeText(
                        applicationContext,
                        "This is Snackbar with action button",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text(text = "Click Me", color = Color.White)
                }
            }) {
                Text(text = "Snackbar with action button")
            }

            Spacer(modifier = spaceModifier)

            // Snackbar with action button on new line
            Snackbar(modifier = Modifier.padding(4.dp), actionOnNewLine = true, action = {
                TextButton(onClick = {
                    Toast.makeText(
                        applicationContext,
                        "This is Snackbar with action button on new line",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text(text = "ClickMe", color = Color.White)
                }
            }) {
                Text(text = "Action button on new line")
            }

            Spacer(modifier = spaceModifier)

            // Snackbar with custom background color
            Snackbar(modifier = Modifier.padding(4.dp), backgroundColor = Color.White, action = {
                TextButton(onClick = {
                    Toast.makeText(
                        applicationContext,
                        "This is Snackbar with custom color background",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text(text = "ClickMe")
                }
            }) {
                Text(text = "Custom color background", color = Color.Black)
            }

            Spacer(modifier = spaceModifier)

            // Custom snackbar with action buttons
            Snackbar(modifier = Modifier.padding(4.dp), action = {
                TextButton(onClick = {
                    Toast.makeText(
                        applicationContext,
                        "This is Custom Snackbar with action button",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text(text = "ClickMe", color = Color.White)
                }
            }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        Toast.makeText(
                            applicationContext,
                            "Edit action button Clicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, modifier = Modifier.height(30.dp)) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Custom Content SnackBar",
                        color = Color.White,
                        modifier = Modifier.wrapContentSize(Alignment.Center)
                    )
                }
            }

            Spacer(modifier = spaceModifier)

            // Show snackbar on Button Click
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                Button(onClick = {
                    coroutineScope.launch {
                        when (scaffoldState.snackbarHostState.showSnackbar(
                            "Hi this is snackbar",
                            "ClickMe",
                            SnackbarDuration.Short
                        )) {
                            SnackbarResult.Dismissed -> Toast.makeText(
                                applicationContext,
                                "Snackbar Dismissed",
                                Toast.LENGTH_SHORT
                            ).show()
                            SnackbarResult.ActionPerformed -> Toast.makeText(
                                applicationContext,
                                "Action button clicked and dismissed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }) {
                    Text(text = "Click me to show a snackbar")
                }
            }
        }
    }
}