package com.jetpack.compose.learning.dialog

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class DialogActivity : ComponentActivity() {

    // it is use to set our state of dialog box to open as true.
    lateinit var openAlertDialog: MutableState<Boolean>
    lateinit var openCustomAlertDialog: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                DialogTypes()
            }
        }
    }

    @Preview
    @Composable
    fun DialogTypes() {
        openAlertDialog = remember { mutableStateOf(false) }
        openCustomAlertDialog = remember { mutableStateOf(false) }

        val (isDismissOnBackPress, setDismissOnBackPress) = remember { mutableStateOf(true) }
        val (isDismissOnClickOutside, setDismissOnClickOutside) = remember { mutableStateOf(true) }

        val spaceHeight = 24.dp
        Column(Modifier.background(MaterialTheme.colors.background).fillMaxHeight()) {
            TopAppBar(
                title = { Text("Dialog") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            Column(Modifier.padding(20.dp)) {
                Text(text = "Dialog Types : ", style = MaterialTheme.typography.h6)
                Spacer(Modifier.requiredHeight(spaceHeight))
                DialogItem(
                    dialogTypeText = "Alert Dialog Sample",
                    onClickEvent = {
                        openAlertDialog.value = true
                    })
                Spacer(Modifier.requiredHeight(spaceHeight))
                DialogItem(
                    dialogTypeText = "Custom Alert Dialog Sample",
                    onClickEvent = {
                        openCustomAlertDialog.value = true
                    })
                Spacer(Modifier.requiredHeight(spaceHeight))
                DialogPropertyCheckboxItem(
                    propertyName = "Close Dialog on Backpress",
                    isDismiss = isDismissOnBackPress,
                    onToggleChange = {
                        setDismissOnBackPress(!isDismissOnBackPress)
                    })
                Spacer(Modifier.requiredHeight(spaceHeight))
                DialogPropertyCheckboxItem(
                    propertyName = "Close Dialog on Click Outside",
                    isDismiss = isDismissOnClickOutside,
                    onToggleChange = {
                        setDismissOnClickOutside(!isDismissOnClickOutside)
                    })
            }
        }

        if (openAlertDialog.value) {
            AlertDialogSample(isDismissOnBackPress, isDismissOnClickOutside)
        }

        if (openCustomAlertDialog.value) {
            CustomAlertDialogSample(isDismissOnBackPress, isDismissOnClickOutside)
        }
    }

    @Composable
    @OptIn(ExperimentalMaterialApi::class)
    fun DialogItem(dialogTypeText: String, onClickEvent: () -> Unit) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            elevation = 5.dp,
            onClick = onClickEvent
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(dialogTypeText, Modifier.weight(1f), fontSize = 16.sp)
                Icon(Icons.Default.KeyboardArrowRight, "", Modifier.size(24.dp))
            }
        }
    }

    @Composable
    fun DialogPropertyCheckboxItem(
        propertyName: String,
        isDismiss: Boolean,
        onToggleChange: (Boolean) -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .toggleable(
                    value = isDismiss,
                    onValueChange = onToggleChange
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = isDismiss, onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primaryVariant,
                    uncheckedColor = Color.DarkGray,
                    checkmarkColor = Color.White
                )
            )
            Text(
                text = propertyName,
                Modifier.padding(start = 5.dp),
                fontSize = 16.sp
            )
        }
    }

    @Composable
    fun AlertDialogSample(closeOnBackPress: Boolean, closeOnOutsideClick: Boolean) {
        AlertDialog(
            properties = DialogProperties(
                dismissOnBackPress = closeOnBackPress,
                dismissOnClickOutside = closeOnOutsideClick
            ),
            onDismissRequest = {
                openAlertDialog.value = false
            },
            title = {
                Text("Alert Dialog Title")
            },
            text = {
                Text("Here is a description of the alert dialog")
            },
            confirmButton = {
                TextButton(onClick = {
                    openAlertDialog.value = false
                    Toast.makeText(this, "Clicked on Confirm", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openAlertDialog.value = false
                    Toast.makeText(this, "Clicked on Dismiss", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Dismiss")
                }
            }
        )
    }

    @Composable
    fun CustomAlertDialogSample(closeOnBackPress: Boolean, closeOnOutsideClick: Boolean) {
        AlertDialog(
            properties = DialogProperties(
                dismissOnBackPress = closeOnBackPress,
                dismissOnClickOutside = closeOnOutsideClick
            ),
            onDismissRequest = {
                openCustomAlertDialog.value = false
            },
            title = {
                Text("Custom Alert Dialog Title")
            },
            text = {
                Text("Here is a description of the custom alert dialog")
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            openCustomAlertDialog.value = false
                            Toast.makeText(
                                this@DialogActivity,
                                "Clicked on Dismiss",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }
}