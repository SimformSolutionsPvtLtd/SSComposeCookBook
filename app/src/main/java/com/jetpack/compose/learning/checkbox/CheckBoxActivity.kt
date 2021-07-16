package com.jetpack.compose.learning.checkbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CheckBoxActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(15.dp)
            ) {
                TitleText("Select Your Interested Area :")
                CheckBox()
            }
        }
    }

    @Composable
    fun CheckBox() {
        /* Here Create State of checkbox */
        val mItemsList = remember {
            mutableStateOf(
                listOf(
                    Profession("Android"),
                    Profession("iOS"),
                    Profession("Angular"),
                    Profession("Python"),
                    Profession("Electron")
                )
            )
        }

        mItemsList.value.forEachIndexed { index, items ->
            Row(modifier = Modifier.padding(bottom = 10.dp)) {
                Checkbox(
                    checked = mItemsList.value[index].value.value,
                    onCheckedChange = { mItemsList.value[index].value.value = it },
                    enabled = true,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Blue,
                        uncheckedColor = Color.DarkGray,
                        checkmarkColor = Color.White
                    )
                )
                Text(text = items.text, modifier = Modifier.padding(start = 15.dp))
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