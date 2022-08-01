package com.jetpack.compose.learning.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlin.math.roundToInt

/**
 * Examples related to canvas text and image
 * Text is drawn via native canvas.
 * Examples includes many properties and customization related to text and images
 */
class CanvasTextImageActivity : ComponentActivity() {

    private val filterQualityList = listOf(
        FilterQuality.None.toString() to FilterQuality.None,
        FilterQuality.Low.toString() to FilterQuality.Low,
        FilterQuality.Medium.toString() to FilterQuality.Medium,
        FilterQuality.High.toString() to FilterQuality.High
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Text & Image") },
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
                        Title("Text")
                        TextExample()
                        VerticalSpace()
                        Title("Image Sample 1")
                        ImageExample()
                        VerticalSpace()
                        Title("Image Sample 2")
                        Image2Example()
                        VerticalSpace()
                    }
                }
            }
        }
    }

    @Composable
    fun TextExample() {
        var textSizeSp by remember { mutableStateOf(100f) }
        var selectedDrawStyle by remember { mutableStateOf(DrawStyle.FILL) }
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
        val paint = Paint().apply {
            this.strokeWidth = strokeWidth
            this.pathEffect = pathEffect
            this.strokeCap = strokeCapMode
            this.strokeJoin = strokeJoinMode
            this.style =
                if (selectedDrawStyle == DrawStyle.FILL) PaintingStyle.Fill else PaintingStyle.Stroke
        }.asFrameworkPaint()
        paint.apply {
            this.textSize = textSizeSp
        }
        val measureText = paint.measureText("Android")

        Canvas(modifier = canvasModifier) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            //To draw text on canvas you need to use the native canvas.
            //With native canvas you can also use all the android.graphics.Canvas methods
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    "Android",
                    (canvasWidth / 2) - measureText / 2,
                    canvasHeight / 2,
                    paint
                )
            }
        }
        SubTitle("Text Size ${textSizeSp.roundToInt()} Px")
        Slider(
            value = textSizeSp,
            onValueChange = { textSizeSp = it },
            valueRange = 50f..150f,
        )
        CommonControls(
            selectedDrawStyle = selectedDrawStyle,
            onDrawStyleChange = {
                if (it != selectedDrawStyle) {
                    selectedDrawStyle = it
                    strokePathEffect = PathStyle.NONE
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
            showColorStyleControl = false,
            pathCornerRadius = pathCornerRadius,
            onPathCornerRadiusChange = {
                pathCornerRadius = it
            }
        )
    }

    @Composable
    fun ImageExample() {
        val bitmap = ImageBitmap.imageResource(id = R.drawable.dp9)
        var srcOffsetX by remember { mutableStateOf(0) }
        var srcOffsetY by remember { mutableStateOf(0) }
        var srcWidth by remember { mutableStateOf(bitmap.width) }
        var srcHeight by remember { mutableStateOf(bitmap.height) }
        var dstOffsetX by remember { mutableStateOf(0) }
        var dstOffsetY by remember { mutableStateOf(0) }
        var dstWidth by remember { mutableStateOf(bitmap.width) }
        var dstHeight by remember { mutableStateOf(bitmap.height) }
        val viewHeight = 300f
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .requiredHeight(viewHeight.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            translate(
                left = (canvasWidth / 2) - (bitmap.width / 2),
                top = (canvasHeight / 2) - (bitmap.height / 2)
            ) {
                drawImage(
                    image = bitmap,
                    srcOffset = IntOffset(srcOffsetX, srcOffsetY),
                    srcSize = IntSize(srcWidth, srcHeight),
                    dstOffset = IntOffset(dstOffsetX, dstOffsetY),
                    dstSize = IntSize(dstWidth, dstHeight),
                )
            }
        }

        Text(text = "srcOffsetX $srcOffsetX")
        Slider(
            value = srcOffsetX.toFloat(),
            onValueChange = { srcOffsetX = it.toInt() },
            valueRange = -viewHeight..viewHeight,
        )
        Text(text = "srcOffsetY $srcOffsetY")
        Slider(
            value = srcOffsetY.toFloat(),
            onValueChange = { srcOffsetY = it.toInt() },
            valueRange = -viewHeight..viewHeight,
        )
        Text(text = "srcWidth $srcWidth")
        Slider(
            value = srcWidth.toFloat(),
            onValueChange = { srcWidth = it.toInt() },
            valueRange = 0f..bitmap.width.toFloat(),
        )
        Text(text = "srcHeight $srcHeight")
        Slider(
            value = srcHeight.toFloat(),
            onValueChange = { srcHeight = it.toInt() },
            valueRange = 0f..bitmap.height.toFloat(),
        )
        Text(text = "dstOffsetX $dstOffsetX")
        Slider(
            value = dstOffsetX.toFloat(),
            onValueChange = { dstOffsetX = it.toInt() },
            valueRange = -viewHeight..viewHeight,
        )
        Text(text = "dstOffsetY $dstOffsetY")
        Slider(
            value = dstOffsetY.toFloat(),
            onValueChange = { dstOffsetY = it.toInt() },
            valueRange = -viewHeight..viewHeight,
        )
        Text(text = "dstWidth $dstWidth")
        Slider(
            value = dstWidth.toFloat(),
            onValueChange = { dstWidth = it.toInt() },
            valueRange = 0f..bitmap.width.toFloat(),
        )
        Text(text = "dstHeight $dstHeight")
        Slider(
            value = dstHeight.toFloat(),
            onValueChange = { dstHeight = it.toInt() },
            valueRange = 0f..bitmap.height.toFloat(),
        )
    }

    @Composable
    fun Image2Example() {
        val bitmap = ImageBitmap.imageResource(id = R.drawable.ic_post_image_9)
        var filterQuality by remember { mutableStateOf(FilterQuality.High) }
        var colorFilter by remember { mutableStateOf(ColorFilterStyle.NONE) }
        val renderWidth = bitmap.width * 0.5
        val renderHeight = bitmap.height * 0.5

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .requiredHeight(400.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            translate(
                left = ((canvasWidth / 2) - (renderWidth / 2)).toFloat(),
                top = ((canvasHeight / 2) - (renderHeight / 2)).toFloat()
            ) {
                drawImage(
                    image = bitmap,
                    dstSize = IntSize(renderWidth.toInt(), renderHeight.toInt()),
                    dstOffset = IntOffset(0, 0),
                    filterQuality = filterQuality,
                    colorFilter = getColorFilterObject(colorFilter),
                )
            }
        }
        VerticalSpace()
        SubTitle("Image Quality")
        VerticalSpace(5)
        CanvasDropDown(
            filterQuality.toString(),
            filterQualityList
        ) {
            if (filterQuality != it) {
                filterQuality = it
            }
        }
        VerticalSpace(10)
        SubTitle("Color Filter")
        VerticalSpace(5)
        CanvasDropDown(
            colorFilter.toString(),
            ColorFilterStyle.values().map { it.toString() to it }
        ) {
            if (colorFilter != it) {
                colorFilter = it
            }
        }
    }

    private fun getColorFilterObject(colorFilter: ColorFilterStyle): ColorFilter? {
        return when (colorFilter) {
            ColorFilterStyle.NONE -> null
            ColorFilterStyle.TINT -> ColorFilter.tint(Color.Red)
            ColorFilterStyle.COLOR_MATRIX -> ColorFilter.colorMatrix(
                ColorMatrix(
                    floatArrayOf(
                        1f, 1f, 1f, 0f, 0f,
                        1f, 1f, 1f, 0f, 0f,
                        1f, 1f, 1f, 0f, 0f,
                        0f, 0f, 0f, 1f, 0f
                    )
                )
            )
            ColorFilterStyle.LIGHTNING -> ColorFilter.lighting(Color.Red, Color.Unspecified)
        }
    }
}

