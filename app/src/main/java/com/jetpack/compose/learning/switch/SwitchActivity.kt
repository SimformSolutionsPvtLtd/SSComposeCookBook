package com.jetpack.compose.learning.switch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class SwitchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Column(Modifier.background(Color.White)) {
                    TopAppBar(
                        title = { Text("Switch") },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                    SwitchComponents()
                }
            }
        }
    }

    @Preview
    @Composable
    private fun SwitchComponents() {

        var simpleSwitchState by remember { mutableStateOf(false) }
        var customEnableColorSwitchState by remember { mutableStateOf(true) }
        var customDisableColorSwitchState by remember { mutableStateOf(false) }
        var customEnableTrackColorSwitchState by remember { mutableStateOf(true) }
        var customDisableTrackColorSwitchState by remember { mutableStateOf(false) }

        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .wrapContentSize(Alignment.TopCenter)
        ) {
            // Basic Switch
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Simple Switch.", modifier = Modifier.padding(end = 5.dp))
                Switch(
                    checked = simpleSwitchState,
                    onCheckedChange = { simpleSwitchState = it }
                )
            }

            // Switch with selected thumb color
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Custom selected thumb color.", modifier = Modifier.padding(end = 5.dp))
                Switch(
                    checked = customEnableColorSwitchState,
                    onCheckedChange = { customEnableColorSwitchState = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Blue
                    ),
                    modifier = Modifier.padding(5.dp)
                )
            }

            // Switch with unselected thumb color
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Custom unselected thumb color.",
                    modifier = Modifier.padding(end = 5.dp)
                )
                Switch(
                    checked = customDisableColorSwitchState,
                    onCheckedChange = { customDisableColorSwitchState = it },
                    colors = SwitchDefaults.colors(
                        uncheckedThumbColor = Color.Black
                    ),
                    modifier = Modifier.padding(5.dp)
                )
            }

            // Switch with selected track color
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Custom selected track color.", modifier = Modifier.padding(end = 5.dp))
                Switch(
                    checked = customEnableTrackColorSwitchState,
                    onCheckedChange = { customEnableTrackColorSwitchState = it },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Color.Blue
                    ),
                    modifier = Modifier.padding(5.dp)
                )
            }

            // Switch with unselected track color
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Custom unselected track color.",
                    modifier = Modifier.padding(end = 5.dp)
                )
                Switch(
                    checked = customDisableTrackColorSwitchState,
                    onCheckedChange = { customDisableTrackColorSwitchState = it },
                    colors = SwitchDefaults.colors(
                        uncheckedTrackColor = Color.Blue
                    ),
                    modifier = Modifier.padding(5.dp)
                )
            }

            // Disabled Switch in OFF state
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Disabled switch in OFF state", modifier = Modifier.padding(end = 5.dp))
                Switch(
                    checked = false,
                    onCheckedChange = { },
                    enabled = false,
                    modifier = Modifier.padding(5.dp)
                )
            }

            // Disabled Switch in ON state
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Disabled switch in ON state.", modifier = Modifier.padding(end = 5.dp))
                Switch(
                    checked = true,
                    onCheckedChange = { },
                    enabled = false,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}