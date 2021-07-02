package com.jetpack.compose.learning.radiobutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class RadioButtonActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(15.dp)
            ) {
                TitleText("Choose Any One Color:")
                RadioGroup()
            }
        }
    }

    @Composable
    fun RadioGroup() {
        /* Here Create List, every display display to radioButton */
        val radioOptions = listOf("Red", "Blue", "Black", "White")
        /* Here Create State of RadioButton */
        val selectedOption = remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxWidth()) {
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
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
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

    @Composable
    fun TitleText(text: String) {
        Text(
            text = text,
            modifier = Modifier.padding(bottom = 20.dp),
            style = TextStyle(fontStyle = FontStyle.Italic),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
    }

}