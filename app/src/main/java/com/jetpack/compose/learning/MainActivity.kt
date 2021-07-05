package com.jetpack.compose.learning

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.recyclerview.MovieList
import com.jetpack.compose.learning.recyclerview.viewmodel.MovieViewModel
import com.jetpack.compose.learning.textstyle.SimpleTextActivity

class MainActivity : AppCompatActivity() {

    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column{
                ButtonComponent(
                    intent = Intent(applicationContext, SimpleTextActivity::class.java),
                    buttonText = "Text Styling"
                )
            }
            MovieList(viewModel = movieViewModel, context = this)
        }
    }

    @Composable
    fun ButtonComponent( intent: Intent, buttonText: String) {
        var context= LocalContext.current
        Button(
            onClick = {
                context.startActivity(intent)
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                text = buttonText,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}