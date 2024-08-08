package com.jetpack.compose.learning.dropdownmenu

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import com.jetpack.compose.learning.theme.pink700

class DropDownMenuActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainContent()
            }
        }
    }

    @Preview
    @Composable
    fun MainContent() {
        var isTopBarExpanded by remember { mutableStateOf(false) }
        val optionsList = listOf("Refresh", "Aboutus", "More")
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Dropdown") },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                    // Show dropdown on Navigation Action clicked
                    actions = {
                        IconButton(onClick = { isTopBarExpanded = true }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = isTopBarExpanded,
                            onDismissRequest = { isTopBarExpanded = false }
                        ) {
                            optionsList.forEach {
                                DropdownMenuItem(onClick = {
                                    showToast("$it Clicked")
                                    isTopBarExpanded = false
                                }) {
                                    Text(it)
                                }
                            }
                        }
                    }
                )
            }
        ) { contentPadding ->
            DropDownContents(modifier = Modifier.padding(contentPadding))
        }
    }

    @Composable
    fun DropDownContents(modifier: Modifier = Modifier) {
        var isSimpleDropDownExpanded by remember { mutableStateOf(false) }
        var isCustomBackgroundDropDownExpanded by remember { mutableStateOf(false) }
        var isCustomDropDownExpanded by remember { mutableStateOf(false) }
        var isDisabledDropDownExpanded by remember { mutableStateOf(false) }
        var simpleCountry by remember { mutableStateOf("") }
        var customBackgroundCountry by remember { mutableStateOf("") }
        var customCountry by remember { mutableStateOf("") }
        var disabledCountry by remember { mutableStateOf("") }
        val countryList = listOf("USA", "INDIA", "JAPAN", "CHINA")

        Column(modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(30.dp))

            // Simple dropdown
            Box {
                OutlinedTextField(
                    value = simpleCountry,
                    onValueChange = { },
                    placeholder = { Text(text = "Simple Dropdown") },
                    enabled = false,
                    modifier = Modifier
                        .clickable {
                            isSimpleDropDownExpanded = true
                        }
                        .fillMaxWidth(0.8f),
                    textStyle = TextStyle(color = Color.Black)
                )

                DropdownMenu(
                    expanded = isSimpleDropDownExpanded,
                    onDismissRequest = { isSimpleDropDownExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.8f),
                ) {
                    countryList.forEach {
                        DropdownMenuItem(onClick = {
                            simpleCountry = it
                            isSimpleDropDownExpanded = false
                        }, modifier = Modifier.fillMaxWidth(0.8f)) {
                            Text(it)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Custom background color dropdown
            Box {
                OutlinedTextField(
                    value = customBackgroundCountry,
                    onValueChange = { },
                    placeholder = { Text(text = "Custom Background Dropdown") },
                    enabled = false,
                    modifier = Modifier
                        .clickable {
                            isCustomBackgroundDropDownExpanded = true
                        }
                        .fillMaxWidth(0.8f),
                    textStyle = TextStyle(color = Color.Black)
                )

                DropdownMenu(
                    expanded = isCustomBackgroundDropDownExpanded,
                    onDismissRequest = { isCustomBackgroundDropDownExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(color = pink700),
                ) {
                    countryList.forEachIndexed { index, country ->
                        DropdownMenuItem(onClick = {
                            customBackgroundCountry = country
                            isCustomBackgroundDropDownExpanded = false
                        }) {
                            Text(country, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Custom dropdown with icons in dropdown item
            Box {
                OutlinedTextField(
                    value = customCountry,
                    onValueChange = { },
                    placeholder = { Text(text = "Custom Dropdown") },
                    enabled = false,
                    modifier = Modifier
                        .clickable {
                            isCustomDropDownExpanded = true
                        }
                        .fillMaxWidth(0.8f),
                    textStyle = TextStyle(color = Color.Black),
                    trailingIcon = { Icon(imageVector = Icons.Default.ArrowCircleDown, "") }
                )

                DropdownMenu(
                    expanded = isCustomDropDownExpanded,
                    onDismissRequest = { isCustomDropDownExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    countryList.forEachIndexed {index, selectedCountry ->
                        DropdownMenuItem(onClick = {
                            customCountry = selectedCountry
                            isCustomDropDownExpanded = false
                        }) {
                            Modifier.fillMaxWidth()
                            Text(selectedCountry)
                            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                                Icon(imageVector = Icons.Default.ArrowCircleUp, contentDescription = null)
                            }
                        }
                        if (index != countryList.lastIndex)
                            Divider(Modifier.background(Color.Black))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Disable selection dropdown
            Box {
                OutlinedTextField(
                    value = disabledCountry,
                    onValueChange = { },
                    placeholder = { Text(text = "Disable selection dropdown") },
                    enabled = false,
                    modifier = Modifier
                        .clickable {
                            isDisabledDropDownExpanded = true
                        }
                        .fillMaxWidth(0.8f),
                    textStyle = TextStyle(color = Color.Black)
                )

                DropdownMenu(
                    expanded = isDisabledDropDownExpanded,
                    onDismissRequest = { isDisabledDropDownExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    countryList.forEachIndexed { index, selectedCountry ->
                        DropdownMenuItem(onClick = {
                            disabledCountry = selectedCountry
                            isDisabledDropDownExpanded = false
                        },
                        enabled = false) {
                            Modifier.fillMaxWidth()
                            Text(selectedCountry)
                        }
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}