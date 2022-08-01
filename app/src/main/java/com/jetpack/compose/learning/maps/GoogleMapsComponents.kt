package com.jetpack.compose.learning.maps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.android.gms.maps.model.LatLng
import com.jetpack.compose.learning.data.DataProvider
import kotlinx.coroutines.launch

val currentMarkerLatLong = LatLng(40.689247, -74.044502)

/**
 * Common screen for maps examples.
 * The @param[sheetContent] and @param[mainContent] will be different for each screen.
 * The toolbar contains one common action item, which is used to expand the bottom sheet.
 * @param[actionItems] can be used to add extra action items in toolbar.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    title: String,
    onBackPressed: () -> Unit,
    showLoading: Boolean = true,
    actionItems: @Composable RowScope.() -> Unit = {},
    sheetContent: @Composable ColumnScope.() -> Unit,
    mainContent: @Composable ColumnScope.() -> Unit,
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetContent = sheetContent,
    ) {
        MapScaffold(
            title = title,
            onBackPressed = onBackPressed,
            showLoading = showLoading,
            actionItems = {
                IconButton(onClick = {
                    coroutineScope.launch {
                        if (modalBottomSheetState.isVisible) {
                            modalBottomSheetState.hide()
                        } else {
                            modalBottomSheetState.show()
                        }
                    }
                }) {
                    Icon(Icons.Filled.Tune, contentDescription = "UI Option")
                }
                actionItems()
            },
            mainContent = mainContent,
        )
    }
}

/**
 * Scaffold wrapper for map screens.
 * The @param[mainContent] will be different for each screen.
 * The @param[actionItems] can be used to display action items in toolbar.
 */
@Composable
@OptIn(ExperimentalAnimationApi::class)
fun MapScaffold(
    title: String,
    onBackPressed: () -> Unit,
    showLoading: Boolean = true,
    actionItems: @Composable RowScope.() -> Unit = {},
    mainContent: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = actionItems
            )
        },
    ) {
        Box(modifier = Modifier.padding(it), contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.fillMaxSize(), content = mainContent)
            AnimatedVisibility(
                visible = showLoading,
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colors.background),
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                CircularProgressIndicator(modifier = Modifier.wrapContentSize())
            }
        }
    }
}

@Composable
fun MapSwitchProperty(checked: Boolean, title: String, onCheckedChange: (Boolean) -> Unit) {
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

/**
 * Common color options view for different shape related properties
 */
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
            DataProvider.getMapColors().forEach {
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

/**
 * Common shape control for maps.
 * It includes stroke color, pattern, shape visibility, stroke width, shape clickable,
 * stroke joint type and stroke color alpha.
 */
@Composable
fun GoogleMapCommonShapeOptions(
    modifier: Modifier = Modifier,
    uiState: CommonShapeUIState,
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
        if (onJointTypeChange != null) {
            MapStrokeJointOptions(uiState.jointType, onJointTypeChange)
        }
        MapStrokePatternOptions(uiState.patternType, onPatternChange)
        MapSwitchProperty(uiState.visible, "Visible", onVisibilityChange)
        MapSwitchProperty(uiState.clickable, "Clickable", onClickableChange)
    }
}

/**
 * Stroke Joint options for the shapes.
 * Include types - BEVEL, DEFAULT, ROUND
 */
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

/**
 * Stroke pattern options for the shapes.
 * Includes types - SOLID, DASH, DOT, GAP and MIX
 */
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
