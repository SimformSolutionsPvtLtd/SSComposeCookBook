package com.jetpack.compose.learning.bottomnav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.google.gson.Gson
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.SystemUiController
import com.jetpack.compose.learning.theme.BaseView

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
        ) {
            Navigation(navController)
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
        Column() {
            BottomNavigation() {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen ->
                    BottomNavigationItem(
                        onClick = {
                            navHostController.navigate(screen.route) {
                                navHostController.popBackStack(
                                    navHostController.graph.startDestinationId,
                                    false
                                )
                                navHostController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                    if (currentRoute != screen.route) {
                                        when (screen.route) {
                                            // Redirecting to Notifications
                                            ScreenType.Notifications.route -> {
                                                navHostController.navigate("notifications/?unreadNotification=")
                                            }
                                            // Redirecting to Profile Screen
                                            ScreenType.Profile.route -> {
                                                val user = Gson().toJson(UserModel("Hanif", 1))
                                                navHostController.navigate(route = "profile/$user")
                                            }
                                            else -> navHostController.navigate(screen.route)
                                        }
                                    }
                                }
                                //Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        icon = { Icon(screen.icon, "") },
                        label = { Text(text = screen.resourceId) },
                        selected = currentRoute == screen.route
                    )
                }
            }
        }
    }

    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController, startDestination = ScreenType.Home.route) {
            // Home Screen
            composable(ScreenType.Home.route) {
                HomeScreen()
            }

            // Search Screen
            composable(ScreenType.Search.route) {
                SearchScreen()
            }

            // Passing args with default values
            composable(ScreenType.Notifications.route,
            arguments = listOf( navArgument("unreadNotification") {
                type = NavType.IntType
                defaultValue = 50
            } )) { backStackEntry ->
                NotificationScreen(backStackEntry.arguments?.getInt("unreadNotification"))
            }

            // Passing User Model in string as an argument
            composable(ScreenType.Profile.route) { backStackEntry ->
                val user = Gson().fromJson(backStackEntry.arguments?.getString("userModel"), UserModel::class.java)
                ProfileScreen(user)
            }
        }
    }
}