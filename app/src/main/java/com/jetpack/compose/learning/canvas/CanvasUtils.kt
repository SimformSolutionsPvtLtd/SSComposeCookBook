package com.jetpack.compose.learning.canvas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

enum class DrawStyle { STROKE, FILL }

enum class ColorStyle {
    SOLID, LINEAR_GRADIENT, HORIZONTAL_GRADIENT, VERTICAL_GRADIENT, RADIAL_GRADIENT, SWEEP_GRADIENT;

    fun showTileMode(): Boolean {
        return this != SOLID && this != SWEEP_GRADIENT
    }
}

enum class PathStyle { NONE, DASH, ROUND_CORNER, CUSTOM_SHAPE }

enum class PathEffectStyle { TRANSLATE, MORPH, ROTATE }

enum class ColorFilterStyle { NONE, TINT, COLOR_MATRIX, LIGHTNING }

val canvasModifier = Modifier
    .fillMaxWidth()
    .background(Color.White)
    .height(200.dp)

val canvasMixColorsList = listOf(
    Color.Red,
    Color.Blue,
    Color.Green
)

val diamondPath = Path().apply {
    moveTo(20f, 0f)
    lineTo(40f, 20f)
    lineTo(20f, 40f)
    lineTo(0f, 20f)
}

private val tileModeList = listOf(
    TileMode.Clamp.toString() to TileMode.Clamp,
    TileMode.Decal.toString() to TileMode.Decal,
    TileMode.Mirror.toString() to TileMode.Mirror,
    TileMode.Repeated.toString() to TileMode.Repeated
)

private val capModeList = listOf(
    StrokeCap.Butt.toString() to StrokeCap.Butt,
    StrokeCap.Round.toString() to StrokeCap.Round,
    StrokeCap.Square.toString() to StrokeCap.Square,
)

private val joinModeList = listOf(
    StrokeJoin.Bevel.toString() to StrokeJoin.Bevel,
    StrokeJoin.Round.toString() to StrokeJoin.Round,
    StrokeJoin.Miter.toString() to StrokeJoin.Miter,
)

@Composable
fun VerticalSpace(spaceHeightDP: Int = 20) {
    Spacer(modifier = Modifier.requiredHeight(spaceHeightDP.dp))
}

@Composable
fun HorizontalSpace(spaceHeightDP: Int = 20) {
    Spacer(modifier = Modifier.requiredWidth(spaceHeightDP.dp))
}

@Composable
fun Title(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Start,
        modifier = modifier
    )
}

@Composable
fun SubTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Start,
        modifier = modifier
    )
}

@Composable
fun RadioSelector(
    selected: Boolean,
    onChangeClick: (Boolean) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected,
            onClick = {
                onChangeClick.invoke(!selected)
            },
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primary),
        )
        Text(
            text = title,
            Modifier.clickable {
                onChangeClick.invoke(!selected)
            },
        )
    }
}

@Composable
fun CanvasCheckBox(title: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange.invoke(!isChecked)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Checkbox(
            checked = isChecked, onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.primary,
                uncheckedColor = Color.DarkGray,
                checkmarkColor = Color.White
            )
        )
        Text(
            text = title, Modifier.padding(start = 5.dp), fontSize = 16.sp
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> CanvasDropDown(
    initialValue: String,
    dropDownItems: List<Pair<String, T>>,
    onChangeClick: (T) -> Unit
) {
    var value by remember { mutableStateOf(initialValue) }
    var isSimpleDropDownExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = isSimpleDropDownExpanded,
        onExpandedChange = {
            isSimpleDropDownExpanded = !isSimpleDropDownExpanded
        },
    ) {
        OutlinedTextField(
            value = value.cleanName(),
            onValueChange = { },
            enabled = false,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = isSimpleDropDownExpanded,
            onDismissRequest = { isSimpleDropDownExpanded = false },
            modifier = Modifier.fillMaxWidth(0.9f),
        ) {
            dropDownItems.forEach {
                DropdownMenuItem(onClick = {
                    value = it.first
                    isSimpleDropDownExpanded = false
                    onChangeClick.invoke(it.second)
                }, modifier = Modifier.fillMaxWidth(0.9f)) {
                    Text(it.first.cleanName())
                }
            }
        }
    }
}

