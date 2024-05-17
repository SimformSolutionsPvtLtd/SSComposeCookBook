package com.jetpack.compose.learning.sharedelementtransition

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

    @OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun ImageAnimationExample() {
        val navController = rememberNavController()
        val boundsTransform = { _: Rect, _: Rect -> tween<Rect>(1000) }
        val images = DataProvider.getImagesData()

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
                NavHost(navController = navController, startDestination = "preview") {
                    composable("preview") {
                        Column {
                            LazyRow(
                                contentPadding = PaddingValues(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                items(images) {
                                    Box(
                                        modifier = Modifier
                                            .size(100.dp, 150.dp)
                                            .background(
                                                Color.White,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .clickable { navController.navigate("details/${it.id}") }
                                            .sharedElement(
                                                rememberSharedContentState(key = "image-${it.id}"),
                                                animatedVisibilityScope = this@composable,
                                                boundsTransform = boundsTransform,
                                                placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize
                                            )
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

                            LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(10.dp)) {
                                itemsIndexed(images) { index, item ->
                                    Image(
                                        painter = painterResource(id = item.image),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }

                    composable("details/{item}", arguments = listOf(navArgument("item") { type = NavType.IntType })) {
                        val imageId = it.arguments?.getInt("item")
                        val image = images[imageId!!]

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .sharedElement(
                                    rememberSharedContentState(key = "image-$imageId"),
                                    animatedVisibilityScope = this@composable,
                                    boundsTransform = boundsTransform
                                )
                                .clickable { navController.navigate("preview") }
                        ) {
                            Image(
                                painterResource(id = image.image),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(500.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}