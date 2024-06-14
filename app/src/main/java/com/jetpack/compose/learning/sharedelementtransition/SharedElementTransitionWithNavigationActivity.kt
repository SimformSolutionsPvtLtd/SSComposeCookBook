package com.jetpack.compose.learning.sharedelementtransition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.sharedelementtransition.model.AlbumModel
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class SharedElementTransitionWithNavigationActivity : ComponentActivity() {

    private val boundsTransform = { _: Rect, _: Rect ->
        tween<Rect>(500)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
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
                    SharedElementTransitionWithNavigation(Modifier.padding(it))
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedElementTransitionWithNavigation(modifier: Modifier) {
        val albums = DataProvider.getAlbumsData()
        val navController = rememberNavController()

        SharedTransitionLayout {
            NavHost(navController = navController, startDestination = "preview") {
                composable("preview") {
                    PreviewContent(
                        modifier = modifier,
                        albums = albums,
                        animatedVisibilityScope = this
                    ) { index ->
                        navController.navigate("details/$index") {
                            popUpTo("preview") {
                                inclusive = true
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

                    PreviewDetailContent(
                        modifier = modifier,
                        album = album,
                        animatedVisibilityScope = this@composable
                    ) {
                        navController.navigate("preview") {
                            popUpTo("details/$albumId") {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.PreviewContent(
        modifier: Modifier,
        albums: List<AlbumModel>,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onItemClick: (Int) -> Unit
    ) {
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(albums) { index, album ->
                AlbumItem(album.cover, index, animatedVisibilityScope) {
                    onItemClick(index)
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.AlbumItem(
        coverRes: Int,
        index: Int,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onClick: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(key = "image-$index"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform
                )
                .clickable(onClick = onClick)
        ) {
            Image(
                painter = painterResource(id = coverRes),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.PreviewDetailContent(
        modifier: Modifier,
        album: AlbumModel,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onBackClick: () -> Unit
    ) {
        Box(
            modifier = modifier
                .sharedElement(
                    rememberSharedContentState(key = "image-${album.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform
                )
                .fillMaxSize()
                .padding(10.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.LightGray.copy(alpha = 0.5f))
        ) {
            Column {
                AlbumDetailHeader(
                    modifier = modifier,
                    coverRes = album.cover,
                    onBackClick = onBackClick
                )
                AlbumDetailInfo(modifier = modifier, album = album)
                AlbumDetailDescription(modifier = modifier)
            }
        }
    }

    @Composable
    fun AlbumDetailHeader(modifier: Modifier, coverRes: Int, onBackClick: () -> Unit) {
        Box {
            Image(
                painterResource(id = coverRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(400.dp)
                    .clip(RoundedCornerShape(30.dp))
            )

            Box(
                modifier = modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White)
                    .clickable(onClick = onBackClick)
            ) {
                Icon(
                    Icons.Outlined.ArrowBack,
                    modifier = modifier
                        .size(50.dp)
                        .padding(10.dp),
                    contentDescription = null
                )
            }
        }
    }

    @Composable
    fun AlbumDetailInfo(modifier: Modifier, album: AlbumModel) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Column {
                    Text(
                        text = album.title,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "${album.author}, ${album.year}",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = modifier.weight(1f))
                IconButton(
                    onClick = { /* Handle play action */ },
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color.Magenta.copy(alpha = 0.3f))
                ) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.AlbumDetailDescription(modifier: Modifier) {
        Column(modifier = modifier.padding(10.dp)) {
            Text(
                text = "About",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 26.sp,
                modifier = modifier.padding(start = 10.dp)
            )
            Text(
                text = stringResource(R.string.album_description),
                modifier = modifier
                    .padding(10.dp)
                    .skipToLookaheadSize()
            )
        }
    }
}