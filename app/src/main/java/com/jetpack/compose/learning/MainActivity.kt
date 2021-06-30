package com.jetpack.compose.learning

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.recyclerview.MovieList
import com.jetpack.compose.learning.textstyle.SimpleTextActivity

class MainActivity : AppCompatActivity() {

    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScrollableColumn {
                val context = ContextAmbient.current
                ButtonComponent(
                    context = context,
                    intent = Intent(context, SimpleTextActivity::class.java),
                    buttonText = "Text Styling"
                )
            }
            MovieList(viewModel = movieViewModel, context = this)
        }
    }

    @Composable
    fun ButtonComponent(context: Context, intent: Intent, buttonText: String) {
        Button(
            onClick = {
                context.startActivity(intent)
            },
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = colorResource(R.color.purple_200)
        ) {
            Text(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                text = buttonText,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}