@Composable
fun CommonControls(
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
    showColorStyleControl: Boolean = true,
    showDrawStyleControl: Boolean = true,
    showStrokeJoinControl: Boolean = false,
    showTileModeControl: Boolean = false,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (showDrawStyleControl) {
            SubTitle("Draw Style")
            VerticalSpace(10)
            CanvasDropDown(
                selectedDrawStyle.name,
                DrawStyle.values().map { Pair(it.name, it) },
                onDrawStyleChange
            )
        }
        AnimatedVisibility(visible = selectedDrawStyle == DrawStyle.STROKE) {
            Column {
                VerticalSpace()
                SubTitle("Stroke Width ${strokeWidth.roundToInt()}")
                VerticalSpace(5)
                Slider(
                    value = strokeWidth,
                    onValueChange = onStrokeWidthChange,
                    valueRange = 10f..30f,
                )
                VerticalSpace(10)
                SubTitle("Stroke Cap Type")
                VerticalSpace(5)
                CanvasDropDown(
                    selectedStrokeCap.toString(),
                    capModeList,
                    onStrokeCapChange
                )
                if (showStrokeJoinControl && onStrokeJoinChange != null) {
                    VerticalSpace(10)
                    SubTitle("Stroke Join Type")
                    VerticalSpace(5)
                    CanvasDropDown(
                        selectedStrokeJoin.toString(),
                        joinModeList,
                        onStrokeJoinChange
                    )
                }
                VerticalSpace(10)
                SubTitle("Path Effect")
                VerticalSpace(5)
                CanvasDropDown(
                    selectedPathEffect.name,
                    PathStyle.values().map { Pair(it.name, it) },
                    onPathEffectChange
                )
                AnimatedVisibility(visible = selectedPathEffect == PathStyle.CUSTOM_SHAPE) {
                    Column {
                        VerticalSpace(10)
                        SubTitle("Path Effect Style")
                        VerticalSpace(5)
                        CanvasDropDown(
                            selectedPathEffectStyle.name,
                            PathEffectStyle.values().map { Pair(it.name, it) },
                            onPathEffectStyleChange
                        )
                    }
                }
                AnimatedVisibility(visible = selectedPathEffect == PathStyle.ROUND_CORNER) {
                    Column {
                        VerticalSpace(10)
                        SubTitle("Round Corner")
                        VerticalSpace(5)
                        Slider(
                            value = pathCornerRadius,
                            onValueChange = onPathCornerRadiusChange,
                            valueRange = 10f..30f,
                        )
                    }
                }
            }
        }
        if (showColorStyleControl && selectedColorStyle != null && onColorStyleChange != null) {
            VerticalSpace(10)
            SubTitle("Color Style")
            VerticalSpace(10)
            CanvasDropDown(
                selectedColorStyle.name,
                ColorStyle.values().map { Pair(it.name, it) },
                onColorStyleChange
            )
        }
        AnimatedVisibility(visible = selectedColorStyle != null && selectedColorStyle.showTileMode() && showTileModeControl && onTileModeChange != null) {
            Column {
                VerticalSpace()
                SubTitle("Tile Mode")
                VerticalSpace(10)
                CanvasDropDown(
                    selectedTileMode.toString(),
                    tileModeList,
                    onTileModeChange!!
                )
            }
        }
    }
}

fun getBrush(
    colorStyle: ColorStyle,
    tileMode: TileMode = TileMode.Clamp,
    solidColor: Color = Color(0xFFFF6347)
): Brush {
    return when (colorStyle) {
        ColorStyle.SOLID -> SolidColor(solidColor)
        ColorStyle.LINEAR_GRADIENT -> Brush.linearGradient(canvasMixColorsList, tileMode = tileMode)
        ColorStyle.HORIZONTAL_GRADIENT -> Brush.horizontalGradient(
            canvasMixColorsList,
            tileMode = tileMode
        )
        ColorStyle.VERTICAL_GRADIENT -> Brush.verticalGradient(
            canvasMixColorsList,
            tileMode = tileMode
        )
        ColorStyle.RADIAL_GRADIENT -> Brush.radialGradient(canvasMixColorsList, tileMode = tileMode)
        ColorStyle.SWEEP_GRADIENT -> Brush.sweepGradient(canvasMixColorsList)
    }
}

fun createAnyPolygonPath(centerX: Float, centerY: Float, sides: Int, radius: Float): Path {
    val angle = 2.0 * Math.PI / sides
    return Path().apply {
        moveTo(
            centerX + (radius * cos(0f)),
            centerY + (radius * sin(0f))
        )
        for (i in 1 until sides) {
            lineTo(
                centerX + (radius * cos(angle * i)).toFloat(),
                centerY + (radius * sin(angle * i)).toFloat()
            )
        }
        close()
    }
}

fun String.cleanName(): String {
    return this.replace("_", " ").lowercase(Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}
