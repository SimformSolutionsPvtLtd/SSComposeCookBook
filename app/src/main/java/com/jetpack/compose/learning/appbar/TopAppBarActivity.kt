package com.jetpack.compose.learning.appbar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class TopAppBarActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("App Bar") },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                }) { contentPadding ->
                    AppBar(modifier = Modifier.padding(contentPadding))
                }
            }
        }
    }

    @Composable
    private fun AppBar(modifier: Modifier) {

        Column(modifier) {

            Spacer(modifier = Modifier.height(80.dp))

            // Simple App Bar with no actions
            TopAppBar(
                title = { Text("Simple App Bar") },
            )

            Spacer(modifier = Modifier.height(20.dp))

            // App Bar with Left icon action
            TopAppBar(
                title = { Text("Left Action App Bar") },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(
                            applicationContext,
                            "Menu Icon Clicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // App Bar with Right icon action
            TopAppBar(
                title = { Text("Right Action App Bar") },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(
                            applicationContext,
                            "Search Icon Clicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // App Bar with Right amd Left icon action
            TopAppBar(
                title = { Text("Multiple Actions App Bar") },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(
                            applicationContext,
                            "Menu Icon Clicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(
                            applicationContext,
                            "Search Icon Clicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // App Bar with multiple Right icon action
            TopAppBar(
                title = { Text("Multiple Right Actions App Bar") },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(
                            applicationContext,
                            "Search Icon Clicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    IconButton(onClick = {
                        Toast.makeText(
                            applicationContext,
                            "Refresh Icon Clicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(imageVector = Icons.Filled.Refresh, contentDescription = null)
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Custom App Bar with Left icon action and Title text in Center
            TopAppBar(modifier = Modifier.fillMaxWidth()) {

                //TopAppBar Content
                Box(Modifier.height(32.dp)) {

                    // Navigation Icon
                    IconButton(
                        onClick = {
                            Toast.makeText(
                                applicationContext,
                                "Custom Refresh Icon Clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        enabled = true,
                        modifier = Modifier.width(60.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                        )
                    }

                    //Title
                    Row(
                        Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            text = "Custom App bar",
                            fontSize = 20.sp
                        )
                    }

                    //Action
                    Row(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            IconButton(
                                onClick = {
                                    Toast.makeText(
                                        applicationContext,
                                        "Custom Search Icon Clicked",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                enabled = true,
                                modifier = Modifier.width(60.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Refresh",
                                )
                            }
                        }
                    )
                }
            }

        }
    }
}