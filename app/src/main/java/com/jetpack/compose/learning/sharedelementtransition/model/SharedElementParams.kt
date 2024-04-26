package com.jetpack.compose.learning.sharedelementtransition.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp

data class SharedElementParams(
    val initialOffset: Offset,
    val targetOffset: Offset,
    val initialSize: Dp,
    val targetSize: Dp,
    val initialCornerRadius: Dp,
    val targetCornerRadius: Dp,
)
