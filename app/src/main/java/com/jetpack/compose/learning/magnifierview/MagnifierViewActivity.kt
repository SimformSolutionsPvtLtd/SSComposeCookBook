package com.jetpack.compose.learning.magnifierview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
class MagnifierViewActivity : AppCompatActivity() {

    companion object {
        const val MAGNIFIER_ZOOM_FACTOR = 2F
    }
    private val Offset.Companion.default
        get() = Offset(0F, 1000F)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }

    @Composable
    private fun MainContent() {
        val systemUiController = remember { SystemUiController(window) }
        val appTheme = remember { mutableStateOf(AppThemeState()) }
        BaseView(appTheme.value, systemUiController) {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("MagnifierView") }, navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    })
                }
            ) {
                MagnifierScreen()
            }
        }
    }



    @OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
    @Composable
    fun MagnifierScreen() {

        var centerOffset by remember { mutableStateOf(Offset.default) }

        /**
         * size of Magnifier should be hidden at beginning.
         */
        var size by remember { mutableStateOf(DpSize(2.dp, 2.dp)) }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .onGloballyPositioned { centerOffset = Offset.default }
                    .magnifier(
                        sourceCenter = { centerOffset },
                        magnifierCenter = { centerOffset },
                        zoom = MAGNIFIER_ZOOM_FACTOR,
                        style = MagnifierStyle(
                            size = size,
                            fishEyeEnabled = true,
                            elevation = 0.dp
                        )
                    )
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                //setting magnifier glass size
                                size = DpSize(200.dp, 200.dp)
                                //offset.x and offset.y are coerced within viewport
                                centerOffset = Offset(
                                    it.x.coerceAtLeast(0f),
                                    it.y.coerceAtLeast(0f)
                                )
                            }
                            MotionEvent.ACTION_MOVE -> {
                                //user keeps move finger on the image
                                centerOffset = Offset(
                                    it.x.coerceAtLeast(0f),
                                    it.y.coerceAtLeast(0f)
                                )
                            }
                            MotionEvent.ACTION_UP -> {
                                //user leaves the screen
                                centerOffset = Offset.default
                            }
                        }
                        true
                    },
                painter = painterResource(id = R.drawable.jetpack),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        }
    }

}