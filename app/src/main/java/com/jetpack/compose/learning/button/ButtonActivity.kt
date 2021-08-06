package com.jetpack.compose.learning.button

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class ButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                ButtonExamples()
            }
        }
    }
}

@Composable
fun ButtonExamples() {
    val context = LocalContext.current
    Button(
        onClick = {
            Toast.makeText(context, "Simple Button with Border Clicked", Toast.LENGTH_SHORT).show()
        },
        border = BorderStroke(width = 4.dp, Color.Red),
        modifier = Modifier
            .width(300.dp)
            .height(50.dp)
    ) {
        Text(text = "Simple Button")
    }

    Button(
        onClick = {
            Toast.makeText(context, "Simple Disable Button Clicked", Toast.LENGTH_SHORT).show()
        }, enabled = false,
        shape = MaterialTheme.shapes.large
    ) {
        Text(text = "Disable Button")
    }

    OutlinedButton(onClick = {
        Toast.makeText(context, "Outlined Button Clicked", Toast.LENGTH_SHORT).show()
    }) {
        Text("Outlined Button")
    }

    TextButton(onClick = {
        Toast.makeText(context, "Text Button Clicked", Toast.LENGTH_SHORT).show()
    }) {
        Text("Text Button")
    }

    IconButton(onClick = {
        Toast.makeText(context, "Icon Button Clicked", Toast.LENGTH_SHORT).show()
    }) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Alarm Button",
            tint = Color.Red,
            modifier = Modifier.size(80.dp)
        )
    }

    Button(
        onClick = {
            Toast.makeText(context, "Icon Button wih Text Clicked", Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
            contentColor = Color.White
        )
    ) {
        Icon(
            imageVector = Icons.Filled.PersonAdd,
            contentDescription = "Icon with Text Button",
            tint = Color.Blue,
            modifier = Modifier.padding(all = 4.dp)
        )
        Text(text = "Icon Button")
    }

    val horizontalGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xffF57F17),
            Color(0xffFFEE58),
            Color(0xffFFF9C4)
        )
    )

    val verticalGradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xffF57F17),
            Color(0xffFFEE58),
            Color(0xffFFF9C4)
        )
    )

    TextButton(onClick = {
        Toast.makeText(context, "Horizontal Gradient Button Clicked", Toast.LENGTH_SHORT).show()
    }, Modifier.background(brush = horizontalGradientBrush)) {
        Text(text = "Horizontal Gradient Button")
    }

    TextButton(onClick = {
        Toast.makeText(context, "Vertical Gradient Button Clicked", Toast.LENGTH_SHORT).show()
    }, Modifier.background(brush = verticalGradientBrush)) {
        Text(text = "Vertical Gradient Button")
    }
}



