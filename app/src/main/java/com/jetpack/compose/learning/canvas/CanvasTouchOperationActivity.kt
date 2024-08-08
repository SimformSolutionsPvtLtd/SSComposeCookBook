package com.jetpack.compose.learning.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.LayersClear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.flowlayout.FlowRow
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

/**
 * Canvas + touch pointer examples
 * it creates a free draw board where user can draw anything with different stroke properties
 */
class CanvasTouchOperationActivity : ComponentActivity() {

    private val colorList = listOf(
        Color.Black, Color.DarkGray, Color.Gray,
        Color.LightGray, Color(0xFFEE5366),
        Color(0xFFFF6347), Color.Red,
        Color.Green, Color.Blue, Color.Yellow,
        Color.Cyan, Color.Magenta,
    )

    enum class DrawEvent {
        IDLE, DOWN, MOVE, UP
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Canvas + Touch") },
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
                        Title("With Drag")
                        VerticalSpace(10)
                        BasicTouchExample()
                        VerticalSpace()
                        Title("With Pointer Loop")
                        VerticalSpace(10)
                        PointerExample()
                        VerticalSpace()
                        Title("On Image")
                        VerticalSpace(10)
                        DrawOnImageExample()
                        VerticalSpace()
                    }
                }
            }
        }
    }

    @Composable
    private fun BasicTouchExample() {
        var drawProperties by remember { mutableStateOf(DrawProperties()) }
        val paths = remember { mutableStateListOf<Pair<Path, DrawProperties>>() }
        var drawEvent by remember { mutableStateOf(DrawEvent.IDLE) }
        var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
        var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
        var currentPath by remember { mutableStateOf(Path()) }
        val modifier = canvasModifier
            .shadow(3.dp, RoundedCornerShape(6.dp))
            .background(Color.White)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        // Wait for at least one pointer to press down, and set first contact position
                        val down: PointerInputChange = awaitFirstDown()
                        drawEvent = DrawEvent.DOWN
                        currentPosition = down.position
                        down.consume()
                        var pointer = down
                        val change: PointerInputChange? =
                            awaitTouchSlopOrCancellation(down.id) { change: PointerInputChange, over: Offset ->
                                if (change.positionChange() != Offset.Zero) {
                                    change.consume()
                                }
                            }
                        if (change != null) {
                            drag(change.id) { pointerInputChange: PointerInputChange ->
                                pointer = pointerInputChange
                                drawEvent = DrawEvent.MOVE
                                currentPosition = pointer.position
                                pointer.consume()
                            }
                            drawEvent = DrawEvent.UP
                            pointer.consume()
                        } else {
                            drawEvent = DrawEvent.UP
                            pointer.consume()
                        }
                    }
                }
            }

        Canvas(modifier = modifier) {
            when (drawEvent) {
                DrawEvent.DOWN -> {
                    currentPath.moveTo(currentPosition.x, currentPosition.y)
                    previousPosition = currentPosition
                }
                DrawEvent.MOVE -> {
                    currentPath.quadraticBezierTo(
                        previousPosition.x,
                        previousPosition.y,
                        (previousPosition.x + currentPosition.x) / 2,
                        (previousPosition.y + currentPosition.y) / 2
                    )
                    previousPosition = currentPosition
                }
                DrawEvent.UP -> {
                    currentPath.lineTo(currentPosition.x, currentPosition.y)
                    paths.add(Pair(currentPath, drawProperties))
                    currentPath = Path()
                    currentPosition = Offset.Unspecified
                    previousPosition = currentPosition
                    drawEvent = DrawEvent.IDLE
                }
                else -> Unit
            }
            paths.forEach {
                val path = it.first
                val property = it.second
                drawPath(
                    path = path,
                    brush = property.getBrush(),
                    style = property.getStroke(),
                )
            }
            if (drawEvent != DrawEvent.IDLE) {
                drawPath(
                    path = currentPath,
                    brush = drawProperties.getBrush(),
                    style = drawProperties.getStroke(),
                )
            }
        }
        DrawControls(drawProperties, {
            paths.clear()
        }) {
            drawProperties = it
        }
    }

    @Composable
    private fun PointerExample() {
        var drawProperties by remember { mutableStateOf(DrawProperties()) }
        val paths = remember { mutableStateListOf<Pair<Path, DrawProperties>>() }
        var drawEvent by remember { mutableStateOf(DrawEvent.IDLE) }
        var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
        var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
        var currentPath by remember { mutableStateOf(Path()) }
        val modifier = canvasModifier
            .shadow(3.dp, RoundedCornerShape(6.dp))
            .background(Color.White)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        val down: PointerInputChange = awaitFirstDown()
                        currentPosition = down.position
                        drawEvent = DrawEvent.DOWN
                        down.consume()
                        var pointerId = down.id
                        while (true) {
                            val event: PointerEvent = awaitPointerEvent()
                            val anyPressed = event.changes.any { it.pressed }
                            if (anyPressed) {
                                val pointerInputChange =
                                    event.changes.firstOrNull { it.id == pointerId }
                                        ?: event.changes.first()
                                pointerId = pointerInputChange.id
                                currentPosition = pointerInputChange.position
                                drawEvent = DrawEvent.MOVE
                                pointerInputChange.consume()
                            } else {
                                drawEvent = DrawEvent.UP
                                break
                            }
                        }
                    }
                }
            }

        Canvas(modifier = modifier) {
            when (drawEvent) {
                DrawEvent.DOWN -> {
                    currentPath.moveTo(currentPosition.x, currentPosition.y)
                    previousPosition = currentPosition
                }
                DrawEvent.MOVE -> {
                    currentPath.quadraticBezierTo(
                        previousPosition.x,
                        previousPosition.y,
                        (previousPosition.x + currentPosition.x) / 2,
                        (previousPosition.y + currentPosition.y) / 2

                    )
                    previousPosition = currentPosition
                }
                DrawEvent.UP -> {
                    currentPath.lineTo(currentPosition.x, currentPosition.y)
                    paths.add(Pair(currentPath, drawProperties))
                    currentPath = Path()
                    currentPosition = Offset.Unspecified
                    previousPosition = currentPosition
                    drawEvent = DrawEvent.IDLE
                }
                else -> Unit
            }
            paths.forEach {
                val path = it.first
                val property = it.second
                drawPath(
                    path = path,
                    brush = property.getBrush(),
                    style = property.getStroke(),
                )
            }
            if (drawEvent != DrawEvent.IDLE) {
                drawPath(
                    path = currentPath,
                    brush = drawProperties.getBrush(),
                    style = drawProperties.getStroke(),
                )
            }
        }
        DrawControls(drawProperties, {
            paths.clear()
        }) {
            drawProperties = it
        }
    }


    @Composable
    private fun DrawOnImageExample() {
        var drawProperties by remember { mutableStateOf(DrawProperties()) }
        val paths = remember { mutableStateListOf<Pair<Path, DrawProperties>>() }
        var drawEvent by remember { mutableStateOf(DrawEvent.IDLE) }
        var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
        var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
        var currentPath by remember { mutableStateOf(Path()) }
        val bitmap = ImageBitmap.imageResource(id = R.drawable.ic_canvas_2)
        val renderHeight = bitmap.height * 0.5
        val modifier = canvasModifier
            .shadow(3.dp, RoundedCornerShape(6.dp))
            .background(Color.White)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        val down: PointerInputChange = awaitFirstDown()
                        currentPosition = down.position
                        drawEvent = DrawEvent.DOWN
                        down.consume()
                        var pointerId = down.id
                        while (true) {
                            val event: PointerEvent = awaitPointerEvent()
                            val anyPressed = event.changes.any { it.pressed }
                            if (anyPressed) {
                                val pointerInputChange =
                                    event.changes.firstOrNull { it.id == pointerId }
                                        ?: event.changes.first()
                                pointerId = pointerInputChange.id
                                currentPosition = pointerInputChange.position
                                drawEvent = DrawEvent.MOVE
                                pointerInputChange.consume()
                            } else {
                                drawEvent = DrawEvent.UP
                                break
                            }
                        }
                    }
                }
            }

        Canvas(modifier = modifier) {
            when (drawEvent) {
                DrawEvent.DOWN -> {
                    currentPath.moveTo(currentPosition.x, currentPosition.y)
                    previousPosition = currentPosition
                }
                DrawEvent.MOVE -> {
                    currentPath.quadraticBezierTo(
                        previousPosition.x,
                        previousPosition.y,
                        (previousPosition.x + currentPosition.x) / 2,
                        (previousPosition.y + currentPosition.y) / 2

                    )
                    previousPosition = currentPosition
                }
                DrawEvent.UP -> {
                    currentPath.lineTo(currentPosition.x, currentPosition.y)
                    paths.add(Pair(currentPath, drawProperties))
                    currentPath = Path()
                    currentPosition = Offset.Unspecified
                    previousPosition = currentPosition
                    drawEvent = DrawEvent.IDLE
                }
                else -> Unit
            }
            drawImage(
                image = bitmap,
                dstSize = IntSize(size.width.toInt(), renderHeight.toInt()),
            )
            paths.forEach {
                val path = it.first
                val property = it.second
                drawPath(
                    path = path,
                    brush = property.getBrush(),
                    style = property.getStroke(),
                )
            }
            if (drawEvent != DrawEvent.IDLE) {
                drawPath(
                    path = currentPath,
                    brush = drawProperties.getBrush(),
                    style = drawProperties.getStroke(),
                )
            }
        }
        DrawControls(drawProperties, {
            paths.clear()
        }) {
            drawProperties = it
        }
    }


    @Composable
    fun DrawControls(
        drawProperties: DrawProperties,
        onClearAllLayer: () -> Unit,
        onDrawPropertiesChange: (DrawProperties) -> Unit
    ) {
        var color by remember { mutableStateOf(drawProperties.color) }
        var selectedDrawStyle by remember { mutableStateOf(drawProperties.selectedDrawStyle) }
        var selectedColorStyle by remember { mutableStateOf(drawProperties.selectedColorStyle) }
        var selectedTileMode by remember { mutableStateOf(drawProperties.selectedTileMode) }
        var strokeWidth by remember { mutableStateOf(drawProperties.strokeWidth) }
        var strokePathEffect by remember { mutableStateOf(drawProperties.strokePathEffect) }
        var pathEffectStyle by remember { mutableStateOf(drawProperties.pathEffectStyle) }
        var strokeCapMode by remember { mutableStateOf(drawProperties.strokeCapMode) }
        var strokeJoinMode by remember { mutableStateOf(drawProperties.strokeJoinMode) }
        var pathCornerRadius by remember { mutableStateOf(drawProperties.pathCornerRadius) }
        var showDialog by remember { mutableStateOf(false) }
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
        val brush = getBrush(selectedColorStyle, selectedTileMode, color)
        val stroke = Stroke(
            strokeWidth,
            pathEffect = pathEffect,
            cap = strokeCapMode,
            join = strokeJoinMode,
        )

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .requiredHeight(50.dp)
                .shadow(3.dp, RoundedCornerShape(6.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onClearAllLayer()
                },
                modifier = Modifier
                    .weight(0.5f)
            ) {
                Icon(
                    Icons.Filled.LayersClear,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(
                onClick = { showDialog = !showDialog }, modifier = Modifier
                    .weight(0.5f)
            ) {
                Icon(
                    Icons.Filled.BorderColor,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(30.dp)
                )
            }
            Canvas(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                val path = Path()
                path.moveTo(0f, size.height / 2)
                path.lineTo(size.width, size.height / 2)

                drawPath(
                    path,
                    brush = brush,
                    style = stroke,
                )
            }
        }
        if (showDialog) {
            ColorWithCommonControlDialog(
                color,
                {
                    color = it
                },
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
            ) {
                val newProperties = DrawProperties(
                    selectedDrawStyle,
                    selectedColorStyle,
                    selectedTileMode,
                    strokeWidth,
                    strokePathEffect,
                    pathEffectStyle,
                    strokeCapMode,
                    strokeJoinMode,
                    pathCornerRadius,
                    color
                )
                showDialog = false
                onDrawPropertiesChange(newProperties)
            }
        }
    }

    @Composable
    fun ColorWithCommonControlDialog(
        currentColor: Color = Color.Black, onColorChange: (Color) -> Unit,
        selectedDrawStyle: DrawStyle,
        onDrawStyleChange: (DrawStyle) -> Unit,
        strokeWidth: Float,
        onStrokeWidthChange: (Float) -> Unit,
        selectedPathEffect: PathStyle,
        onPathEffectChange: (PathStyle) -> Unit,
        selectedPathEffectStyle: PathEffectStyle,
        onPathEffectStyleChange: (PathEffectStyle) -> Unit,
        selectedStrokeCap: StrokeCap,
        onStrokeCapChange: (StrokeCap) -> Unit,
        pathCornerRadius: Float,
        onPathCornerRadiusChange: (Float) -> Unit,
        selectedColorStyle: ColorStyle? = null,
        onColorStyleChange: ((ColorStyle) -> Unit)? = null,
        selectedStrokeJoin: StrokeJoin? = null,
        onStrokeJoinChange: ((StrokeJoin) -> Unit)? = null,
        selectedTileMode: TileMode? = null,
        onTileModeChange: ((TileMode) -> Unit)? = null,
        onConfirmClick: () -> Unit,
    ) {
        val scrollState = rememberScrollState()
        Dialog(onDismissRequest = {}) {
            Box(
                Modifier
                    .shadow(2.dp, RoundedCornerShape(6.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        Modifier
                            .verticalScroll(scrollState)
                            .weight(1f, false)
                    ) {
                        AnimatedVisibility(visible = selectedColorStyle == null || selectedColorStyle == ColorStyle.SOLID) {
                            Column {
                                VerticalSpace()
                                Title("Color Pallet")
                                VerticalSpace(10)
                                FlowRow(mainAxisSpacing = 10.dp, crossAxisSpacing = 5.dp) {
                                    repeat(colorList.size) {
                                        val color = colorList[it]
                                        ColorItem(
                                            color = color,
                                            selected = color.value == currentColor.value,
                                            onClick = onColorChange
                                        )
                                    }
                                }
                            }
                        }
                        VerticalSpace()
                        CommonControls(
                            selectedDrawStyle,
                            onDrawStyleChange,
                            strokeWidth,
                            onStrokeWidthChange,
                            selectedPathEffect,
                            onPathEffectChange,
                            selectedPathEffectStyle,
                            onPathEffectStyleChange,
                            selectedStrokeCap,
                            onStrokeCapChange,
                            pathCornerRadius,
                            onPathCornerRadiusChange,
                            selectedColorStyle,
                            onColorStyleChange,
                            selectedStrokeJoin,
                            onStrokeJoinChange,
                            selectedTileMode,
                            onTileModeChange,
                            showTileModeControl = true,
                            showDrawStyleControl = false
                        )
                        VerticalSpace()
                    }
                    Button(modifier = Modifier.fillMaxWidth(), onClick = onConfirmClick) {
                        Text("OK")
                    }
                }
            }
        }
    }

    @Composable
    fun ColorItem(
        color: Color,
        selected: Boolean,
        onClick: (Color) -> Unit
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
                    onClick(color)
                }
        )
    }
}
