package com.jetpack.compose.learning.canvas

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke

data class DrawProperties(
    var selectedDrawStyle: DrawStyle = DrawStyle.STROKE,
    var selectedColorStyle: ColorStyle = ColorStyle.SOLID,
    var selectedTileMode: TileMode = TileMode.Clamp,
    var strokeWidth: Float = 10f,
    var strokePathEffect: PathStyle = PathStyle.NONE,
    var pathEffectStyle: PathEffectStyle = PathEffectStyle.TRANSLATE,
    var strokeCapMode: StrokeCap = StrokeCap.Butt,
    var strokeJoinMode: StrokeJoin = StrokeJoin.Miter,
    var pathCornerRadius: Float = 10f,
    var color: Color = Color(0xFFFF6347)
) {
    fun getStroke(): Stroke {
        val stampedPathEffectStyle = when (pathEffectStyle) {
            PathEffectStyle.TRANSLATE -> StampedPathEffectStyle.Translate
            PathEffectStyle.MORPH -> StampedPathEffectStyle.Morph
            PathEffectStyle.ROTATE -> StampedPathEffectStyle.Rotate
        }
        val pathEffect = when (strokePathEffect) {
            PathStyle.NONE -> null
            PathStyle.DASH -> PathEffect.dashPathEffect(floatArrayOf(50f, 30f))
            PathStyle.CUSTOM_SHAPE -> PathEffect.stampedPathEffect(
                diamondPath, 60f, 0f,
                stampedPathEffectStyle
            )
            PathStyle.ROUND_CORNER -> PathEffect.cornerPathEffect(pathCornerRadius)
        }
        return Stroke(
            strokeWidth,
            pathEffect = pathEffect,
            cap = strokeCapMode,
            join = strokeJoinMode,
        )
    }

    fun getBrush(): Brush {
        return getBrush(selectedColorStyle, selectedTileMode, color)
    }
}