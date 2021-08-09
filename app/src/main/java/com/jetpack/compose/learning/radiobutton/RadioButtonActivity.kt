package com.jetpack.compose.learning.radiobutton

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import com.jetpack.compose.learning.theme.pink200
import com.jetpack.compose.learning.theme.pink700

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
        Scaffold (topBar = {
            TopAppBar(
                title = { Text("Radio Button") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }) {
            Column(
                Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopCenter)
                    .padding(top = 10.dp)) {
                RadioButtonsWithProperties()
                RadioGroupSample()
            }
        }
    }

    @Composable
    private fun RadioButtonsWithProperties() {

        var simpleRadioButtonSelectionState by remember { mutableStateOf(false) }
        var simpleRadioButtonWithLabelSelectionState by remember { mutableStateOf(false) }
        var customSelectedColorRadioButton by remember { mutableStateOf(false) }
        var customUnSelectedColorRadioButton by remember { mutableStateOf(false) }

        Card(backgroundColor = pink200,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp)) {
            Column(Modifier.padding(10.dp)) {

                Text(text = "This are Radio Button with different properties", style = MaterialTheme.typography.h6)

                Spacer(modifier = spaceModifier)

                // Simple Radio Button
                RadioButton(selected = simpleRadioButtonSelectionState, onClick = {
                    simpleRadioButtonSelectionState = !simpleRadioButtonSelectionState
                })

                Spacer(modifier = spaceModifier)

                // Simple Radio Button with Label
                Row {
                    RadioButton(selected = simpleRadioButtonWithLabelSelectionState, onClick = {
                        simpleRadioButtonWithLabelSelectionState = !simpleRadioButtonWithLabelSelectionState
                    }, Modifier.padding(end = 5.dp))
                    Text(text = "Simple Radio button with label", Modifier.clickable { simpleRadioButtonWithLabelSelectionState = !simpleRadioButtonWithLabelSelectionState })
                }

                Spacer(modifier = spaceModifier)

                // Disabled Radio Button
                Row {
                    RadioButton(selected = false, onClick = {
                        simpleRadioButtonWithLabelSelectionState = !simpleRadioButtonWithLabelSelectionState
                    },
                        enabled = false, modifier = Modifier.padding(end = 5.dp))
                    Text(text = "Disabled unselected Radio button")
                }

                Spacer(modifier = spaceModifier)

                // Disabled selected Radio Button
                Row {
                    RadioButton(selected = true, onClick = {
                        simpleRadioButtonSelectionState = !simpleRadioButtonSelectionState
                    }, enabled = false, modifier =  Modifier.padding(end = 5.dp))
                    Text(text = "Disabled selected Radio button")
                }

                Spacer(modifier = spaceModifier)

                // Custom selected color Radio Button
                Row {
                    RadioButton(selected = customSelectedColorRadioButton, onClick = {
                        customSelectedColorRadioButton = !customSelectedColorRadioButton
                    },
                        colors = RadioButtonDefaults.colors(selectedColor = pink700), modifier =  Modifier.padding(end = 5.dp))
                    Text(text = "Custom selected color Radio button",
                        Modifier.clickable { customSelectedColorRadioButton = !customSelectedColorRadioButton })
                }

                Spacer(modifier = spaceModifier)

                // Custom unselected color Radio Button
                Row {
                    RadioButton(selected = customUnSelectedColorRadioButton, onClick = {
                        customUnSelectedColorRadioButton = !customUnSelectedColorRadioButton
                    },
                        colors = RadioButtonDefaults.colors(unselectedColor = Color.White), modifier =  Modifier.padding(end = 5.dp))
                    Text(text = "Custom unselected color Radio button",
                        Modifier.clickable { customUnSelectedColorRadioButton = !customUnSelectedColorRadioButton })
                }
            }
        }
    }

    @Composable
    private fun RadioGroupSample() {
        /* Here Create List for radioButton */
        val radioOptions = listOf("Red", "Blue", "Black", "White")
        /* Here Create State of radioButton */
        var selectedOption by remember { mutableStateOf("") }

        Card (backgroundColor = pink200,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp)) {

            Column(Modifier.padding(10.dp)) {

                Text(text = "This is an example of Radio Group", style = MaterialTheme.typography.h6)

                Spacer(spaceModifier)

                radioOptions.forEach { text ->
                    Row(Modifier
                        .padding(bottom = 10.dp)
                        .wrapContentSize()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                selectedOption = text
                                showToast("$selectedOption color selected")
                            }
                        )
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = {
                                selectedOption = text
                                showToast("$selectedOption color selected")
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