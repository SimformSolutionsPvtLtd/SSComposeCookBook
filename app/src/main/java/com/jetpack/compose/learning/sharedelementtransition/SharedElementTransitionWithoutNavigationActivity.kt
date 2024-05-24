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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.jetpack.compose.learning.sharedelementtransition.model.CoffeeModel
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class SharedElementTransitionWithoutNavigationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                SharedElementTransitionWithoutTNavigation()
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedElementTransitionWithoutTNavigation() {
        var selectedCoffee by remember { mutableStateOf<CoffeeModel?>(null) }
        var showDetails by remember { mutableStateOf(false) }
        val coffeeList = DataProvider.getCoffeDetails()

        SharedTransitionLayout {
            AnimatedContent(targetState = showDetails, label = "transition") { targetState ->
                if (!targetState) {
                    MainContent(coffeeList = coffeeList, animatedVisibilityScope = this) {
                        selectedCoffee = it
                        showDetails = true
                    }
                } else {
                    DetailsContent(coffee = selectedCoffee, animatedVisibilityScope = this) {
                        showDetails = false
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.MainContent(
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                        .clickable {
                            onShowDetails(coffee)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = coffee.image),
                        contentDescription = null,
                        modifier = Modifier
                            .sharedBounds(
                                rememberSharedContentState(key = coffee.id),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = coffee.name,
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun SharedTransitionScope.DetailsContent(
        coffee: CoffeeModel?,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onBack: () -> Unit,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onBack)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = coffee?.image ?: 0),
                    contentDescription = coffee?.description ?: "",
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(key = coffee?.id ?: 0),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .size(200.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = coffee?.name ?: "",
                    style = MaterialTheme.typography.h4
                )

                Text(text = coffee?.description ?: "")
            }
        }
    }
}