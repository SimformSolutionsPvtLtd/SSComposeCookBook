package com.jetpack.compose.learning.textstyle

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class SimpleTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SimpleText("This is text styling demo!!")
            }
        }
    }
}

// This is a Composable function. To write any Composable function, you need to use @Composable annotation
@Composable
fun SimpleText(displayText: String) {
    // Text composable is used to display some text
    Text(text = displayText)
}


@Preview
@Composable
fun SimpleTextPreview() {
    SimpleText("learning Compose")
}