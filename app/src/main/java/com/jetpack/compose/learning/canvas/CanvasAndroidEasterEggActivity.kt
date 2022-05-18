package com.jetpack.compose.learning.canvas

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * Android 11 easter egg created with the help of canvas
 */
class CanvasAndroidEasterEggActivity : ComponentActivity() {

    private val startAngle = 45f
    private val endAngle = 360f
    private val numOfParts = 9
    private val innerControllerRadius = 45f
    private val maxAngleChange = 10f
    private val mainText = "11"

    private val poppinsTypeface by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            resources.getFont(R.font.font_poppins_regular)
        } else {
            ResourcesCompat.getFont(this, R.font.font_poppins_regular)
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Android Easter Egg") },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed() }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                                }
                            }
                        )
                    }
                ) {
                    EasterEggExample()
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun EasterEggExample() {
        val perAngle = (endAngle - startAngle) / numOfParts
        var centerPosition by remember { mutableStateOf(Offset.Zero) }
        var greenCircleRadius by remember { mutableStateOf(0f) }
        var innerCircleAngle by remember { mutableStateOf(startAngle) }
        //TODO - Work on the logic of showing the android 11 text properly.
        val paint = Paint().apply {
            style = PaintingStyle.Fill
            color = Color(0xFFFB6439)
            alpha = if (innerCircleAngle >= endAngle - 5) 1f else 0f
        }.asFrameworkPaint()
        paint.apply {
            textSize = with(LocalDensity.current) { 70.sp.toPx() }
            typeface = poppinsTypeface
        }
        val measureText = paint.measureText(mainText)
        val modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFF073042))
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_MOVE -> {
                        val newAngle = adjustAngle(getAngle(Offset(it.x, it.y), centerPosition))
                        if (checkAngle(newAngle, innerCircleAngle)) {
                            innerCircleAngle = newAngle
                        }
                    }
                }
                true
            }

        Canvas(modifier) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val halfCanvasWidth = canvasWidth / 2
            val halfCanvasHeight = canvasHeight / 2
            centerPosition = Offset(halfCanvasWidth, halfCanvasHeight)
            greenCircleRadius = (size.minDimension / 2) / 1.8f
            val outerSquareRadius = greenCircleRadius * 1.5f
            val innerWhiteCircleRadiusPath = greenCircleRadius - (innerControllerRadius * 2)

            rotate(50f) {
                drawRect(
                    Color(0xFF062B3E),
                    Offset(
                        halfCanvasWidth,
                        halfCanvasHeight - greenCircleRadius
                    ),
                    Size(greenCircleRadius * 2, greenCircleRadius * 2)
                )
            }
            drawCircle(Color(0xFF3DDB84), radius = greenCircleRadius)
            repeat(numOfParts) { index ->
                val angle = (startAngle + (perAngle * index)).toDouble()
                val cX = halfCanvasWidth + outerSquareRadius * cos(Math.toRadians(angle)).toFloat()
                val cY = halfCanvasHeight + outerSquareRadius * sin(Math.toRadians(angle)).toFloat()
                drawCircle(Color.White, if (innerCircleAngle >= angle) 20f else 10f, Offset(cX, cY))
            }
            rotate(innerCircleAngle) {
                drawCircle(
                    Color.White,
                    innerControllerRadius,
                    Offset(halfCanvasWidth + innerWhiteCircleRadiusPath, halfCanvasHeight)
                )
            }
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    mainText,
                    halfCanvasWidth + (outerSquareRadius + greenCircleRadius) / 2,
                    halfCanvasHeight + measureText / 2,
                    paint
                )
            }
        }
    }

    private fun checkAngle(newAngle: Float, innerCircleAngle: Float): Boolean {
        return abs(newAngle - innerCircleAngle) < maxAngleChange
    }

    private fun adjustAngle(angle: Float): Float {
        var actualAngle = 360 - angle
        if (actualAngle < startAngle) {
            actualAngle = startAngle
        } else if (actualAngle > endAngle) {
            actualAngle = endAngle
        }
        return actualAngle
    }

    private fun getAngle(touchOffset: Offset, centerPosition: Offset): Float {
        //If we have co-ordinates and we need to find the angle we can do it via this formula
        //thanks to this SO answer - https://stackoverflow.com/a/8968708/7276442
        if (touchOffset == Offset.Unspecified || centerPosition == Offset.Unspecified) {
            return 0f
        }
        val dy = centerPosition.y - touchOffset.y
        val dx = touchOffset.x - centerPosition.x
        val toDegrees = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble()))
        return ((toDegrees + 360.0) % 360.0).toFloat()
    }
}
