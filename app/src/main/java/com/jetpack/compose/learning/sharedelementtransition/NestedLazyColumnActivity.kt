package com.jetpack.compose.learning.sharedelementtransition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.sharedelementtransition.model.ChildDataClass
import com.jetpack.compose.learning.sharedelementtransition.model.ParentDataClass
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController


class NestedLazyColumnActivity : ComponentActivity() {

    private val parentItemsList = ArrayList<ParentDataClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                NestedLazyColumnExample()
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun NestedLazyColumnExample() {
        val navController = rememberNavController()

        SharedTransitionLayout {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                setData()
                NavHost(navController = navController, startDestination = "Preview") {
                    composable("preview") {
                        Preview(
                            modifier = Modifier,
                            animatedVisibilityScope = this,
                            parentItemsList = parentItemsList
                        ) { parentIndex, childIndex ->
                            navController.navigate("details/$parentIndex/$childIndex")
                        }
                    }

                    composable(
                        route = "details/{parentIndex}/{childIndex}",
                        arguments = listOf(
                            navArgument("parentIndex") { type = NavType.IntType },
                            navArgument("childIndex") { type = NavType.IntType }
                        )
                    ) { navBackStackEntry ->
                        val parentIndex = navBackStackEntry.arguments?.getInt("parentIndex")
                        val childIndex = navBackStackEntry.arguments?.getInt("childIndex")
                        val child = parentItemsList[parentIndex!!].childList[childIndex!!]
                        PreviewDetail(childDataClass = child, animatedVisibilityScope = this)
                    }
                }
            }
        }
    }

    private fun setData() {
        val images = listOf(
            ChildDataClass(0, R.drawable.ic_post_image_1),
            ChildDataClass(1, R.drawable.ic_post_image_2),
            ChildDataClass(2, R.drawable.ic_post_image_3),
            ChildDataClass(3, R.drawable.ic_post_image_4),
            ChildDataClass(4, R.drawable.ic_post_image_5),
            ChildDataClass(5, R.drawable.ic_post_image_6),
            ChildDataClass(6, R.drawable.ic_post_image_7),
        )
        parentItemsList.add(ParentDataClass(0, "Novel:", images))
        parentItemsList.add(ParentDataClass(1, "Best Seller:", images))
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.Preview(
        modifier: Modifier,
        animatedVisibilityScope: AnimatedVisibilityScope,
        parentItemsList: ArrayList<ParentDataClass>,
        onItemClick: (Int, Int) -> Unit
    ) {
        LazyColumn(contentPadding = PaddingValues(10.dp)) {
            items(parentItemsList) { parentItem ->
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.LightGray)
                ) {
                    Column {
                        Text(
                            text = parentItemsList[parentItem.id].title,
                            modifier.padding(12.dp),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        LazyRow(contentPadding = PaddingValues(12.dp)) {
                            items(parentItemsList[parentItem.id].childList) { childItem ->
                                Box(
                                    modifier
                                        .height(200.dp)
                                        .width(160.dp)
                                        .padding(horizontal = 10.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xF1201E1F))
                                        .clickable {
                                            onItemClick(
                                                parentItem.id,
                                                childItem.id
                                            )
                                        }

                                ) {
                                    Image(
                                        painter = painterResource(id = childItem.image),
                                        contentDescription = "",
                                        contentScale = ContentScale.Crop,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.PreviewDetail(
        childDataClass: ChildDataClass,
        animatedVisibilityScope: AnimatedVisibilityScope
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = childDataClass.image),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.sharedElement(
                    rememberSharedContentState(key = "image"),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
            )
        }
    }
}
