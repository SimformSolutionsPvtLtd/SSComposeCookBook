package com.jetpack.compose.learning.sharedelementtransition

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class ImageAnimationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                ImageAnimationExample()
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun ImageAnimationExample() {
        var showDetails by remember { mutableStateOf(false) }

        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Image Animation") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }) {
            SharedTransitionLayout {
                AnimatedContent(targetState = showDetails, label = "transition") { targetState ->
                    if (!targetState) {
                        LazyRow(
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)

                        ) {
                            items(DataProvider.getImagesData()) {
                                Box(
                                    modifier = Modifier
                                        .size(100.dp, 150.dp)
                                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                                        .clickable { showDetails = true }
                                ) {
                                    Image(
                                        painter = painterResource(id = it.image),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(10.dp))
                                    )
                                    Text(
                                        text = it.title,
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.BottomStart)
                                    )
                                }
                            }
                        }
                    } else {
                        ImageDetailScreen()
                    }
                }

            }
        }
    }

    @Composable
    fun ImageDetailScreen() {

    }
}