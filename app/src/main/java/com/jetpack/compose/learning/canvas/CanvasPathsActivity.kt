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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlin.math.roundToInt

/**
 * Canvas path example
 * The examples includes all path properties drawn any shape.
 */
class CanvasPathsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Path") },
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
                        Title("Basic")
                        BasicPath()
                        VerticalSpace()
                        Title("With Shapes")
                        BasicPathShapes()
                        VerticalSpace()
                        Title("Any Polygon")
                        AnyPolygon()
                        VerticalSpace()
                        Title("Any Polygon (with progress)")
                        AnyPolygonProgress()
                        VerticalSpace()
                    }
                }
            }
        }
    }

    @Composable
    fun BasicPath() {
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
        val stroke = Stroke(
            strokeWidth,
            pathEffect = pathEffect,
            cap = strokeCapMode,
            join = strokeJoinMode,
        )

        Canvas(modifier = canvasModifier) {
            inset(horizontal = 150f, 50f) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val halfCanvasWidth = canvasWidth / 2
                val halfCanvasHeight = canvasHeight / 2
                val path = Path().apply {
                    moveTo(halfCanvasWidth, canvasHeight)
                    lineTo(0f, halfCanvasHeight)
                    lineTo(halfCanvasWidth, 0f)
                    lineTo(canvasWidth, halfCanvasHeight)
                    lineTo(halfCanvasWidth, canvasHeight)
                }
                drawPath(
                    path,
                    brush = getBrush(selectedColorStyle, selectedTileMode),
                    style = if (selectedDrawStyle == DrawStyle.FILL) Fill else stroke,
                )
            }
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
    fun BasicPathShapes() {
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
            inset(horizontal = 50f, 50f) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val halfCanvasWidth = canvasWidth / 2
                val halfCanvasHeight = canvasHeight / 2
                val path = Path().apply {
                    addRoundRect(
                        RoundRect(
                            Rect(
                                Offset(0f, 0f),
                                Size(halfCanvasHeight, halfCanvasHeight)
                            )
                        )
                    )
                    addOval(Rect(Offset(halfCanvasHeight + 50f, 0f), Size(150f, 150f)))
                    moveTo(halfCanvasWidth + 100f, 0f)
                    addArc(Rect(Offset(halfCanvasHeight + 250, 0f), Size(150f, 150f)), 130f, 120f)
                }
                drawPath(
                    path,
                    brush = getBrush(selectedColorStyle, selectedTileMode),
                    style = if (selectedDrawStyle == DrawStyle.FILL) Fill else stroke,
                )
            }
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
    fun AnyPolygon() {
        var sides by remember { mutableStateOf(3) }
        var radius by remember { mutableStateOf(150f) }
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
            inset(horizontal = 50f, 50f) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val halfCanvasWidth = canvasWidth / 2
                val halfCanvasHeight = canvasHeight / 2
                val path = createAnyPolygonPath(halfCanvasWidth, halfCanvasHeight, sides, radius)
                drawPath(
                    path,
                    brush = getBrush(selectedColorStyle, selectedTileMode),
                    style = if (selectedDrawStyle == DrawStyle.FILL) Fill else stroke,
                )
            }
        }
        SubTitle("Sides $sides")
        Slider(
            value = sides.toFloat(),
            onValueChange = { sides = it.roundToInt() },
            valueRange = 3f..10f,
            steps = 8
        )
        VerticalSpace(10)
        SubTitle("Radius ${radius.roundToInt()}")
        Slider(
            value = radius,
            onValueChange = { radius = it },
            valueRange = 150f..300f,
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
            showStrokeJoinControl = true
        )
    }

    @Composable
    fun AnyPolygonProgress() {
        var sides by remember { mutableStateOf(3) }
        var radius by remember { mutableStateOf(150f) }
        var progress by remember { mutableStateOf(50f) }
        val pathMeasure by remember { mutableStateOf(PathMeasure()) }
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
        val stroke = Stroke(
            strokeWidth,
            pathEffect = pathEffect,
            cap = strokeCapMode,
            join = strokeJoinMode,
        )

        Canvas(modifier = canvasModifier) {
            inset(horizontal = 50f, 50f) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val halfCanvasWidth = canvasWidth / 2
                val halfCanvasHeight = canvasHeight / 2
                val path = createAnyPolygonPath(halfCanvasWidth, halfCanvasHeight, sides, radius)
                val tempPath = Path()
                if (progress == 100f) {
                    tempPath.addPath(path)
                } else {
                    pathMeasure.setPath(path, forceClosed = false)
                    pathMeasure.getSegment(
                        startDistance = 0f,
                        stopDistance = pathMeasure.length * progress / 100f,
                        tempPath
                    )
                }
                drawPath(
                    tempPath,
                    brush = getBrush(selectedColorStyle, selectedTileMode),
                    style = if (selectedDrawStyle == DrawStyle.FILL) Fill else stroke,
                )
            }
        }
        SubTitle("Sides $sides")
        Slider(
            value = sides.toFloat(),
            onValueChange = { sides = it.roundToInt() },
            valueRange = 3f..10f,
            steps = 8
        )
        VerticalSpace(10)
        Text(text = "Progress ${progress.roundToInt()}%")
        Slider(
            value = progress,
            onValueChange = { progress = it },
            valueRange = 0f..100f,
        )
        VerticalSpace(10)
        SubTitle("Radius ${radius.roundToInt()}")
        Slider(
            value = radius,
            onValueChange = { radius = it },
            valueRange = 150f..300f,
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
            showStrokeJoinControl = true
        )
    }
}
