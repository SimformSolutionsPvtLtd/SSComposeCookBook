package com.jetpack.compose.learning.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Screen illustrates the drawing on canvas using compose with gestures and
 * several customization options like stroke width and colors
 */
class CanvasDrawingBoardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Drawing Board") },
                        navigationIcon = {
                            IconButton(onClick = { onBackPressed() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                }) {
                    DrawingSample(Modifier.padding(it))
                }
            }
        }
    }

    @Composable
    fun DrawingSample(
        modifier: Modifier = Modifier
    ) {
        var strokeWidth by remember {
            mutableStateOf(4f)
        }
        var penColor by remember {
            mutableStateOf(Color.Black)
        }

        Column(
            modifier = modifier.fillMaxSize()
        ) {
            DrawBoard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                penStrokeWidth = strokeWidth,
                penColor = penColor
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(text = "Color")
                ColorPalette(
                    onColorSelected = {
                        penColor = it
                    }
                )
                Text(text = "Pen's Stroke Size")
                Slider(
                    value = strokeWidth,
                    steps = 12,
                    valueRange = 2f..15f,
                    onValueChange = {
                        strokeWidth = it
                    }
                )
            }
        }
    }

    @Composable
    fun ColorPalette(
        onColorSelected: (Color) -> Unit
    ) {
        val colorList = listOf(
            Color.Black, Color.DarkGray, Color.Gray,
            Color.LightGray, Color(0xFFEE5366), Color.Red,
            Color.Green, Color.Blue, Color.Yellow,
            Color.Cyan, Color.Magenta,
        )
        var selectedIndex by remember { mutableStateOf(0) }
        val onItemClick = { index: Int, color: Color ->
            selectedIndex = index
            onColorSelected(color)
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(colorList.size) { index ->
                ColorItem(
                    index = index,
                    color = colorList[index],
                    selected = selectedIndex == index,
                    onClick = onItemClick
                )
            }
        }
    }

    @Composable
    fun ColorItem(
        index: Int,
        color: Color,
        selected: Boolean,
        onClick: (Int, Color) -> Unit
    ) {
        val shape = RoundedCornerShape(50)
        val modifier = if (selected) {
            Modifier
                .padding(3.dp)
                .background(Color.White, shape)
                .padding(3.dp)
                .background(color, shape)
        } else {
            Modifier
        }

        Box(
            modifier = Modifier
                .size(30.dp, 30.dp)
                .background(color, RoundedCornerShape(50))
                .then(modifier)
                .clickable {
                    onClick(index, color)
                }
        )
    }

    @Composable
    fun DrawBoard(
        modifier: Modifier,
        penStrokeWidth: Float = 4f,
        penColor: Color = Color.Black
    ) {
        // Store path and path properties for individual path drawings
        val paths = remember { mutableStateListOf<Pair<Path, PathCharacteristics>>() }
        var motionEvent by remember { mutableStateOf(MotionEvent.IDLE) }
        var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
        var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
        var currentPath by remember { mutableStateOf(Path()) }
        var currentPathProperty by remember {
            mutableStateOf(
                PathCharacteristics(
                    penStrokeWidth,
                    penColor
                )
            )
        }.apply {
            value = PathCharacteristics(penStrokeWidth, penColor)
        }

        BoxWithConstraints(
            modifier = modifier
                .clipToBounds(),
        ) {
            Canvas(
                modifier = Modifier
                    .width(maxWidth)
                    .height(maxHeight)
                    .pointerInput(Unit) {
                        forEachGesture {
                            awaitPointerEventScope {
                                // Wait for at least one pointer to press down, and set first contact position
                                val down: PointerInputChange = awaitFirstDown()
                                motionEvent = MotionEvent.DOWN
                                currentPosition = down.position
                                if (down.pressed != down.previousPressed) down.consume()
                                var pointer = down
                                val change: PointerInputChange? =
                                    awaitTouchSlopOrCancellation(down.id) { change: PointerInputChange, _: Offset ->
                                        if (change.positionChange() != Offset.Zero) change.consume()
                                    }
                                if (change != null) {
                                    drag(change.id) { pointerInputChange: PointerInputChange ->
                                        pointer = pointerInputChange
                                        motionEvent = MotionEvent.MOVE
                                        currentPosition = pointer.position
                                        if (pointer.positionChange() != Offset.Zero) pointer.consume()
                                    }
                                    motionEvent = MotionEvent.UP
                                    if (pointer.pressed != pointer.previousPressed) pointer.consume()
                                } else {
                                    motionEvent = MotionEvent.UP
                                    if (pointer.pressed != pointer.previousPressed) pointer.consume()
                                }
                            }
                        }
                    }
            ) {
                // Draw here
                when (motionEvent) {
                    MotionEvent.DOWN -> {
                        currentPath.moveTo(currentPosition.x, currentPosition.y)
                        previousPosition = currentPosition
                    }
                    MotionEvent.MOVE -> {
                        currentPath.quadraticBezierTo(
                            previousPosition.x,
                            previousPosition.y,
                            (previousPosition.x + currentPosition.x) / 2,
                            (previousPosition.y + currentPosition.y) / 2
                        )
                        previousPosition = currentPosition
                    }
                    MotionEvent.UP -> {
                        // Save path and rest all properties
                        currentPath.lineTo(currentPosition.x, currentPosition.y)
                        paths.add(Pair(currentPath, currentPathProperty))
                        currentPath = Path()
                        currentPathProperty = PathCharacteristics(
                            strokeWidth = currentPathProperty.strokeWidth,
                            color = currentPathProperty.color
                        )
                        currentPosition = Offset.Unspecified
                        previousPosition = currentPosition
                        motionEvent = MotionEvent.IDLE
                    }
                    else -> Unit
                }

                // draw all the paths with individual stroke and color
                paths.forEach {
                    val path = it.first
                    val property = it.second
                    drawPath(
                        color = property.color,
                        path = path,
                        style = Stroke(
                            width = property.strokeWidth,
                            cap = StrokeCap.Round, join = StrokeJoin.Round
                        )
                    )
                }

                if (motionEvent != MotionEvent.IDLE) {
                    // draw path based on current ongoing gesture
                    drawPath(
                        color = currentPathProperty.color,
                        path = currentPath,
                        style = Stroke(
                            width = currentPathProperty.strokeWidth,
                            cap = StrokeCap.Round, join = StrokeJoin.Round
                        )
                    )
                }
            }
        }
    }

    enum class MotionEvent {
        IDLE, DOWN, MOVE, UP
    }

    data class PathCharacteristics(
        val strokeWidth: Float = 4f,
        val color: Color = Color.Black
    )
}

