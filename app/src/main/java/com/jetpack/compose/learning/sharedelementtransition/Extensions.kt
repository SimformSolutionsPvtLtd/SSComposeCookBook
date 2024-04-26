package com.jetpack.compose.learning.sharedelementtransition

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

/**
 * Converts an integer value representing pixels to density-independent pixels (Dp) using the provided density value.
 * @param [density] Density value representing the screen density.
 * @return The equivalent value in density-independent pixels (Dp).
 */
fun Int.toDp(density: Density): Dp = with(density) { this@toDp.toDp() }

/**
 * Converts a floating-point value representing pixels to density-independent pixels (Dp) using the provided density value.
 * @param [density] Density value representing the screen density.
 * @return The equivalent value in density-independent pixels (Dp).
 */
fun Float.toDp(density: Density): Dp = with(density) { this@toDp.toDp() }

/**
 * Converts a value in density-independent pixels (Dp) to pixels using the provided density value.
 * @param [density] Density value representing the screen density.
 * @return The equivalent value in pixels.
 */
fun Dp.toPx(density: Density): Int = with(density) { this@toPx.roundToPx() }

/**
 * Linearly interpolates between two colors.
 * @param [start] The starting color.
 * @param [stop] The ending color.
 * @param [fraction] The fraction representing the interpolation progress (should be between 0 and 1).
 * @return The interpolated color.
 */
fun lerp(start: Color, stop: Color, fraction: Float): Color =
    androidx.compose.ui.graphics.lerp(start, stop, fraction)

/**
 * Linearly interpolates between two density-independent pixel (Dp) values.
 * @param [start] The starting Dp value.
 * @param [stop] The ending Dp value.
 * @param [fraction] The fraction representing the interpolation progress (should be between 0 and 1).
 * @return The interpolated Dp value.
 */
fun lerp(start: Dp, stop: Dp, fraction: Float): Dp =
    androidx.compose.ui.unit.lerp(start, stop, fraction)

/**
 * Linearly interpolates between two Offset values.
 * @param [start] The starting Offset.
 * @param [stop] The ending Offset.
 * @param [fraction] The fraction representing the interpolation progress (should be between 0 and 1).
 * @return The interpolated Offset value.
 */
fun lerp(start: Offset, stop: Offset, fraction: Float): Offset =
    androidx.compose.ui.geometry.lerp(start, stop, fraction)
