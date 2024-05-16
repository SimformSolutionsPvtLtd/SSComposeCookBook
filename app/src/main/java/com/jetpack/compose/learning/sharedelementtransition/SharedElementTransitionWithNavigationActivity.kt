package com.jetpack.compose.learning.sharedelementtransition

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class SharedElementTransitionWithNavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                SharedElementTransitionWithNavigation()
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedElementTransitionWithNavigation() {
        val navController = rememberNavController()
        val albums = DataProvider.getAlbumsData()
        val boundsTransform = { _: Rect, _: Rect -> tween<Rect>(500) }

        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Shared Element Transition with Navigation") },
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
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            itemsIndexed(albums) { index, album ->
                                Box(
                                    modifier = Modifier
                                        .sharedElement(
                                            rememberSharedContentState(key = "image-$index"),
                                            animatedVisibilityScope = this@composable,
                                            boundsTransform = boundsTransform
                                        )
                                        .clickable {
                                            navController.navigate("details/$index")
                                        }
                                ) {
                                    Image(
                                        painter = painterResource(id = album.cover),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .aspectRatio(1f)
                                            .clip(RoundedCornerShape(10.dp))
                                    )
                                }
                            }
                        }
                    }

                    composable(
                        "details/{item}",
                        arguments = listOf(navArgument("item") { type = NavType.IntType })
                    ) { navBackStackEntry ->
                        val albumId = navBackStackEntry.arguments?.getInt("item")
                        val album = albums[albumId!!]

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .sharedElement(
                                    rememberSharedContentState(key = "image-$albumId"),
                                    animatedVisibilityScope = this@composable,
                                    boundsTransform = boundsTransform
                                )
                                .clickable { navController.navigate("preview") }
                        ) {
                            Image(
                                painterResource(id = album.cover),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(500.dp)
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = album.title,
                                fontSize = 20.sp,
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = album.author,
                                fontSize = 20.sp,
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = album.year.toString(),
                                fontSize = 20.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}