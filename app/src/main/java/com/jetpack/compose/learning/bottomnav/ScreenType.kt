package com.jetpack.compose.learning.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenType(val icon: ImageVector, val route: String, val resourceId: String) {
    object Home: ScreenType(Icons.Filled.Home,"home", "Home")
    object Search: ScreenType(Icons.Filled.Search,"search", "Search")
    object Notifications : ScreenType(Icons.Filled.Notifications,"notifications/?unreadNotification={unreadNotification}", "Notifications")
    object Profile: ScreenType(Icons.Filled.Person,"profile/{userModel}", "Profile")
}