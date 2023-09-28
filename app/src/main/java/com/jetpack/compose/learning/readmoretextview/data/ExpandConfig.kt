package com.jetpack.compose.learning.readmoretextview.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class ExpandConfig(
    val expandText: String = "Show More",
    val expandTextSize: TextUnit = 16.sp,
    val expandTextColor: Color = Color.Blue,
    val showUnderline: Boolean = true,
    val expandIcon: ImageVector = Icons.Outlined.KeyboardArrowDown,
)