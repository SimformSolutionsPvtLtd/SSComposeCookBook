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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlin.math.roundToInt

/**
 * Canvas blend mode examples with shapes and images
 */
class CanvasBlendModesActivity : ComponentActivity() {

    private val blendModeList = listOf(
        BlendMode.Clear.toString() to BlendMode.Clear,
        BlendMode.Src.toString() to BlendMode.Src,
        BlendMode.Dst.toString() to BlendMode.Dst,
        BlendMode.SrcOver.toString() to BlendMode.SrcOver,
        BlendMode.DstOver.toString() to BlendMode.DstOver,
        BlendMode.SrcIn.toString() to BlendMode.SrcIn,
        BlendMode.DstIn.toString() to BlendMode.DstIn,
        BlendMode.SrcOut.toString() to BlendMode.SrcOut,
        BlendMode.DstOut.toString() to BlendMode.DstOut,
        BlendMode.SrcAtop.toString() to BlendMode.SrcAtop,
        BlendMode.DstAtop.toString() to BlendMode.DstAtop,
        BlendMode.Xor.toString() to BlendMode.Xor,
        BlendMode.Plus.toString() to BlendMode.Plus,
        BlendMode.Modulate.toString() to BlendMode.Modulate,
        BlendMode.Screen.toString() to BlendMode.Screen,
        BlendMode.Overlay.toString() to BlendMode.Overlay,
        BlendMode.Darken.toString() to BlendMode.Darken,
        BlendMode.Lighten.toString() to BlendMode.Lighten,
        BlendMode.ColorDodge.toString() to BlendMode.ColorDodge,
        BlendMode.ColorBurn.toString() to BlendMode.ColorBurn,
        BlendMode.Hardlight.toString() to BlendMode.Hardlight,
        BlendMode.Softlight.toString() to BlendMode.Softlight,
        BlendMode.Difference.toString() to BlendMode.Difference,
        BlendMode.Exclusion.toString() to BlendMode.Exclusion,
        BlendMode.Multiply.toString() to BlendMode.Multiply,
        BlendMode.Hue.toString() to BlendMode.Hue,
        BlendMode.Saturation.toString() to BlendMode.Saturation,
        BlendMode.Color.toString() to BlendMode.Color,
        BlendMode.Luminosity.toString() to BlendMode.Luminosity
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Blend Mode") },
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
                        Title("With Shapes")
                        ShapeBlendMode()
                        VerticalSpace()
                        Title("With Shape and Image")
                        ShapeImageBlendMode()
                        VerticalSpace()
                        Title("With Shape and Image 2")
                        ShapeImage2BlendMode()
                        VerticalSpace()
                        Title("With Images")
                        ImagesBlendMode()
                        VerticalSpace()
                    }
                }
            }
        }
    }

    @Composable
    fun ShapeBlendMode() {
        var blendMode by remember { mutableStateOf(DefaultBlendMode) }
        Canvas(modifier = canvasModifier) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            with(drawContext.canvas.nativeCanvas) {
                val mark = saveLayer(null, null)
                //Destination
                drawCircle(
                    Color.Cyan,
                    170f,
                    Offset(canvasWidth / 2.5f, canvasHeight / 2f)
                )
                //Source
                drawRect(
                    Color.Red,
                    Offset(70f + (canvasWidth / 2.5f), canvasHeight / 2.5f),
                    Size(canvasWidth / 2f, canvasHeight / 2f),
                    blendMode = blendMode
                )
                restoreToCount(mark)
            }
        }
        VerticalSpace(10)
        SubTitle("Blend Mode")
        VerticalSpace(5)
        CanvasDropDown(blendMode.toString(), blendModeList) {
            if (blendMode != it) {
                blendMode = it
            }
        }
    }

    @Composable
    fun ShapeImageBlendMode() {
        var blendMode by remember { mutableStateOf(BlendMode.SrcOut) }
        val bitmap = ImageBitmap.imageResource(id = R.drawable.ic_canvas_1)
        val renderWidth = bitmap.width * 0.5
        val renderHeight = bitmap.height * 0.5

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .requiredHeight(250.dp)
        ) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            translate(
                left = ((canvasWidth / 2) - (renderWidth / 2)).toFloat(),
                top = ((canvasHeight / 2) - (renderHeight / 2)).toFloat()
            ) {
                drawImage(
                    image = bitmap,
                    dstSize = IntSize(renderWidth.toInt(), renderHeight.toInt()),
                    filterQuality = FilterQuality.High,
                )
                with(drawContext.canvas.nativeCanvas) {
                    val mark = saveLayer(null, null)
                    //Destination
                    drawCircle(Color.Blue, 170f)
                    //Source
                    drawRect(
                        Color.Black, size = Size(
                            renderWidth.toFloat(),
                            renderHeight.toFloat()
                        ), blendMode = blendMode
                    )
                    restoreToCount(mark)
                }
            }
        }
        VerticalSpace(10)
        SubTitle("Blend Mode")
        VerticalSpace(5)
        CanvasDropDown(blendMode.toString(), blendModeList) {
            if (blendMode != it) {
                blendMode = it
            }
        }
    }

    @Composable
    fun ShapeImage2BlendMode() {
        var blendMode by remember { mutableStateOf(BlendMode.SrcIn) }
        val bitmap = ImageBitmap.imageResource(id = R.drawable.ic_canvas_1)
        val renderWidth = bitmap.width * 0.5
        val renderHeight = bitmap.height * 0.5
        var radius by remember { mutableStateOf(150f) }
        var xPosition by remember { mutableStateOf(150f) }
        var yPosition by remember { mutableStateOf(150f) }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .requiredHeight(250.dp)
        ) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            translate(
                left = ((canvasWidth / 2) - (renderWidth / 2)).toFloat(),
                top = ((canvasHeight / 2) - (renderHeight / 2)).toFloat()
            ) {
                with(drawContext.canvas.nativeCanvas) {
                    val mark = saveLayer(null, null)
                    //Destination
                    //If circle goes out bound of the image then background will be white color.
                    //If you set any color here that will be visible outside of the image size.
                    drawCircle(Color.White, radius, center = Offset(xPosition, yPosition))
                    //Source
                    drawImage(
                        image = bitmap,
                        dstSize = IntSize(renderWidth.toInt(), renderHeight.toInt()),
                        filterQuality = FilterQuality.High,
                        blendMode = blendMode
                    )
                    restoreToCount(mark)
                }
            }
        }
        VerticalSpace(10)
        SubTitle("Radius ${radius.roundToInt()} Px")
        Slider(
            value = radius,
            onValueChange = { radius = it },
            valueRange = 150f..200f,
        )
        VerticalSpace(5)
        SubTitle("X Position")
        Slider(
            value = xPosition,
            onValueChange = { xPosition = it },
            valueRange = 150f..renderWidth.toFloat(),
        )
        VerticalSpace(5)
        SubTitle("Y Position")
        Slider(
            value = yPosition,
            onValueChange = { yPosition = it },
            valueRange = 150f..renderHeight.toFloat(),
        )
        VerticalSpace(5)
        SubTitle("Blend Mode")
        VerticalSpace(5)
        CanvasDropDown(
            blendMode.toString(),
            blendModeList
        ) {
            if (blendMode != it) {
                blendMode = it
            }
        }
    }

    /**
     * To make image clip with each other via blending modes
     * Make sure one of the images have transparent background so it will blend good.
     */
    @Composable
    fun ImagesBlendMode() {
        var blendMode by remember { mutableStateOf(DefaultBlendMode) }
        val bitmap1 = ImageBitmap.imageResource(id = R.drawable.ic_canvas_2)
        val bitmap2 = ImageBitmap.imageResource(id = R.drawable.ic_canvas_3)
        val renderWidth = bitmap1.width * 0.5
        val renderHeight = bitmap1.height * 0.5

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .requiredHeight(250.dp)
        ) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            translate(
                left = ((canvasWidth / 2) - (renderWidth / 2)).toFloat(),
                top = ((canvasHeight / 2) - (renderHeight / 2)).toFloat()
            ) {
                with(drawContext.canvas.nativeCanvas) {
                    val mark = saveLayer(null, null)
                    //Destination
                    drawImage(
                        image = bitmap2,
                        dstSize = IntSize(renderWidth.toInt(), renderHeight.toInt()),
                    )
                    //Source
                    drawImage(
                        image = bitmap1,
                        dstSize = IntSize(renderWidth.toInt(), renderHeight.toInt()),
                        blendMode = blendMode
                    )
                    restoreToCount(mark)
                }
            }
        }
        VerticalSpace(10)
        SubTitle("Blend Mode")
        VerticalSpace(5)
        CanvasDropDown(blendMode.toString(), blendModeList) {
            if (blendMode != it) {
                blendMode = it
            }
        }
    }
}
