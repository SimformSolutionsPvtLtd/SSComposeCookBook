package com.jetpack.compose.learning.radiobutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.SystemUiController
import com.jetpack.compose.learning.theme.BaseView

class RadioButtonActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                RadioGroupSample()
            }
        }
    }

    @Composable
    fun RadioGroupSample() {
        /* Here Create List for radioButton */
        val radioOptions = listOf("Red", "Blue", "Black", "White")
        /* Here Create State of radioButton */
        val selectedOption = remember { mutableStateOf("") }

        Column {
            TopAppBar(
                title = { Text("Radio Button") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            Column(Modifier.padding(20.dp)) {
                Text("Choose any one color :", style = MaterialTheme.typography.h6)
                Spacer(Modifier.requiredHeight(24.dp))
                radioOptions.forEach { text ->
                    Row(Modifier
                        .padding(bottom = 10.dp)
                        .wrapContentSize()
                        .selectable(
                            selected = (text == selectedOption.value),
                            onClick = {
                                selectedOption.value = text
                            }
                        )
                    ) {
                        RadioButton(
                            selected = (text == selectedOption.value),
                            onClick = {
                                selectedOption.value = text
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
}