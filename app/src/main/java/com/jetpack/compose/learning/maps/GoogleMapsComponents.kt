package com.jetpack.compose.learning.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

val statueOfLiberty = LatLng(40.689247, -74.044502)

val mapColorList = listOf(
    Color.Black,
    Color.Blue,
    Color.Cyan,
    Color.LightGray,
    Color.Red,
    Color.Green,
    Color.Magenta,
    Color.DarkGray
)

@Composable
fun MapCheckBoxProperty(checked: Boolean, title: String, onCheckedChange: (Boolean) -> Unit) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            MapTitle(title)
            Spacer(modifier = Modifier.requiredWidth(10.dp))
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MaterialTheme.colors.primary,
                    checkedThumbColor = MaterialTheme.colors.primary
                )
            )
        }
        MapVerticalSpace(5.dp)
    }
}

@Composable
fun MapSliderProperty(
    value: Float,
    title: String,
    onValueChange: (Float) -> Unit,
    min: Float = 0f,
    max: Float = 359f
) {
    Column {
        MapTitle("$title (${"%.2f".format(value)})")
        Slider(value = value, onValueChange = onValueChange, valueRange = min..max)
        MapVerticalSpace(5.dp)
    }
}

@Composable
fun MapTitle(title: String) {
    Column {
        Text(title, style = MaterialTheme.typography.subtitle1)
        MapVerticalSpace(5.dp)
    }
}

@Composable
fun MapVerticalSpace(height: Dp = 10.dp) {
    Spacer(modifier = Modifier.requiredHeight(height))
}

@Composable
fun MapColorOptions(title: String, color: Color, onColorSelect: (Color) -> Unit) {
    Column {
        MapTitle(title)
        FlowRow(
            mainAxisSpacing = 12.dp,
            crossAxisSpacing = 6.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            mapColorList.forEach {
                OutlinedButton(onClick = {
                    onColorSelect(it)
                }) {
                    AnimatedVisibility(visible = color == it) {
                        Row {
                            Icon(
                                Icons.Rounded.Check,
                                contentDescription = "",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.requiredWidth(8.dp))
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .size(50.dp, 25.dp)
                            .background(it)
                    )
                }
            }
        }
        MapVerticalSpace()
    }
}

@Composable
fun GoogleMapCommonShapeOptions(
    modifier: Modifier = Modifier,
    uiState: CommonShapeUiState,
    onStrokeColorChange: (Color) -> Unit,
    onPatternChange: (StrokePatternType) -> Unit,
    onVisibilityChange: (Boolean) -> Unit,
    onWidthChange: (Float) -> Unit,
    onClickableChange: (Boolean) -> Unit,
    onJointTypeChange: ((StrokeJointType) -> Unit)? = null,
    onStrokeColorAlphaChange: (Float) -> Unit,
) {
    Column(modifier = modifier) {
        MapVerticalSpace()
        MapSliderProperty(uiState.strokeWidth, "Stroke Width", onWidthChange, 10f, 25f)
        MapColorOptions("Stroke Color", uiState.strokeColor, onStrokeColorChange)
        MapSliderProperty(
            uiState.strokeColorAlpha,
            "Stroke Color Alpha",
            onStrokeColorAlphaChange,
            max = 1f
        )
        if (onJointTypeChange != null)
            MapStrokeJointOptions(uiState.jointType, onJointTypeChange)
        MapStrokePatternOptions(uiState.patternType, onPatternChange)
        MapCheckBoxProperty(uiState.visibility, "Visible", onVisibilityChange)
        MapCheckBoxProperty(uiState.clickable, "Clickable", onClickableChange)
    }
}

@Composable
fun MapStrokeJointOptions(
    jointOption: StrokeJointType,
    onPolylineJointSelect: (StrokeJointType) -> Unit
) {
    Column {
        MapTitle("Joint Type")
        FlowRow(
            mainAxisSpacing = 12.dp,
            crossAxisSpacing = 6.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            StrokeJointType.values().forEach {
                OutlinedButton(onClick = {
                    onPolylineJointSelect(it)
                }) {
                    AnimatedVisibility(visible = jointOption == it) {
                        Row {
                            Icon(
                                Icons.Rounded.Check,
                                contentDescription = "",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.requiredWidth(8.dp))
                        }
                    }
                    Text(it.toString())
                }
            }
        }
        MapVerticalSpace()
    }
}

@Composable
fun MapStrokePatternOptions(
    patternOptions: StrokePatternType,
    onPolylinePatternSelect: (StrokePatternType) -> Unit
) {
    Column {
        MapTitle("Pattern Type")
        FlowRow(
            mainAxisSpacing = 12.dp,
            crossAxisSpacing = 6.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            StrokePatternType.values().forEach {
                OutlinedButton(onClick = {
                    onPolylinePatternSelect(it)
                }) {
                    AnimatedVisibility(visible = patternOptions == it) {
                        Row {
                            Icon(
                                Icons.Rounded.Check,
                                contentDescription = "",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.requiredWidth(8.dp))
                        }
                    }
                    Text(it.toString())
                }
            }
        }
        MapVerticalSpace()
    }
}

fun convertVectorToBitmap(context: Context, @DrawableRes id: Int, color: Color): BitmapDescriptor {
    val vectorDrawable: Drawable = ResourcesCompat.getDrawable(context.resources, id, null)
        ?: return BitmapDescriptorFactory.defaultMarker()
    val bitmap = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    DrawableCompat.setTint(vectorDrawable, color.toArgb())
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun getRandomColor(): Color {
    return Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
}