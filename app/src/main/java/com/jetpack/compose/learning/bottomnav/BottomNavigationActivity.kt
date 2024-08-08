package com.jetpack.compose.learning.bottomnav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

const val NOTIFICATION_ARGUMENT_KEY = "unreadNotification"
const val PROFILE_ARGUMENT_KEY = "userModel"

class BottomNavigationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainContent()
            }
        }
    }

    @Composable
    fun MainContent() {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Bottom Navigation") },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            },
            bottomBar = { BottomNavBar(navController) }
        ) { contentPadding ->
            Navigation(Modifier.padding(contentPadding),navController)
        }
    }

    @Composable
    fun BottomNavBar(navHostController: NavHostController) {
        val items = listOf(
            ScreenType.Home,
            ScreenType.Search,
            ScreenType.Notifications,
            ScreenType.Profile
        )
        Column {
            BottomNavigation {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen ->
                    BottomNavigationItem(
                        onClick = {
                            // to make sure that we do not on the same screen again and again
                            if (currentRoute?.contains(screen.route) == false) {

                                // removed backstack when navigated
                                navHostController.popBackStack(
                                    navHostController.graph.startDestinationId,
                                    false
                                )

                                when (screen.route) {
                                    // Redirecting to Notifications
                                    ScreenType.Notifications.route -> {
                                        navHostController.navigate("${screen.route}/?$NOTIFICATION_ARGUMENT_KEY=145") {
                                            launchSingleTop = true
                                        }
                                    }
                                    // Redirecting to Profile Screen
                                    ScreenType.Profile.route -> {
                                        val user = Gson().toJson(UserModel("Hanif", 1))
                                        navHostController.navigate(route = "${screen.route}/$user") {
                                            launchSingleTop = true
                                        }
                                    }
                                    else -> {
                                        navHostController.navigate(screen.route) {
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            }
                        },
                        icon = { Icon(screen.icon, "") },
                        label = { Text(text = screen.resourceId) },
                        selected = when (screen.route) {
                            ScreenType.Profile.route -> currentRoute == screen.route.plus("/{$PROFILE_ARGUMENT_KEY}")
                            ScreenType.Notifications.route -> currentRoute == screen.route.plus("/?$NOTIFICATION_ARGUMENT_KEY={$NOTIFICATION_ARGUMENT_KEY}")
                            else -> currentRoute == screen.route
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun Navigation(modifier: Modifier, navController: NavHostController) {
        NavHost(modifier = modifier, navController = navController, startDestination = ScreenType.Home.route) {
            // Home Screen
            composable(ScreenType.Home.route) {
                HomeScreen()
            }

            // Search Screen
            composable(ScreenType.Search.route) {
                SearchScreen()
            }

            // Passing args with default values
            composable(ScreenType.Notifications.route.plus("/?$NOTIFICATION_ARGUMENT_KEY={$NOTIFICATION_ARGUMENT_KEY}"),
                arguments = listOf(navArgument(NOTIFICATION_ARGUMENT_KEY) {
                    type = NavType.IntType
                    defaultValue = 50
                })) { backStackEntry ->
                NotificationScreen(backStackEntry.arguments?.getInt(NOTIFICATION_ARGUMENT_KEY))
            }

            // Passing User Model in string as an argument
            composable(ScreenType.Profile.route.plus("/{$PROFILE_ARGUMENT_KEY}")) { backStackEntry ->
                val user = Gson().fromJson(
                    backStackEntry.arguments?.getString(PROFILE_ARGUMENT_KEY),
                    UserModel::class.java
                )
                ProfileScreen(user)
            }
        }
    }
}