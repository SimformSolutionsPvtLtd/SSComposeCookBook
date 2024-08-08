package com.jetpack.compose.learning.radiobutton

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

val spaceModifier = Modifier.height(20.dp)

class RadioButtonActivity : ComponentActivity() {

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
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Radio Button") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }) { padding ->
            LazyColumn(modifier = Modifier.padding(padding), contentPadding = PaddingValues(10.dp)) {
                item {
                    RadioButtonsWithProperties()
                }
                item {
                    RadioGroupSample()
                }
            }
        }
    }

    @Composable
    private fun RadioButtonsWithProperties() {

        var simpleRadioButtonSelectionState by remember { mutableStateOf(false) }
        var simpleRadioButtonWithLabelSelectionState by remember { mutableStateOf(false) }
        var customSelectedColorRadioButton by remember { mutableStateOf(false) }
        var customUnSelectedColorRadioButton by remember { mutableStateOf(false) }

        Card(
            backgroundColor = MaterialTheme.colors.primary.copy(0.04f),
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, MaterialTheme.colors.primary.copy(0.5f)),
        ) {
            Column(Modifier.padding(10.dp)) {

                Text(
                    text = "Radio Button with different properties:",
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = spaceModifier)

                // Simple Radio Button with Label
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = simpleRadioButtonWithLabelSelectionState, onClick = {
                        simpleRadioButtonWithLabelSelectionState =
                            !simpleRadioButtonWithLabelSelectionState
                    }, Modifier.padding(end = 5.dp))
                    Text(
                        text = "Simple Radio button with label",
                        Modifier.clickable {
                            simpleRadioButtonWithLabelSelectionState =
                                !simpleRadioButtonWithLabelSelectionState
                        })
                }

                Spacer(modifier = spaceModifier)

                // Disabled Radio Button
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = false, onClick = {
                            simpleRadioButtonWithLabelSelectionState =
                                !simpleRadioButtonWithLabelSelectionState
                        },
                        enabled = false, modifier = Modifier.padding(end = 5.dp)
                    )
                    Text(text = "Disabled unselected Radio button")
                }

                Spacer(modifier = spaceModifier)

                // Disabled selected Radio Button
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = true, onClick = {
                        simpleRadioButtonSelectionState = !simpleRadioButtonSelectionState
                    }, enabled = false, modifier = Modifier.padding(end = 5.dp))
                    Text(text = "Disabled selected Radio button")
                }

                Spacer(modifier = spaceModifier)

                // Custom selected color Radio Button
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = customSelectedColorRadioButton,
                        onClick = {
                            customSelectedColorRadioButton = !customSelectedColorRadioButton
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primaryVariant),
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Text(text = "Custom selected color Radio button",
                        Modifier.clickable {
                            customSelectedColorRadioButton = !customSelectedColorRadioButton
                        })
                }

                Spacer(modifier = spaceModifier)

                // Custom unselected color Radio Button
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = customUnSelectedColorRadioButton,
                        onClick = {
                            customUnSelectedColorRadioButton = !customUnSelectedColorRadioButton
                        },
                        colors = RadioButtonDefaults.colors(unselectedColor = MaterialTheme.colors.primaryVariant),
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Text(text = "Custom unselected color Radio button",
                        Modifier.clickable {
                            customUnSelectedColorRadioButton = !customUnSelectedColorRadioButton
                        })
                }
            }
        }
    }

    @Composable
    private fun RadioGroupSample() {
        /* Here Create List for radioButton */
        val radioOptions = listOf("Red Color", "Blue Color", "Black Color", "White Color")
        /* Here Create State of radioButton */
        var selectedOption by remember { mutableStateOf("") }

        Card(
            backgroundColor = MaterialTheme.colors.primary.copy(0.04f),
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, MaterialTheme.colors.primary.copy(0.5f)),
        ) {

            Column(Modifier.padding(10.dp)) {

                Text(
                    text = "Radio Group example :",
                    style = MaterialTheme.typography.h6
                )

                Spacer(spaceModifier)

                radioOptions.forEach { text ->
                    Row(Modifier
                        .padding(bottom = 10.dp)
                        .wrapContentSize()
                        .clickable(
                            onClick = {
                                selectedOption = text
                                showToast("$selectedOption selected")
                            }
                        ), verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = {
                                selectedOption = text
                                showToast("$selectedOption selected")
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primaryVariant)
                        )
                        Text(
                            text = text,
                            modifier = Modifier.padding(start = 10.dp, end = 20.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}