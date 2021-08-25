package com.jetpack.compose.learning.constraintlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class BarrierActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                BarrierSample()
            }
        }
    }

    @Preview
    @Composable
    fun BarrierSample() {

        Column(Modifier.background(MaterialTheme.colors.background)) {
            TopAppBar(
                title = { Text("Barrier") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConstraintLayout {

                    val (text1, text2, text3) = createRefs()
                    val barrier = createEndBarrier(text1)

                    Text(text = "Created Barrier view",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .constrainAs(text1) {
                                top.linkTo(parent.top, margin = 16.dp)
                            }
                            .background(colorResource(R.color.blue))
                            .padding(10.dp)
                    )
                    Text(text = "Left to Barrier",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier
                            .constrainAs(text2) {
                                top.linkTo(text1.bottom, margin = 16.dp)
                                end.linkTo(barrier)
                            }
                            .background(colorResource(R.color.fruit))
                            .padding(10.dp)
                    )
                    Text(text = "Right to Barrier",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier
                            .constrainAs(text3) {
                                top.linkTo(parent.top, margin = 16.dp)
                                start.linkTo(barrier)
                            }
                            .background(colorResource(R.color.pink))
                            .padding(10.dp)
                    )
                }
            }
        }
    }

}