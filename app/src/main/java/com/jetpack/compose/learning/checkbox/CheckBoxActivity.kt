package com.jetpack.compose.learning.checkbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class CheckBoxActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                CheckBoxSample()
            }
        }
    }

    @Composable
    fun CheckBoxSample() {

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

        val (isClick, toggleClick) = remember { mutableStateOf(true) }

        Column(Modifier.background(MaterialTheme.colors.background).fillMaxHeight()) {
            TopAppBar(title = { Text("Checkbox") }, navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            })
            Column(Modifier.padding(20.dp)) {
                Text("Select your interested area :", style = MaterialTheme.typography.h6)
                Spacer(Modifier.requiredHeight(24.dp))
                mItemsList.value.forEachIndexed { index, items ->
                    Row(
                        modifier = Modifier.padding(bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = mItemsList.value[index].value.value,
                            onCheckedChange = { mItemsList.value[index].value.value = it },
                            enabled = true,
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colors.primaryVariant,
                                uncheckedColor = Color.DarkGray,
                                checkmarkColor = Color.White
                            )
                        )
                        Text(
                            text = items.text,
                            modifier = Modifier.padding(start = 10.dp),
                            fontSize = 16.sp
                        )
                    }
                }
                Spacer(Modifier.requiredHeight(24.dp))
                Text("Clickable label checkbox :", style = MaterialTheme.typography.h6)
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .toggleable(
                            value = isClick, onValueChange = toggleClick
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = isClick, onCheckedChange = null,
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colors.primaryVariant,
                            uncheckedColor = Color.DarkGray,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(
                        text = "Checkbox label", Modifier.padding(start = 5.dp), fontSize = 16.sp
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    private fun PreviewCheckBox() {
        CheckBoxSample()
    }
}