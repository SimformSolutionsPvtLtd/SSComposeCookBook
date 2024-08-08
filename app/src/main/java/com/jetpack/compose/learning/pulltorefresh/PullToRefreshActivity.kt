package com.jetpack.compose.learning.pulltorefresh

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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

class PullToRefreshActivity : ComponentActivity() {

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
                title = { Text("Pull To Refresh") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }) { contentPadding ->
            Column(modifier = Modifier.padding(contentPadding)) {
                Button(
                        onClick = {
                            startActivity(Intent(context, SimplePullToRefresh::class.java))
                        }, modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Simple Pull To Refresh",
                            textAlign = TextAlign.Center,
                            color = Color.White
                    )
                }
                Button(
                        onClick = {
                            startActivity(Intent(context, CustomBackgroundPullToRefreshActivity::class.java))
                        }, modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Custom Background Pull To Refresh",
                            textAlign = TextAlign.Center,
                            color = Color.White
                    )
                }
                Button(
                        onClick = {
                            startActivity(Intent(context, CustomViewPullToRefreshActivity::class.java))
                        }, modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Custom View Pull To Refresh",
                            textAlign = TextAlign.Center,
                            color = Color.White
                    )
                }
            }
        }
    }
}