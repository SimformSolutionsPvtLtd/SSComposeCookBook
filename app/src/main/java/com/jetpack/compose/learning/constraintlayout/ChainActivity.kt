package com.jetpack.compose.learning.constraintlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
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
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class ChainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                ChainSample()
            }
        }
    }

    @Preview
    @Composable
    fun ChainSample() {
        val spaceHeight = 24.dp
        Column(Modifier.background(MaterialTheme.colors.background).fillMaxHeight()) {
            TopAppBar(
                title = { Text("Chain") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            Spacer(Modifier.requiredHeight(spaceHeight))
            Text(
                text = "Packed : ",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(20.dp)
            )
            HorizontalPackedChain()
            Spacer(Modifier.requiredHeight(spaceHeight))
            Text(
                text = "Spread : ",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(20.dp)
            )
            HorizontalSpreadChain()
            Spacer(Modifier.requiredHeight(spaceHeight))
            Text(
                text = "SpreadInside : ",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(20.dp)
            )
            HorizontalSpreadInsideChain()
        }
    }

    @Composable
    fun HorizontalPackedChain() {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (text1, text2, text3) = createRefs()

            createHorizontalChain(text1, text2, text3, chainStyle = ChainStyle.Packed)

            Text(text = "View 1",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(text1) {
                    }
                    .background(colorResource(R.color.blue))
                    .padding(10.dp)
            )
            Text(text = "View 2",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(text2) {
                    }
                    .background(colorResource(R.color.pink))
                    .padding(10.dp)
            )
            Text(text = "View 3",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(text3) {
                    }
                    .background(colorResource(R.color.fruit))
                    .padding(10.dp)
            )
        }
    }

    @Composable
    fun HorizontalSpreadChain() {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (text1, text2, text3) = createRefs()

            createHorizontalChain(text1, text2, text3, chainStyle = ChainStyle.Spread)

            Text(text = "View 1",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(text1) {
                    }
                    .background(colorResource(R.color.blue))
                    .padding(10.dp)
            )
            Text(text = "View 2",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(text2) {
                    }
                    .background(colorResource(R.color.pink))
                    .padding(10.dp)
            )
            Text(text = "View 3",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(text3) {
                    }
                    .background(colorResource(R.color.fruit))
                    .padding(10.dp)
            )
        }
    }

    @Composable
    fun HorizontalSpreadInsideChain() {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (text1, text2, text3) = createRefs()

            createHorizontalChain(text1, text2, text3, chainStyle = ChainStyle.SpreadInside)

            Text(text = "View 1",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(text1) {
                    }
                    .background(colorResource(R.color.blue))
                    .padding(10.dp)
            )
            Text(text = "View 2",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(text2) {
                    }
                    .background(colorResource(R.color.pink))
                    .padding(10.dp)
            )
            Text(text = "View 3",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(text3) {
                    }
                    .background(colorResource(R.color.fruit))
                    .padding(10.dp)
            )
        }
    }
}