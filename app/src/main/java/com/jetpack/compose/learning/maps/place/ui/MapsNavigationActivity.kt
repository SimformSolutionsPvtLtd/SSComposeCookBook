package com.jetpack.compose.learning.maps.place.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Square
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetpack.compose.learning.maps.MapVerticalSpace
import com.jetpack.compose.learning.maps.place.component.MapPlaceScreens
import com.jetpack.compose.learning.maps.place.component.MapsNavigationViewer
import com.jetpack.compose.learning.maps.place.component.PlacePicker
import com.jetpack.compose.learning.maps.place.viewmodel.NavigationViewModel
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.ComposeCookBookTheme
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Navigation viewer example.
 * Select the start and end locations to see the different routes and navigation between them.
 */
class MapsNavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            systemUiController.setStatusBarColor(
                color = MaterialTheme.colors.background,
                darkIcons = !appTheme.value.darkTheme
            )
            ComposeCookBookTheme(
                darkTheme = appTheme.value.darkTheme,
                colorPalette = appTheme.value.pallet
            ) {
                MainContent()
            }
        }
    }

    @Composable
    fun MainContent(viewModel: NavigationViewModel = viewModel()) {
        val navController = rememberNavController()
        NavHost(navController, startDestination = MapPlaceScreens.Main.route) {
            composable(MapPlaceScreens.Main.route) {
                MainScreen(navController, viewModel)
            }
            composable(MapPlaceScreens.PlacePicker.route) {
                PlacePicker {
                    viewModel.selectPlace(it)
                    navController.popBackStack()
                }
            }
        }
    }

    @Composable
    fun MainScreen(navController: NavController, viewModel: NavigationViewModel) {
        val startLocation by viewModel.startLocationResult.collectAsState()
        val endLocation by viewModel.endLocationResult.collectAsState()
        val showNavigationViewer by viewModel.showNavigationViewer.collectAsState()

        Scaffold(topBar = {
            TopAppBar(
                contentPadding = PaddingValues(0.dp),
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier.height(120.dp)
            ) {
                Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconComponent(Icons.Outlined.Circle, Modifier.size(18.dp))
                        IconComponent(Icons.Filled.MoreVert, Modifier.padding(vertical = 4.dp))
                        IconComponent(Icons.Outlined.Square, Modifier.size(18.dp))
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                    ) {
                        SearchField(startLocation?.address ?: "", "Choose start location") {
                            viewModel.setStartLocationSelection(true)
                            navController.navigate(MapPlaceScreens.PlacePicker.route)
                        }
                        MapVerticalSpace()
                        SearchField(endLocation?.address ?: "", "Choose end location") {
                            viewModel.setStartLocationSelection(false)
                            navController.navigate(MapPlaceScreens.PlacePicker.route)
                        }
                    }
                }
            }
        }) {
            Box(modifier = Modifier.padding(it)) {
                if (showNavigationViewer) {
                    MapsNavigationViewer(viewModel)
                }
            }
        }
    }

    @Composable
    private fun IconComponent(icon: ImageVector, modifier: Modifier = Modifier) {
        Icon(
            icon,
            contentDescription = null,
            modifier = modifier,
            tint = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
    }

    @Composable
    private fun SearchField(text: String, placeHolder: String, onClick: () -> Unit) {
        BasicTextField(
            value = text,
            readOnly = true,
            enabled = false,
            onValueChange = {},
            textStyle = TextStyle(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .border(
                            1.dp,
                            MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
                            RoundedCornerShape(6.dp)
                        )
                        .requiredHeight(40.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        if (text.isEmpty()) {
                            Text(text = placeHolder)
                        }
                        innerTextField()
                    }
                }
            },
            modifier = Modifier.clickable { onClick() },
            maxLines = 1,
            singleLine = true,
        )
    }
}
