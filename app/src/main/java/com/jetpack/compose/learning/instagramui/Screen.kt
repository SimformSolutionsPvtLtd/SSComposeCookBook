package com.jetpack.compose.learning.instagramui

sealed class Screen(val route : String) {
    object SplashScreen : Screen(route = "splash_screen")
    object LoginScreen : Screen(route = "login_screen")
}
