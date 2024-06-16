package com.jetpack.compose.learning.sharedelementtransition

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.sharedelementtransition.model.CoffeeModel
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

@OptIn(ExperimentalSharedTransitionApi::class)
class SharedElementTransitionWithoutNavigationActivity : ComponentActivity() {

    /**
     * Transformation for the shared element bounds.
     * Defines the tween animation for the shared element transitions.
     */
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
                        title = { Text(stringResource(R.string.shared_element_transition_without_navigation)) },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                }) {
                    SharedElementTransitionWithoutNavigation(Modifier.padding(it))
                }
            }
        }
    }

    /**
     * Composable function that sets up the navigation and shared element transitions
     * for the coffee preview and detail screens.
     */
    @Composable
    fun SharedElementTransitionWithoutNavigation(modifier: Modifier = Modifier) {
        var selectedCoffee by remember { mutableStateOf<CoffeeModel?>(null) }
        var showDetails by remember { mutableStateOf(false) }
        val coffeeList = DataProvider.getCoffeeDetails()

        SharedTransitionLayout {
            AnimatedContent(targetState = showDetails, label = "transition") { targetState ->
                if (!targetState) {
                    PreviewContent(
                        modifier = modifier,
                        coffeeList = coffeeList,
                        animatedVisibilityScope = this
                    ) {
                        selectedCoffee = it
                        showDetails = true
                    }
                } else {
                    selectedCoffee?.let { coffee ->
                        PreviewDetailContent(
                            modifier = modifier,
                            coffee = coffee,
                            animatedVisibilityScope = this
                        ) {
                            showDetails = false
                        }
                    }
                }
            }
        }
    }

    /**
     * Composable function that displays the list of coffee items.
     */
    @Composable
    fun SharedTransitionScope.PreviewContent(
        modifier: Modifier = Modifier,
        coffeeList: List<CoffeeModel>,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onShowDetails: (CoffeeModel) -> Unit,
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
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

    /**
     * Composable function that displays a single coffee item with a shared element.
     */
    @Composable
    fun SharedTransitionScope.CoffeeItem(
        modifier: Modifier = Modifier,
        animatedVisibilityScope: AnimatedVisibilityScope,
        coffee: CoffeeModel,
        onItemClick: () -> Unit,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
                .clickable(onClick = onItemClick)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = coffee.image),
                contentDescription = null,
                modifier = modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(key = coffee.id),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = boundsTransform
                    )
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = modifier.width(16.dp))

            Column {
                Text(
                    text = coffee.name,
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }

    /**
     * Composable function that displays the details of a selected coffee item.
     */
    @Composable
    fun SharedTransitionScope.PreviewDetailContent(
        modifier: Modifier = Modifier,
        coffee: CoffeeModel,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onBack: () -> Unit,
    ) {
        Box(
            modifier = modifier
                // Adding shared element for the detailed view of the coffee item
                // The sharedElement modifier is used to specify the shared element for the transition.
                // - `rememberSharedContentState(key = coffee.id)` creates a state holder for the shared element with a unique key.
                // - `animatedVisibilityScope` provides the scope for managing visibility changes during the transition.
                // - `boundsTransform` defines the transformation applied to the bounds of the shared element during the transition.
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
                CoffeeDetailHeader(
                    modifier = modifier,
                    coverRes = coffee.image,
                    onBackClick = onBack
                )
                CoffeeDetailDescription(
                    modifier = modifier,
                    coffee = coffee
                )
            }
        }
    }

    /**
     * Composable function that displays the header of the coffee details view.
     */
    @Composable
    fun CoffeeDetailHeader(
        modifier: Modifier = Modifier,
        coverRes: Int,
        onBackClick: () -> Unit
    ) {
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

    /**
     * Composable function that displays the description of the selected coffee item.
     */
    @Composable
    fun SharedTransitionScope.CoffeeDetailDescription(
        modifier: Modifier = Modifier,
        coffee: CoffeeModel
    ) {
        Column(modifier = modifier.padding(5.dp)) {
            Text(
                text = stringResource(R.string.about),
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 26.sp,
                modifier = modifier.padding(start = 10.dp)
            )
            Spacer(modifier = modifier.height(20.dp))
            Text(
                text = coffee.name,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = modifier.padding(start = 10.dp)
            )
            Text(
                text = coffee.description,
                modifier = modifier
                    .padding(10.dp)
                    .skipToLookaheadSize()
            )
        }
    }
}