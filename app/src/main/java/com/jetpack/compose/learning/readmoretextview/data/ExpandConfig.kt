package com.jetpack.compose.learning.readmoretextview.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
/**
 * Custom configuration class for Collapse action for ReadMoreTextView class.
 * It accepts following parameters used in Collapse action text
 *
 * @param expandText Text to be shown for expand action
 * @param expandTextSize Text size for [expandText] text
 * @param expandTextColor Text color for [expandText] text
 * @param expandIcon Icon to be shown for expand action
 * @param expandFontFamily Font family [expandText] text
 * @param showUnderline Whether to show an underline below [expandText] text
 */
data class ExpandConfig(
    val expandText: String = "Show More",
    val expandTextSize: TextUnit = 16.sp,
    val expandTextColor: Color = Color.Blue,
    val expandIcon: ImageVector = Icons.Outlined.KeyboardArrowDown,
    val expandFontFamily: FontFamily? = null,
    val showUnderline: Boolean = true,
)