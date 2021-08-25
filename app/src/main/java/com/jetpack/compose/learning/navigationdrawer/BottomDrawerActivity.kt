package com.jetpack.compose.learning.navigationdrawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.SystemUiController
import com.jetpack.compose.learning.theme.BaseView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BottomDrawerActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    lateinit var drawerState: BottomDrawerState
    lateinit var scope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                BottomDrawerSample()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Preview
    @Composable
    fun BottomDrawerSample() {
        drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
        scope = rememberCoroutineScope()
        val (isGestureEnable, toggleGesturesEnabled) = remember { mutableStateOf(true) }
        Column(Modifier.background(MaterialTheme.colors.background)) {
            TopAppBar(
                title = { Text("Bottom Drawer Sample") },
                navigationIcon = {
                    IconButton(onClick = { finish() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            Spacer(Modifier.requiredHeight(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .toggleable(
                        value = isGestureEnable,
                        onValueChange = toggleGesturesEnabled
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = isGestureEnable, onCheckedChange = null,
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colors.primaryVariant,
                        uncheckedColor = Color.DarkGray,
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    text = "Enable swipe up/down gesture",
                    Modifier.padding(start = 5.dp),
                    fontSize = 16.sp
                )
            }
            BottomDrawer(
                gesturesEnabled = isGestureEnable,
                drawerState = drawerState,
                drawerContent = {
                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp),
                        onClick = { scope.launch { drawerState.close() } },
                        content = { Text("Close Drawer") }
                    )
                    LazyColumn {
                        items(25) {
                            ListItem(
                                text = { Text("Item ${it + 1}") },
                                icon = {
                                    Icon(
                                        Icons.Default.Info,
                                        contentDescription = "Description"
                                    )
                                }
                            )
                        }
                    }
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = { scope.launch { drawerState.open() } }) {
                            Text("Click to open Bottom Drawer", fontSize = 16.sp)
                        }
                    }
                })
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onBackPressed() {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            super.onBackPressed()
        }
    }
}