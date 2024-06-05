package com.jetpack.compose.learning.sharedelementtransition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.sharedelementtransition.model.ProfileModel
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class FabComponentAnimationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appThemeState = appTheme.value, systemUiController = systemUiController) {
                FabComponentAnimation()
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun FabComponentAnimation() {
        var showDetails by remember { mutableStateOf(false) }

        SharedTransitionLayout {
            AnimatedContent(targetState = showDetails, label = "transition") { targetState ->
                if (!targetState) {
                    FabMainContent(this) {
                        showDetails = !showDetails
                    }
                } else {
                    FabDetailContent(this) {
                        showDetails = !showDetails
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.FabMainContent(
        animatedVisibilityScope: AnimatedVisibilityScope,
        onFabClick: () -> Unit
    ) {
        Column {
            FloatingActionButton(
                onClick = {
                    onFabClick()
                },
                backgroundColor = colorResource(id = R.color.bright_cyan),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 20.dp, top = 20.dp)
                    .sharedElement(
                        rememberSharedContentState(key = "fab"),
                        animatedVisibilityScope
                    )
            ) {
                Icon(Icons.Filled.Edit, contentDescription = "")
            }
        }

    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.FabDetailContent(
        animatedVisibilityScope: AnimatedVisibilityScope,
        onClick: () -> Unit
    ) {
        Column {
            LazyColumn(modifier = Modifier
                .padding(start = 20.dp, top = 20.dp)
                .width(180.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.LightGray.copy(alpha = 0.5f))
                .clickable {
                    onClick()
                }
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                colorResource(id = R.color.bright_cyan),
                                RoundedCornerShape(40)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(32.dp))
                        Text(text = "Edit", color = Color.Black, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(32.dp))
                        FloatingActionButton(
                            onClick = {},
                            backgroundColor = colorResource(id = R.color.bright_cyan),
                            shape = RoundedCornerShape(16.dp),
                            elevation = FloatingActionButtonDefaults.elevation(0.dp),
                            modifier = Modifier
                                .clickable(false) {}
                                .size(70.dp)
                                .sharedElement(
                                    rememberSharedContentState(key = "fab"),
                                    animatedVisibilityScope
                                )
                        ) {
                            Icon(Icons.Filled.Edit, contentDescription = "")
                        }
                    }
                }
                items(DataProvider.getFabProfiles()) { profile: ProfileModel ->
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(25.dp)),
                            painter = painterResource(id = profile.image),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = profile.name, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}