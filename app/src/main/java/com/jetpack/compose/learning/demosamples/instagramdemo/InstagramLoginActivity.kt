package com.jetpack.compose.learning.demosamples.instagramdemo

import android.content.Intent
import android.os.Bundle
import androidx.compose.ui.graphics.Color
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.R

class InstagramLoginActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                LoginScreen()
            }
        }
    }

    @Composable
    fun LoginScreen() {
        val context = LocalContext.current
        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val emptyEmailError = remember { mutableStateOf(false) }
        val emptyPasswordError = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_instagram_text),
                contentDescription = "Instagram",
                modifier = Modifier
                    .width(200.dp)
                    .align(alignment = Alignment.CenterHorizontally),
            )
            Spacer(Modifier.size(25.dp))
            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    if (emptyEmailError.value) {
                        emptyEmailError.value = false
                    }
                    email.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Email")
                },
                isError = emptyEmailError.value,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(android.graphics.Color.parseColor("#0095F6")),
                    cursorColor = Color(android.graphics.Color.parseColor("#0095F6")),
                    focusedLabelColor = Color(android.graphics.Color.parseColor("#0095F6")))
            )
            if (emptyEmailError.value) {
                Text(text = "Email is Required", color = Color.Red)
            }

            Spacer(Modifier.size(16.dp))
            val passwordVisibility = remember { mutableStateOf(true) }

            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    if (emptyPasswordError.value) {
                        emptyPasswordError.value = false
                    }
                    password.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Password")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "visibility"
                        )
                    }
                },
                visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(android.graphics.Color.parseColor("#0095F6")),
                    cursorColor = Color(android.graphics.Color.parseColor("#0095F6")),
                    focusedLabelColor = Color(android.graphics.Color.parseColor("#0095F6")))
            )
            if (emptyPasswordError.value) {
                Text(text = "Password is Required", color = Color.Red)
            }

            Spacer(Modifier.size(25.dp))

            Button(
                onClick = {
                    when {
                        email.value.text.isEmpty() -> {
                            emptyEmailError.value = true
                        }
                        password.value.text.isEmpty() -> {
                            emptyPasswordError.value = true
                        }
                        else -> {
                            context.startActivity(Intent(context, InstagramHomeActivity::class.java))
                        }
                    }
                },
                content = {
                    Text(text = "Login", color = Color.White)
                },
                modifier = Modifier
                    .height(height = 45.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(android.graphics.Color.parseColor("#0095F6")))
            )

            Spacer(Modifier.size(8.dp))

            Text(
                text = "Forget your login details? Get Help logging in",
                color = Color.Gray,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )

            Spacer(Modifier.size(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Divider(Modifier.fillMaxWidth(0.45f), color = Color.Gray, thickness = 1.dp)
                Spacer(Modifier.size(8.dp))
                Text(text = "OR", color = Color.Gray, fontWeight = FontWeight.Bold)
                Spacer(Modifier.size(8.dp))
                Divider(Modifier.fillMaxWidth(), color = Color.Gray, thickness = 1.dp)
            }

            Spacer(Modifier.size(24.dp))

            Text(
                text = "Login With Facebook",
                color = Color(android.graphics.Color.parseColor("#0095F6")),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            )
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(text = "Don't have account? Signup", color = Color.Gray, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        }
    }

    @Preview
    @Composable
    fun PreviewLogin() {
        LoginScreen()
    }
}
