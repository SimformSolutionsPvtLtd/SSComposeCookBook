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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlin.math.roundToInt

/**
 * Canvas draw scope operation examples
 * It contains example related to Insets, Translate, Rotate, Scale, Clip Path and Clip Rect
 */
class CanvasDrawScopeOperationActivity : ComponentActivity() {

    companion object {
        const val MINIMUM_SPACE = 0f
        const val MAXIMUM_SPACE = 150f
    }

    private val clipPathList = listOf(
        ClipOp.Difference.toString() to ClipOp.Difference,
        ClipOp.Intersect.toString() to ClipOp.Intersect
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("DrawScope Helpers") },
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
                        Title("Insets")
                        VerticalSpace()
                        InsetsDemo()
                        VerticalSpace()
                        Title("Translate")
                        VerticalSpace()
                        TranslateDemo()
                        VerticalSpace()
                        Title("Rotate")
                        VerticalSpace()
                        RotateDemo()
                        Title("Scale")
                        VerticalSpace()
                        ScaleDemo()
                        VerticalSpace()
                        Title("Clip Path")
                        VerticalSpace()
                        ClipPathDemo()
                        VerticalSpace()
                        Title("Clip Rect")
                        VerticalSpace()
                        ClipRectDemo()
                        VerticalSpace()
                    }
                }
            }
        }
    }

    @Composable
    fun InsetsDemo() {
        var leftSide by remember { mutableStateOf(0f) }
        var rightSide by remember { mutableStateOf(0f) }
        var topSide by remember { mutableStateOf(0f) }
        var bottomSide by remember { mutableStateOf(0f) }

        Canvas(modifier = canvasModifier) {
            inset(leftSide, topSide, rightSide, bottomSide) {
                drawRect(Color.Red, style = Stroke(15f))
            }
        }
        VerticalSpace()
        SubTitle("Left ${leftSide.roundToInt()}")
        Slider(
            value = leftSide,
            onValueChange = { leftSide = it },
            valueRange = MINIMUM_SPACE..MAXIMUM_SPACE,
        )
        VerticalSpace(10)
        SubTitle("Right ${rightSide.roundToInt()}")
        Slider(
            value = rightSide,
            onValueChange = { rightSide = it },
            valueRange = MINIMUM_SPACE..MAXIMUM_SPACE,
        )
        VerticalSpace(10)
        SubTitle("Top ${topSide.roundToInt()}")
        Slider(
            value = topSide,
            onValueChange = { topSide = it },
            valueRange = MINIMUM_SPACE..MAXIMUM_SPACE,
        )
        SubTitle("Bottom ${bottomSide.roundToInt()}")
        Slider(
            value = bottomSide,
            onValueChange = { bottomSide = it },
            valueRange = MINIMUM_SPACE..MAXIMUM_SPACE,
        )
    }

    @Composable
    fun TranslateDemo() {
        var leftSide by remember { mutableStateOf(0f) }
        var topSide by remember { mutableStateOf(0f) }

        Canvas(modifier = canvasModifier) {
            translate(leftSide, topSide) {
                drawRect(
                    color = Color.Red,
                    style = Stroke(15f),
                    size = Size(200f, 200f)
                )
            }
        }
        VerticalSpace()
        SubTitle("Left ${leftSide.roundToInt()}")
        Slider(
            value = leftSide,
            onValueChange = { leftSide = it },
            valueRange = 0f..350f,
        )
        VerticalSpace(10)
        SubTitle("Top ${topSide.roundToInt()}")
        Slider(
            value = topSide,
            onValueChange = { topSide = it },
            valueRange = 0f..350f,
        )
    }

    @Composable
    fun RotateDemo() {
        var rotation by remember { mutableStateOf(0f) }

        Canvas(modifier = canvasModifier) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val halfCanvasWidth = canvasWidth / 2
            val halfCanvasHeight = canvasHeight / 2
            val path = createAnyPolygonPath(
                halfCanvasWidth,
                halfCanvasHeight,
                3,
                180f
            )
            rotate(rotation) {
                drawPath(
                    path,
                    color = Color.Red, style = Stroke(15f),
                )
                drawRect(
                    color = Color.Red, style = Stroke(15f),
                    size = Size(200f, 200f),
                    topLeft = Offset(0f, halfCanvasHeight - 100f)
                )
            }
        }
        VerticalSpace()
        SubTitle("Rotation ${rotation.roundToInt()}")
        Slider(
            value = rotation,
            onValueChange = { rotation = it },
            valueRange = 0f..360f,
        )
    }

    @Composable
    fun ScaleDemo() {
        var scale by remember { mutableStateOf(1f) }

        Canvas(modifier = canvasModifier) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val halfCanvasWidth = canvasWidth / 2
            val halfCanvasHeight = canvasHeight / 2
            val path = createAnyPolygonPath(
                halfCanvasWidth,
                halfCanvasHeight,
                3,
                80f
            )
            scale(scale) {
                drawPath(
                    path,
                    color = Color.Red, style = Stroke(10f),
                )
            }
        }
        VerticalSpace()
        SubTitle("Scale $scale")
        Slider(
            value = scale,
            onValueChange = { scale = it },
            valueRange = 1f..4f,
        )
    }

    @Composable
    fun ClipPathDemo() {
        var radius by remember { mutableStateOf(150f) }
        var clipPathOperation by remember { mutableStateOf(ClipOp.Intersect) }
        val bitmap = ImageBitmap.imageResource(R.drawable.ic_canvas_1)
        val renderWidth = bitmap.width * 0.5
        val renderHeight = bitmap.height * 0.5

        Canvas(modifier = canvasModifier) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val halfCanvasWidth = canvasWidth / 2
            val halfCanvasHeight = canvasHeight / 2
            val path = createAnyPolygonPath(
                halfCanvasWidth,
                halfCanvasHeight,
                8,
                radius
            )
            clipPath(path, clipPathOperation) {
                drawImage(
                    image = bitmap,
                    dstSize = IntSize(renderWidth.toInt(), renderHeight.toInt())
                )
            }
        }
        VerticalSpace()
        SubTitle("Clip Path Operation")
        VerticalSpace(5)
        CanvasDropDown(clipPathOperation.toString(), clipPathList) {
            if (it != clipPathOperation) {
                clipPathOperation = it
            }
        }
        VerticalSpace(10)
        SubTitle("Radius ${radius.roundToInt()}")
        Slider(
            value = radius,
            onValueChange = { radius = it },
            valueRange = 100f..250f,
        )
    }

    @Composable
    fun ClipRectDemo() {
        val bitmap = ImageBitmap.imageResource(R.drawable.ic_canvas_1)
        val renderWidth = bitmap.width * 0.5
        val renderHeight = bitmap.height * 0.5
        var leftSide by remember { mutableStateOf(0f) }
        var rightSide by remember { mutableStateOf((renderWidth / 2).toFloat()) }
        var topSide by remember { mutableStateOf(0f) }
        var bottomSide by remember { mutableStateOf((renderHeight / 2).toFloat()) }
        var clipPathOperation by remember { mutableStateOf(ClipOp.Intersect) }

        Canvas(modifier = canvasModifier) {
            clipRect(leftSide, topSide, rightSide, bottomSide, clipPathOperation) {
                drawImage(
                    image = bitmap,
                    dstSize = IntSize(renderWidth.toInt(), renderHeight.toInt())
                )
            }
        }
        VerticalSpace()
        SubTitle("Clip Path Operation")
        VerticalSpace(5)
        CanvasDropDown(clipPathOperation.toString(), clipPathList) {
            if (it != clipPathOperation) {
                clipPathOperation = it
            }
        }
        VerticalSpace(10)
        SubTitle("Left ${leftSide.roundToInt()}")
        Slider(
            value = leftSide,
            onValueChange = { leftSide = it },
            valueRange = MINIMUM_SPACE..(renderWidth.toFloat() / 2),
        )
        VerticalSpace(10)
        SubTitle("Right ${rightSide.roundToInt()}")
        Slider(
            value = rightSide,
            onValueChange = { rightSide = it },
            valueRange = (renderWidth.toFloat() / 2)..renderWidth.toFloat(),
        )
        VerticalSpace(10)
        SubTitle("Top ${topSide.roundToInt()}")
        Slider(
            value = topSide,
            onValueChange = { topSide = it },
            valueRange = MINIMUM_SPACE..(renderHeight.toFloat() / 2),
        )
        SubTitle("Bottom ${bottomSide.roundToInt()}")
        Slider(
            value = bottomSide,
            onValueChange = { bottomSide = it },
            valueRange = (renderHeight.toFloat() / 2)..renderHeight.toFloat(),
        )
    }
}
