package com.jetpack.compose.learning.sharedelementtransition

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.sharedelementtransition.model.CoffeeModel
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class SharedElementTransitionWithoutNavigationActivity : ComponentActivity() {

    private val boundsTransform = { _: Rect, _: Rect ->
        tween<Rect>(500)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                SharedElementTransitionWithoutNavigation()
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedElementTransitionWithoutNavigation() {
        var selectedCoffee by remember { mutableStateOf<CoffeeModel?>(null) }
        var showDetails by remember { mutableStateOf(false) }
        val coffeeList = DataProvider.getCoffeDetails()

        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Shared Element Transition without Navigation") },
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
                        PreviewContent(coffeeList = coffeeList, animatedVisibilityScope = this) {
                            selectedCoffee = it
                            showDetails = true
                        }
                    } else {
                        selectedCoffee?.let { coffee ->
                            PreviewDetailContent(coffee = coffee, animatedVisibilityScope = this) {
                                showDetails = false
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
        coffeeList: List<CoffeeModel>,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onShowDetails: (CoffeeModel) -> Unit,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = 20.dp,
                vertical = 10.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(coffeeList) { coffee ->
                CoffeeItem(
                    animatedVisibilityScope = animatedVisibilityScope,
                    coffee = coffee
                ) {
                    onShowDetails(coffee)
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.CoffeeItem(
        animatedVisibilityScope: AnimatedVisibilityScope,
        coffee: CoffeeModel,
        onClick: () -> Unit,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
                .clickable(onClick = onClick)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = coffee.image),
                contentDescription = null,
                modifier = Modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(key = coffee.id),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = boundsTransform
                    )
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = coffee.name,
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.PreviewDetailContent(
        coffee: CoffeeModel,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onBack: () -> Unit,
    ) {
        Box(
            modifier = Modifier
                .sharedElement(
                    state = rememberSharedContentState(key = coffee.id),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform
                )
                .fillMaxSize()
                .padding(10.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.LightGray.copy(alpha = 0.5f))
        ) {
            Column {
                CoffeeDetailHeader(coverRes = coffee.image, onBackClick = onBack)
                CoffeeDetailDescription(coffee = coffee)
            }
        }
    }

    @Composable
    fun CoffeeDetailHeader(coverRes: Int, onBackClick: () -> Unit) {
        Box {
            Image(
                painterResource(id = coverRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp)
                    .clip(RoundedCornerShape(30.dp))
            )

            Box(
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White)
                    .clickable(onClick = onBackClick)
            ) {
                Icon(
                    Icons.Outlined.ArrowBack,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(10.dp),
                    contentDescription = null
                )
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.CoffeeDetailDescription(coffee: CoffeeModel) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(
                text = "About",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 26.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = coffee.name,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = coffee.description,
                modifier = Modifier
                    .padding(10.dp)
                    .skipToLookaheadSize()
            )
        }
    }
}