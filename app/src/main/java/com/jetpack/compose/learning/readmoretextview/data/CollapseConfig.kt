package com.jetpack.compose.learning.readmoretextview.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Custom configuration class for Collapse action for ReadMoreTextView class.
 * It accepts following parameters used in Collapse action text
 *
 * @param collapseText Text to be shown for collapse action
 * @param collapseTextSize Text size for [collapseText] text
 * @param collapseTextColor Text color for [collapseText] text
 * @param collapseIcon Icon to be shown for collapse action
 * @param collapseFontFamily Font family [collapseText] text
 * @param showUnderline Whether to show an underline below [collapseText] text
 */
data class CollapseConfig(
    val collapseText: String = "Show Less",
    val collapseTextSize: TextUnit = 16.sp,
    val collapseTextColor: Color = Color.Blue,
    val collapseIcon: ImageVector = Icons.Outlined.KeyboardArrowUp,
    val collapseFontFamily: FontFamily? = null,
    val showUnderline: Boolean = true,
)