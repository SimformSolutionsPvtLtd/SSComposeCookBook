package com.jetpack.compose.learning.canvas

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

private const val START_ANGLE = 135f
private const val SWEEP_ANGLE = 270f
private const val ARC_RADIUS = 140f
private const val STROKE_SIZE = 25f

/**
 * This activity contains basic example related to canvas
 * Examples included common shapes, progress bar and a gauge meter
 */
class BasicCanvasExampleActivity : ComponentActivity() {

    private val paint = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 5f
        color = Color.Black.toArgb()
        textSize = 40f
    }

    private val currentSpeedPaint = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 5f
        color = Color.Black.toArgb()
        textSize = 120f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Basic Example") },
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
                            .padding(
                                start = 8.dp,
                                end = 8.dp,
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            )
                            .verticalScroll(scrollState)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        VerticalSpace()
                        Title("Basic Example:")
                        BasicCanvasExample()
                        VerticalSpace()
                        Title("Progress Indicator:")
                        LoadingCircle()
                        VerticalSpace()
                        Title("Gauge Meter:")
                        GaugeMeterExample()
                    }
                }
            }
        }
    }

    /**
     * Basic example containing the common shapes and their usage
     */
    @Composable
    fun BasicCanvasExample(modifier: Modifier = Modifier) {
        val radiusAnimation = remember { Animatable(0f) }
        LaunchedEffect(radiusAnimation) {
            radiusAnimation.animateTo(
                360f,
                animationSpec = tween(2000, easing = LinearEasing)
            )
        }
        val strokeWidth = 8f
        val stroke = Stroke(width = strokeWidth)

        Canvas(
            modifier = modifier
                .requiredSize(200.dp)
                .padding(8.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawRect(Color.Red, style = stroke, size = Size(canvasWidth, canvasHeight))
            inset(100f + strokeWidth) {
                val innerCanvasWidth = size.width
                val innerCanvasHeight = size.height
                drawLine(
                    color = Color.Green,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round,
                    start = Offset(0f, 0f),
                    end = Offset(innerCanvasWidth, innerCanvasHeight)
                )
                drawLine(
                    color = Color.Green,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round,
                    start = Offset(innerCanvasWidth, 0f),
                    end = Offset(0f, innerCanvasHeight)
                )
            }
            inset(50f) {
                drawArc(Color.Blue, 0f, radiusAnimation.value, false, style = stroke)
            }
        }
    }

    /**
     * Progress indicator with infinite animation transition
     */
    @Composable
    fun LoadingCircle(modifier: Modifier = Modifier) {
        val radius = 180f
        val infiniteTransition = rememberInfiniteTransition()
        val rotationAnim by infiniteTransition.animateFloat(
            initialValue = 0F,
            targetValue = 360F,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, easing = LinearEasing)
            )
        )
        val radiusAnim by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = radius,
            animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse)
        )
        val stroke = Stroke(
            width = 8f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
        )

        Canvas(
            modifier = modifier
                .padding(8.dp)
                .requiredHeight(radius.dp)
                .graphicsLayer {
                    rotationZ = rotationAnim
                }
        ) {
            val canvasHalfWidth = size.width / 2
            val canvasHalfHeight = size.height / 2
            drawCircle(
                Color.Red, radiusAnim,
                Offset(canvasHalfWidth, canvasHalfHeight),
                style = stroke,
            )
            drawCircle(
                Color.Green, radiusAnim / 1.5f,
                Offset(canvasHalfWidth, canvasHalfHeight),
                style = stroke,
            )
            drawCircle(
                Color.Blue, radiusAnim / 3,
                Offset(canvasHalfWidth, canvasHalfHeight),
                style = stroke,
            )
        }
    }

    /**
     * Gauge meter created with canvas
     * This example uses basic shapes + animation to achieve the gauge meter effects
     */
    @Composable
    fun GaugeMeterExample() {
        val startSpeed = 0
        val endSpeed = 100
        var currentSpeed by remember { mutableStateOf(45) }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GaugeMeter(startSpeed, endSpeed, currentSpeed)
            VerticalSpace()
            Row {
                Button(onClick = {
                    if (currentSpeed >= endSpeed) {
                        currentSpeed = endSpeed
                        return@Button
                    }
                    currentSpeed += 5
                }) {
                    Text("Increase Speed")
                }
                HorizontalSpace()
                Button(onClick = {
                    if (currentSpeed <= startSpeed) {
                        currentSpeed = startSpeed
                        return@Button
                    }
                    currentSpeed -= 5
                }) {
                    Text("Decrease Speed")
                }
            }
            VerticalSpace()
        }
    }

    @Composable
    fun GaugeMeter(
        startSpeed: Int,
        endSpeed: Int,
        currentSpeed: Int,
        numOfDivision: Int = 10,
    ) {
        val numOfLines = numOfDivision + 1
        val divisionValue = (endSpeed - startSpeed) / numOfDivision
        val floatAnimValues = (0 until numOfLines).map { remember { Animatable(1F) } }
        LaunchedEffect(floatAnimValues) {
            (0 until numOfLines).map { index ->
                launch {
                    floatAnimValues[index].animateTo(
                        targetValue = 0F,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = FastOutSlowInEasing,
                            delayMillis = 350 * index
                        )
                    )
                }
            }
        }
        val animateFloat = remember { Animatable(0f) }
        LaunchedEffect(animateFloat) {
            animateFloat.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
            )
        }
        val animateCurrentSpeedFloat = remember { Animatable(0f) }
        LaunchedEffect(animateFloat) {
            animateCurrentSpeedFloat.animateTo(
                targetValue = 1f,
                animationSpec = tween(4000)
            )
        }
        val measureText = currentSpeedPaint.measureText(currentSpeed.toString())

        Canvas(
            modifier = Modifier
                .requiredSize(300.dp)
                .padding(8.dp)
        ) {
            rotate(START_ANGLE + 90f) {
                var startAngle = 0f
                repeat(numOfLines) { index ->
                    rotate(degrees = startAngle) {
                        drawLine(
                            color = Color.Black,
                            alpha = if (floatAnimValues[index].value < 1f) 1f else 0f,
                            strokeWidth = 4F,
                            cap = StrokeCap.Round,
                            start = Offset(size.width / 2, ARC_RADIUS - STROKE_SIZE),
                            end = Offset(size.width / 2, STROKE_SIZE * 3)
                        )
                        drawIntoCanvas {
                            it.nativeCanvas.drawText(
                                "${startSpeed + (divisionValue * index)}",
                                (size.width / 2) - STROKE_SIZE, STROKE_SIZE * 2,
                                paint
                            )
                        }
                    }
                    startAngle += SWEEP_ANGLE / numOfDivision
                }
            }
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    "$currentSpeed",
                    (size.width / 2) - measureText / 2, size.height / 2,
                    currentSpeedPaint
                )
            }
            inset(ARC_RADIUS) {
                drawArc(
                    Color.Gray,
                    START_ANGLE,
                    SWEEP_ANGLE * animateFloat.value,
                    false,
                    style = Stroke(width = STROKE_SIZE, cap = StrokeCap.Round),
                )
                drawArc(
                    Color.Red,
                    START_ANGLE,
                    getCurrentSpeedAngle(
                        currentSpeed,
                        startSpeed,
                        endSpeed
                    ) * animateCurrentSpeedFloat.value,
                    false,
                    style = Stroke(width = STROKE_SIZE, cap = StrokeCap.Round),
                )
            }
        }
    }

    private fun getCurrentSpeedAngle(currentSpeed: Int, startSpeed: Int, endSpeed: Int): Float {
        val newValue = (endSpeed.toFloat() - startSpeed.toFloat()) / currentSpeed.toFloat()
        return SWEEP_ANGLE / newValue
    }
}
