package com.jetpack.compose.learning.textfield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

@ExperimentalComposeUiApi
class TextFieldActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(title = { Text("Text fields") }, navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    })
                }) { contentPadding ->
                    TextFieldExamples(Modifier.padding(contentPadding))
                }
            }
        }
    }

    @Preview
    @Composable
    private fun TextFieldExamples(modifier: Modifier = Modifier) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopCenter)
        ) {
            var simpleText by remember { mutableStateOf("") }
            var basicText by remember { mutableStateOf("This is a Basic Textfield") }
            var emailText by remember { mutableStateOf("") }
            var phoneNumberText by remember { mutableStateOf("") }
            var passwordText by remember { mutableStateOf("") }
            var differentColorText by remember { mutableStateOf("") }
            var passwordVisibility: Boolean by remember { mutableStateOf(false) }
            var showError: Boolean by remember { mutableStateOf(false) }
            val focusManager = LocalFocusManager.current
            val (simple, differentColor, email, phoneNumber, password) = remember { FocusRequester.createRefs() }

            Spacer(modifier = Modifier.height(50.dp))

            // Basic Textfield which does not contain placeholder or any decorations
            BasicTextField(
                value = basicText,
                onValueChange = {
                    basicText = it
                },
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { simple.requestFocus() })
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Simple textfield
            // With label and placeholder
            TextField(
                value = simpleText,
                onValueChange = { simpleText = it },
                label = { Text("Simple TextField") }, // It will be shown on the top when focussed
                placeholder = { Text("Placeholder text") }, // It will be shown as hint when nothing is typed
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                isError = showError,
                modifier = Modifier.focusRequester(simple),
                keyboardActions = KeyboardActions(onNext = { differentColor.requestFocus() })
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Textfield with different coloured text input
            TextField(
                value = differentColorText,
                onValueChange = { differentColorText = it },
                label = { Text("TextField with Annotated text") },
                placeholder = { Text("PlaceHolder text") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ),
                visualTransformation = if (differentColorText == "") VisualTransformation.None else ColorsTransformation(),
                isError = showError,
                modifier = Modifier.focusRequester(differentColor),
                keyboardActions = KeyboardActions(onNext = { email.requestFocus() })
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Simple Outlined textfield
            // without placeholder
            OutlinedTextField(
                value = emailText,
                onValueChange = { emailText = it },
                label = { Text("Enter email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                ),
                leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = "") },
                isError = showError,
                modifier = Modifier.focusRequester(email),
                keyboardActions = KeyboardActions(onNext = { phoneNumber.requestFocus() })
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Phone number textfield with max length as 10
            OutlinedTextField(
                value = phoneNumberText,
                onValueChange = {
                    if (it.length <= 10) phoneNumberText = it
                },
                label = { Text("Enter phonenumber") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next
                ),
                isError = showError,
                modifier = Modifier.focusRequester(phoneNumber),
                keyboardActions = KeyboardActions(onNext = { password.requestFocus() })
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Outlined Password textfield
            // Without label
            OutlinedTextField(
                value = passwordText,
                onValueChange = { passwordText = it },
                placeholder = { Text("Outlined password TextField") },
                singleLine = true,
                isError = showError,
                colors = TextFieldDefaults.outlinedTextFieldColors(disabledBorderColor = if (showError) Color.Red else Color.Gray),
                trailingIcon = {
                    val image = if (passwordVisibility) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    IconButton({ passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = image, "")
                    }
                },
                modifier = Modifier.focusRequester(password),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Button(
                    onClick = {
                        showError = true
                    },
                    Modifier
                        .wrapContentSize(Alignment.Center)
                        .padding(5.dp)
                ) {
                    Text(text = "Show Error")
                }
                Button(
                    onClick = {
                        showError = false
                    },
                    Modifier
                        .wrapContentSize(Alignment.Center)
                        .padding(5.dp)
                ) {
                    Text(text = "Remove Error")
                }
            }
        }

    }

}

class ColorsTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            buildAnnotatedStringWithColors(text.toString()), OffsetMapping.Identity
        )
    }

    private fun buildAnnotatedStringWithColors(text: String): AnnotatedString {
        val words: List<String> = text.split("\\s+".toRegex())// splits by whitespace
        val colors = listOf(Color.Magenta, Color.Red, Color.Blue, Color.Black)
        var count = 0
        val builder = AnnotatedString.Builder()
        for (word in words) {
            builder.withStyle(style = SpanStyle(color = colors[count % 4])) {
                append("$word ")
            }
            count++
        }
        return builder.toAnnotatedString()
    }
}