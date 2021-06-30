package com.mindorks.example.jetpack.compose.text

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BaseTextField
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TextFieldActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScrollableColumn {

                SimpleTextComponent("Simple Text Field")
                SimpleTextFieldComponent()
                Divider(color = Color.Gray)

            }
        }
    }
}

@Composable
fun SimpleTextComponent(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            color = Color.Black
        ),
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    )
}

@ExperimentalFoundationApi
@Composable
fun SimpleTextFieldComponent() {
    // Surface as the name suggests is used to have some UI elements over it. You can provide some
    // color, contentColor, shape, border, elevation, etc to a surface. If no contentColor is passed
    // then the surface will try to match its color with the background color.
    Surface(color = Color.LightGray, modifier = Modifier.padding(16.dp)) {
        var text by remember { mutableStateOf(TextFieldValue("Enter text here")) }
        // BaseTextField is a composable that is used to take input. It is similar to EditText.
        // onValueChange will be called when there is a change in content of text field.
        BaseTextField(
            value = text,
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            onValueChange = {
                text = it
            }
        )
    }
}


@ExperimentalFoundationApi
@Composable
fun NumberTextFieldComponent() {
    Surface(color = Color.LightGray, modifier = Modifier.padding(16.dp)) {
        var text by remember { mutableStateOf(TextFieldValue("0123")) }
        // Here, only numerical keyboard will be opened
        BaseTextField(value = text,
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            keyboardType = KeyboardType.Number,
            onValueChange = {
                text = it
            }
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun PasswordTextFieldComponent() {
    Surface(color = Color.LightGray, modifier = Modifier.padding(16.dp)) {
        var text by remember { mutableStateOf(TextFieldValue("9876")) }
        // This is an example of Text Field taking password as an input. It will
        // hide the password text and will show a dot.
        BaseTextField(value = text,
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                text = it
            }
        )
    }
}

@Composable
fun SimpleMaterialTextFieldComponent() {
    // savedInstanceState behaves similarly to remember { mutableStateOf(...) }, but the stored value
    // will survive the activity or process recreation using the saved instance state mechanism
    // (for example it happens when the screen is rotated in the Android application)
    var text by savedInstanceState { "" }
    // TextField is a Material Design implementation of EditText in Compose.
    // label is the text that is displayed inside the TextField when no text is there
    TextField(
        value = text,
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}

@Composable
fun PlaceholderMaterialTextFieldComponent() {
    // savedInstanceState behaves similarly to remember { mutableStateOf(...) }, but the stored value
    // will survive the activity or process recreation using the saved instance state mechanism
    // (for example it happens when the screen is rotated in the Android application)
    var text by savedInstanceState { "" }
    // placeholder is displayed when the TextField is focused and is empty.
    TextField(
        value = text,
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        onValueChange = { text = it },
        label = { Text("Enter Name") },
        placeholder = { Text("MindOrks") }
    )
}

@Composable
fun IconMaterialTextFieldComponent() {
    // savedInstanceState behaves similarly to remember { mutableStateOf(...) }, but the stored value
    // will survive the activity or process recreation using the saved instance state mechanism
    // (for example it happens when the screen is rotated in the Android application)
    var text by savedInstanceState { "" }
    // You can put some icon in a TextField to make it more awesome.
    // leadingIcon will put the icon in front and trailingIcon to the end of TextField
    TextField(
        value = text,
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        onValueChange = { text = it },
        label = { Text("Enter Name") },
        placeholder = { Text("MindOrks") },
        leadingIcon = { Icon(Icons.Filled.Person) },
        trailingIcon = { Icon(Icons.Filled.Done) }
    )
}

@Composable
fun ErrorMaterialTextFieldComponent() {
    // savedInstanceState behaves similarly to remember { mutableStateOf(...) }, but the stored value
    // will survive the activity or process recreation using the saved instance state mechanism
    // (for example it happens when the screen is rotated in the Android application)
    var text by savedInstanceState { "" }
    val isValidPhoneNumber = text.count() == 10
    // isErrorValue is used to show some error while entering text.
    TextField(
        value = text,
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        onValueChange = { text = it },
        label = {
            val label = if (isValidPhoneNumber) "Phone Number" else "Phone Number*"
            Text(label)
        },
        keyboardType = KeyboardType.Number,
        isErrorValue = !isValidPhoneNumber
    )
}
