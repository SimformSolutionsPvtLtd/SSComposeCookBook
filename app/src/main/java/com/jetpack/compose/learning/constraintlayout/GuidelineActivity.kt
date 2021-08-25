package com.jetpack.compose.learning.constraintlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class GuidelineActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                GuidelineSample()
            }
        }
    }

    @Preview
    @Composable
    fun GuidelineSample() {

        Column(Modifier.background(MaterialTheme.colors.background)) {
            TopAppBar(
                title = { Text("Guideline") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )

            ConstraintLayout(Modifier.fillMaxSize()) {

                val (text1, text2, text3) = createRefs()
                val topGuideline = createGuidelineFromTop(0.3f)
                val startQuarterGuideline = createGuidelineFromStart(0.25f)
                val startHalfGuideline = createGuidelineFromStart(0.5f)

                Text(text = "Quarter\n(25% from Start)",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .constrainAs(text1) {
                            top.linkTo(topGuideline)
                            start.linkTo(startQuarterGuideline)
                        }
                        .background(colorResource(R.color.blue))
                        .padding(10.dp)
                )
                Text(text = "Center Horizontal",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .constrainAs(text2) {
                            top.linkTo(text1.bottom, margin = 16.dp)
                            centerHorizontallyTo(parent)
                        }
                        .background(colorResource(R.color.pink))
                        .padding(10.dp)
                )
                Text(text = "Half\n(50% from Start)",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .constrainAs(text3) {
                            top.linkTo(text2.bottom, margin = 16.dp)
                            start.linkTo(startHalfGuideline)
                        }
                        .background(colorResource(R.color.fruit))
                        .padding(10.dp)
                )
            }
        }
    }
}