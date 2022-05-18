package com.jetpack.compose.learning.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlin.math.roundToInt

/**
 * Basic canvas shapes like Line, Circle, Arc, Rectangle and Outline
 * All the properties of shapes are included with full customizations
 */
class CanvasShapesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Shapes") },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                }) {
                    val scrollState = rememberScrollState()
                    Column(
                        Modifier
                            .verticalScroll(scrollState)
                            .padding(horizontal = 16.dp, vertical = it.calculateBottomPadding())
                            .fillMaxWidth()
                    ) {
                        VerticalSpace()
                        Title("Line")
                        VerticalSpace()
                        LineExample()
                        VerticalSpace()
                        Title("Circle")
                        CircleShape()
                        VerticalSpace()
                        Title("Arc")
                        ArcShape()
                        VerticalSpace()
                        Title("Rectangle")
                        RectangleShape()
                        VerticalSpace()
                        Title("Outline")
                        OutlineShape()
                        VerticalSpace()
                    }
                }
            }
        }
    }

    @Composable
    fun LineExample() {
        var selectedDrawStyle by remember { mutableStateOf(DrawStyle.STROKE) }
        var selectedColorStyle by remember { mutableStateOf(ColorStyle.SOLID) }
        var selectedTileMode by remember { mutableStateOf(TileMode.Clamp) }
        var strokeWidth by remember { mutableStateOf(10f) }
        var strokePathEffect by remember { mutableStateOf(PathStyle.NONE) }
        var pathEffectStyle by remember { mutableStateOf(PathEffectStyle.TRANSLATE) }
        var strokeCapMode by remember { mutableStateOf(StrokeCap.Butt) }
        var strokeJoinMode by remember { mutableStateOf(StrokeJoin.Miter) }
        var pathCornerRadius by remember { mutableStateOf(10f) }
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

        Canvas(modifier = canvasModifier) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val halfCanvasWidth = canvasWidth / 2
            val halfCanvasHeight = canvasHeight / 2
            drawLine(
                getBrush(selectedColorStyle, selectedTileMode),
                Offset(0f, 0f),
                Offset(canvasWidth, canvasHeight),
                strokeWidth,
                strokeCapMode,
                pathEffect
            )
            drawLine(
                getBrush(selectedColorStyle, selectedTileMode),
                Offset(canvasWidth, 0f),
                Offset(0f, canvasHeight),
                strokeWidth,
                strokeCapMode,
                pathEffect
            )
            drawLine(
                getBrush(selectedColorStyle, selectedTileMode),
                Offset(0f, halfCanvasHeight),
                Offset(canvasWidth, halfCanvasHeight),
                strokeWidth,
                strokeCapMode,
                pathEffect
            )
            drawLine(
                getBrush(selectedColorStyle, selectedTileMode),
                Offset(halfCanvasWidth, 0f),
                Offset(halfCanvasWidth, canvasHeight),
                strokeWidth,
                strokeCapMode,
                pathEffect
            )
        }
        CommonControls(
            selectedDrawStyle = selectedDrawStyle,
            onDrawStyleChange = {
                if (it != selectedDrawStyle) {
                    selectedDrawStyle = it
                }
            },
            selectedColorStyle = selectedColorStyle,
            onColorStyleChange = {
                if (it != selectedColorStyle) {
                    selectedColorStyle = it
                }
            },
            selectedTileMode = selectedTileMode,
            onTileModeChange = {
                if (it != selectedTileMode) {
                    selectedTileMode = it
                }
            },
            strokeWidth = strokeWidth,
            onStrokeWidthChange = {
                strokeWidth = it
            },
            selectedPathEffect = strokePathEffect,
            onPathEffectChange = {
                if (it != strokePathEffect) {
                    strokePathEffect = it
                }
            },
            selectedPathEffectStyle = pathEffectStyle,
            onPathEffectStyleChange = {
                if (it != pathEffectStyle) {
                    pathEffectStyle = it
                }
            },
            selectedStrokeCap = strokeCapMode,
            onStrokeCapChange = {
                if (it != strokeCapMode) {
                    strokeCapMode = it
                }
            },
            selectedStrokeJoin = strokeJoinMode,
            onStrokeJoinChange = {
                if (it != strokeJoinMode) {
                    strokeJoinMode = it
                }
            },
            pathCornerRadius = pathCornerRadius,
            onPathCornerRadiusChange = {
                pathCornerRadius = it
            },
            showTileModeControl = true,
            showDrawStyleControl = false
        )
    }

    @Composable
    fun CircleShape() {
        var circleRadius by remember { mutableStateOf(200f) }
        var selectedDrawStyle by remember { mutableStateOf(DrawStyle.FILL) }
        var selectedColorStyle by remember { mutableStateOf(ColorStyle.SOLID) }
        var selectedTileMode by remember { mutableStateOf(TileMode.Clamp) }
        var strokeWidth by remember { mutableStateOf(10f) }
        var strokePathEffect by remember { mutableStateOf(PathStyle.NONE) }
        var pathEffectStyle by remember { mutableStateOf(PathEffectStyle.TRANSLATE) }
        var strokeCapMode by remember { mutableStateOf(StrokeCap.Butt) }
        var strokeJoinMode by remember { mutableStateOf(StrokeJoin.Miter) }
        var pathCornerRadius by remember { mutableStateOf(10f) }
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
        val stroke = Stroke(
            strokeWidth,
            pathEffect = pathEffect,
            cap = strokeCapMode,
            join = strokeJoinMode,
        )

        Canvas(modifier = canvasModifier) {
            drawCircle(
                brush = getBrush(selectedColorStyle, selectedTileMode),
                radius = circleRadius,
                style = if (selectedDrawStyle == DrawStyle.FILL) Fill else stroke
            )
        }
        SubTitle("Radius ${circleRadius.roundToInt()}")
        Slider(
            value = circleRadius,
            onValueChange = { circleRadius = it },
            valueRange = 50f..200f,
        )
        CommonControls(
            selectedDrawStyle = selectedDrawStyle,
            onDrawStyleChange = {
                if (it != selectedDrawStyle) {
                    selectedDrawStyle = it
                }
            },
            selectedColorStyle = selectedColorStyle,
            onColorStyleChange = {
                if (it != selectedColorStyle) {
                    selectedColorStyle = it
                }
            },
            selectedTileMode = selectedTileMode,
            onTileModeChange = {
                if (it != selectedTileMode) {
                    selectedTileMode = it
                }
            },
            strokeWidth = strokeWidth,
            onStrokeWidthChange = {
                strokeWidth = it
            },
            selectedPathEffect = strokePathEffect,
            onPathEffectChange = {
                if (it != strokePathEffect) {
                    strokePathEffect = it
                }
            },
            selectedPathEffectStyle = pathEffectStyle,
            onPathEffectStyleChange = {
                if (it != pathEffectStyle) {
                    pathEffectStyle = it
                }
            },
            selectedStrokeCap = strokeCapMode,
            onStrokeCapChange = {
                if (it != strokeCapMode) {
                    strokeCapMode = it
                }
            },
            selectedStrokeJoin = strokeJoinMode,
            onStrokeJoinChange = {
                if (it != strokeJoinMode) {
                    strokeJoinMode = it
                }
            },
            pathCornerRadius = pathCornerRadius,
            onPathCornerRadiusChange = {
                pathCornerRadius = it
            }
        )
    }

    @Composable
    private fun ArcShape() {
        var startAngle by remember { mutableStateOf(0f) }
        var sweepAngle by remember { mutableStateOf(60f) }
        var useCenter by remember { mutableStateOf(true) }
        var selectedDrawStyle by remember { mutableStateOf(DrawStyle.FILL) }
        var selectedColorStyle by remember { mutableStateOf(ColorStyle.SOLID) }
        var selectedTileMode by remember { mutableStateOf(TileMode.Clamp) }
        var strokeWidth by remember { mutableStateOf(10f) }
        var strokePathEffect by remember { mutableStateOf(PathStyle.NONE) }
        var pathEffectStyle by remember { mutableStateOf(PathEffectStyle.TRANSLATE) }
        var strokeCapMode by remember { mutableStateOf(StrokeCap.Butt) }
        var strokeJoinMode by remember { mutableStateOf(StrokeJoin.Miter) }
        var pathCornerRadius by remember { mutableStateOf(10f) }
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
        val stroke = Stroke(
            strokeWidth,
            pathEffect = pathEffect,
            cap = strokeCapMode,
            join = strokeJoinMode,
        )

        Canvas(modifier = canvasModifier) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawArc(
                brush = getBrush(selectedColorStyle, selectedTileMode),
                startAngle,
                sweepAngle,
                useCenter,
                topLeft = Offset((canvasWidth - canvasHeight) / 2, 0f),
                size = Size(canvasHeight, canvasHeight),
                style = if (selectedDrawStyle == DrawStyle.FILL) Fill else stroke
            )
        }

        SubTitle(text = "StartAngle ${startAngle.roundToInt()}")
        Slider(
            value = startAngle,
            onValueChange = { startAngle = it },
            valueRange = 0f..360f,
        )
        SubTitle(text = "SweepAngle ${sweepAngle.roundToInt()}")
        Slider(
            value = sweepAngle,
            onValueChange = { sweepAngle = it },
            valueRange = 0f..360f,
        )
        CanvasCheckBox("Use Center", useCenter) {
            useCenter = it
        }
        CommonControls(
            selectedDrawStyle = selectedDrawStyle,
            onDrawStyleChange = {
                if (it != selectedDrawStyle) {
                    selectedDrawStyle = it
                }
            },
            selectedColorStyle = selectedColorStyle,
            onColorStyleChange = {
                if (it != selectedColorStyle) {
                    selectedColorStyle = it
                }
            },
            selectedTileMode = selectedTileMode,
            onTileModeChange = {
                if (it != selectedTileMode) {
                    selectedTileMode = it
                }
            },
            strokeWidth = strokeWidth,
            onStrokeWidthChange = {
                strokeWidth = it
            },
            selectedPathEffect = strokePathEffect,
            onPathEffectChange = {
                if (it != strokePathEffect) {
                    strokePathEffect = it
                }
            },
            selectedPathEffectStyle = pathEffectStyle,
            onPathEffectStyleChange = {
                if (it != pathEffectStyle) {
                    pathEffectStyle = it
                }
            },
            selectedStrokeCap = strokeCapMode,
            onStrokeCapChange = {
                if (it != strokeCapMode) {
                    strokeCapMode = it
                }
            },
            selectedStrokeJoin = strokeJoinMode,
            onStrokeJoinChange = {
                if (it != strokeJoinMode) {
                    strokeJoinMode = it
                }
            },
            pathCornerRadius = pathCornerRadius,
            onPathCornerRadiusChange = {
                pathCornerRadius = it
            },
            showStrokeJoinControl = true
        )
    }

    @Composable
    private fun RectangleShape() {
        var cornerRadius by remember { mutableStateOf(0f) }
        var selectedDrawStyle by remember { mutableStateOf(DrawStyle.FILL) }
        var selectedColorStyle by remember { mutableStateOf(ColorStyle.SOLID) }
        var selectedTileMode by remember { mutableStateOf(TileMode.Clamp) }
        var strokeWidth by remember { mutableStateOf(10f) }
        var strokePathEffect by remember { mutableStateOf(PathStyle.NONE) }
        var pathEffectStyle by remember { mutableStateOf(PathEffectStyle.TRANSLATE) }
        var strokeCapMode by remember { mutableStateOf(StrokeCap.Butt) }
        var strokeJoinMode by remember { mutableStateOf(StrokeJoin.Miter) }
        var pathCornerRadius by remember { mutableStateOf(10f) }
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
        val stroke = Stroke(
            strokeWidth,
            pathEffect = pathEffect,
            cap = strokeCapMode,
            join = strokeJoinMode
        )

        Canvas(modifier = canvasModifier) {
            val canvasWidth = size.width
            drawRoundRect(
                brush = getBrush(selectedColorStyle, selectedTileMode),
                topLeft = Offset((canvasWidth / 2 - 90.dp.toPx()), 10f),
                size = Size(180.dp.toPx(), 180.dp.toPx()),
                style = if (selectedDrawStyle == DrawStyle.FILL) Fill else stroke,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        }

        SubTitle(text = "Corner Radius ${cornerRadius.roundToInt()}")
        Slider(
            value = cornerRadius,
            onValueChange = { cornerRadius = it },
            valueRange = 0f..40f,
        )
        CommonControls(
            selectedDrawStyle = selectedDrawStyle,
            onDrawStyleChange = {
                if (it != selectedDrawStyle) {
                    selectedDrawStyle = it
                }
            },
            selectedColorStyle = selectedColorStyle,
            onColorStyleChange = {
                if (it != selectedColorStyle) {
                    selectedColorStyle = it
                }
            },
            selectedTileMode = selectedTileMode,
            onTileModeChange = {
                if (it != selectedTileMode) {
                    selectedTileMode = it
                }
            },
            strokeWidth = strokeWidth,
            onStrokeWidthChange = {
                strokeWidth = it
            },
            selectedPathEffect = strokePathEffect,
            onPathEffectChange = {
                if (it != strokePathEffect) {
                    strokePathEffect = it
                }
            },
            selectedPathEffectStyle = pathEffectStyle,
            onPathEffectStyleChange = {
                if (it != pathEffectStyle) {
                    pathEffectStyle = it
                }
            },
            selectedStrokeCap = strokeCapMode,
            onStrokeCapChange = {
                if (it != strokeCapMode) {
                    strokeCapMode = it
                }
            },
            selectedStrokeJoin = strokeJoinMode,
            onStrokeJoinChange = {
                if (it != strokeJoinMode) {
                    strokeJoinMode = it
                }
            },
            pathCornerRadius = pathCornerRadius,
            onPathCornerRadiusChange = {
                pathCornerRadius = it
            },
            showStrokeJoinControl = true,
            showTileModeControl = true,
        )
    }

    @Composable
    private fun OutlineShape() {
        var cornerRadius by remember { mutableStateOf(25f) }
        var shapeSize by remember { mutableStateOf(100f) }
        var selectedDrawStyle by remember { mutableStateOf(DrawStyle.FILL) }
        var selectedColorStyle by remember { mutableStateOf(ColorStyle.SOLID) }
        var selectedTileMode by remember { mutableStateOf(TileMode.Clamp) }
        var strokeWidth by remember { mutableStateOf(10f) }
        var strokePathEffect by remember { mutableStateOf(PathStyle.NONE) }
        var pathEffectStyle by remember { mutableStateOf(PathEffectStyle.TRANSLATE) }
        var strokeCapMode by remember { mutableStateOf(StrokeCap.Butt) }
        var strokeJoinMode by remember { mutableStateOf(StrokeJoin.Miter) }
        var pathCornerRadius by remember { mutableStateOf(10f) }
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
        val stroke = Stroke(
            strokeWidth,
            pathEffect = pathEffect,
            cap = strokeCapMode,
            join = strokeJoinMode
        )

        Canvas(modifier = canvasModifier) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val canvasHalfWidth = canvasWidth / 2
            val rectShape = Outline.Rectangle(
                Rect(
                    Offset(
                        (canvasHalfWidth / 2),
                        canvasHeight / 2f
                    ), shapeSize
                )
            )
            val roundRect = Outline.Rounded(
                RoundRect(
                    Rect(
                        Offset(
                            (canvasWidth + canvasHalfWidth) / 2,
                            canvasHeight / 2f
                        ), shapeSize
                    ),
                    CornerRadius(cornerRadius, cornerRadius)
                )
            )
            drawOutline(
                rectShape,
                brush = getBrush(selectedColorStyle, selectedTileMode),
                style = if (selectedDrawStyle == DrawStyle.FILL) Fill else stroke,
            )
            drawOutline(
                roundRect,
                brush = getBrush(selectedColorStyle, selectedTileMode),
                style = if (selectedDrawStyle == DrawStyle.FILL) Fill else stroke,
            )
        }
        SubTitle(text = "Size ${shapeSize.roundToInt()}")
        Slider(
            value = shapeSize,
            onValueChange = { shapeSize = it },
            valueRange = 100f..200f,
        )
        SubTitle(text = "Corner Radius ${cornerRadius.roundToInt()}")
        Slider(
            value = cornerRadius,
            onValueChange = { cornerRadius = it },
            valueRange = 10f..50f,
        )
        CommonControls(
            selectedDrawStyle = selectedDrawStyle,
            onDrawStyleChange = {
                if (it != selectedDrawStyle) {
                    selectedDrawStyle = it
                }
            },
            selectedColorStyle = selectedColorStyle,
            onColorStyleChange = {
                if (it != selectedColorStyle) {
                    selectedColorStyle = it
                }
            },
            selectedTileMode = selectedTileMode,
            onTileModeChange = {
                if (it != selectedTileMode) {
                    selectedTileMode = it
                }
            },
            strokeWidth = strokeWidth,
            onStrokeWidthChange = {
                strokeWidth = it
            },
            selectedPathEffect = strokePathEffect,
            onPathEffectChange = {
                if (it != strokePathEffect) {
                    strokePathEffect = it
                }
            },
            selectedPathEffectStyle = pathEffectStyle,
            onPathEffectStyleChange = {
                if (it != pathEffectStyle) {
                    pathEffectStyle = it
                }
            },
            selectedStrokeCap = strokeCapMode,
            onStrokeCapChange = {
                if (it != strokeCapMode) {
                    strokeCapMode = it
                }
            },
            selectedStrokeJoin = strokeJoinMode,
            onStrokeJoinChange = {
                if (it != strokeJoinMode) {
                    strokeJoinMode = it
                }
            },
            pathCornerRadius = pathCornerRadius,
            onPathCornerRadiusChange = {
                pathCornerRadius = it
            }
        )
    }
}
