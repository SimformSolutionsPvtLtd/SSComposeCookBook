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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlin.math.roundToInt

/**
 * Canvas path operations
 * All the operation related to path with different shapes are listed in this example
 */
class CanvasPathOperationActivity : ComponentActivity() {

    private val pathOperationList = listOf(
        PathOperation.Difference.toString() to PathOperation.Difference,
        PathOperation.Intersect.toString() to PathOperation.Intersect,
        PathOperation.ReverseDifference.toString() to PathOperation.ReverseDifference,
        PathOperation.Union.toString() to PathOperation.Union,
        PathOperation.Xor.toString() to PathOperation.Xor
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Path Operation") },
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
                        Title("Path Operation")
                        BasicPathOperation()
                        VerticalSpace()
                    }
                }
            }
        }
    }

    @Composable
    fun BasicPathOperation() {
        var pathOperation by remember { mutableStateOf(PathOperation.Difference) }
        var leftPolygonSides by remember { mutableStateOf(6) }
        var leftPolygonRadius by remember { mutableStateOf(200f) }
        var rightPolygonSide by remember { mutableStateOf(5) }
        var rightPolygonRadius by remember { mutableStateOf(200f) }
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
            val halfCanvasWidth = canvasWidth / 2
            val halfCanvasHeight = canvasHeight / 2
            val leftPath = createAnyPolygonPath(
                halfCanvasWidth / 2 + 150f,
                halfCanvasHeight,
                leftPolygonSides,
                leftPolygonRadius
            )
            val rightPath = createAnyPolygonPath(
                (halfCanvasWidth + canvasWidth) / 2 - 150f,
                halfCanvasHeight,
                rightPolygonSide,
                rightPolygonRadius
            )
            drawPath(
                leftPath,
                Color.Green,
                style = Stroke(15f)
            )
            drawPath(
                rightPath,
                Color.Blue,
                style = Stroke(15f)
            )
            val newPath = Path.combine(pathOperation, leftPath, rightPath)
            drawPath(
                newPath,
                brush = getBrush(selectedColorStyle, selectedTileMode),
                style = if (selectedDrawStyle == DrawStyle.FILL) Fill else stroke,
            )
        }
        VerticalSpace()
        SubTitle("Path Operation")
        VerticalSpace(5)
        CanvasDropDown(pathOperation.toString(), pathOperationList) {
            if (it != pathOperation) {
                pathOperation = it
            }
        }
        VerticalSpace()
        SubTitle("Left Polygon Sides $leftPolygonSides")
        Slider(
            value = leftPolygonSides.toFloat(),
            onValueChange = { leftPolygonSides = it.roundToInt() },
            valueRange = 3f..10f,
            steps = 8
        )
        VerticalSpace(10)
        SubTitle("Left Polygon Radius ${leftPolygonRadius.roundToInt()}")
        Slider(
            value = leftPolygonRadius,
            onValueChange = { leftPolygonRadius = it },
            valueRange = 150f..250f,
        )
        SubTitle("Right Polygon Sides $rightPolygonSide")
        Slider(
            value = rightPolygonSide.toFloat(),
            onValueChange = { rightPolygonSide = it.roundToInt() },
            valueRange = 3f..10f,
            steps = 8
        )
        VerticalSpace(10)
        SubTitle("Right Polygon Radius ${rightPolygonRadius.roundToInt()}")
        Slider(
            value = rightPolygonRadius,
            onValueChange = { rightPolygonRadius = it },
            valueRange = 150f..250f,
        )
        SubTitle("New Path Controls")
        VerticalSpace(10)
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
