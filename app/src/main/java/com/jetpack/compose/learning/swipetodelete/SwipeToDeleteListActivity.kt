package com.jetpack.compose.learning.swipetodelete

import android.content.Context
import androidx.activity.ComponentActivity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

@ExperimentalMaterialApi
class SwipeToDeleteListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainContent()
            }
        }
    }

    @Preview
    @Composable
    fun MainContent() {
        val context = LocalContext.current
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Swipe To Delete") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }) { contentPadding ->
            Column(Modifier.padding(contentPadding)) {
                Button(
                    onClick = {
                        navigateToSwipeToDeleteActivity(context, SwipeDirection.LEFT)
                    }, modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Swipe Left",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
                Button(
                    onClick = {
                        navigateToSwipeToDeleteActivity(context, SwipeDirection.RIGHT)
                    }, modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Swipe Right",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
                Button(
                    onClick = {
                        navigateToSwipeToDeleteActivity(context, SwipeDirection.BOTH)
                    }, modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Swipe Left + Right",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
    }

    private fun navigateToSwipeToDeleteActivity(context: Context, swipeDirection: SwipeDirection) {
        val intent = Intent(context, SwipeToDeleteDirectionActivity::class.java)
        intent.putExtra("SwipeDirection", swipeDirection)
        startActivity(intent)
    }
}