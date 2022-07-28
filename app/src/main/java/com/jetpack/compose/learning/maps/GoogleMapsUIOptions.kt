package com.jetpack.compose.learning.maps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.maps.android.compose.MapType

//region - Basic Map Options
/**
 * View for map UI options and map properties options.
 */
@Composable
fun GoogleMapUIOptions(
    uiState: BasicMapUIState,
    onMapTypeSelect: (MapType) -> Unit,
    onMapStyleSelect: (MapStyle) -> Unit,
    onBuildingEnabled: (Boolean) -> Unit,
    onIndoorEnabled: (Boolean) -> Unit,
    onMyLocationEnabled: (Boolean) -> Unit,
    onTrafficEnabled: (Boolean) -> Unit,
    onCompassEnabled: (Boolean) -> Unit,
    onMapToolBarEnabled: (Boolean) -> Unit,
    onMyLocationButtonEnabled: (Boolean) -> Unit,
    onRotationGestureEnabled: (Boolean) -> Unit,
    onTiltGestureEnabled: (Boolean) -> Unit,
    onZoomControlEnabled: (Boolean) -> Unit,
    onZoomGestureEnabled: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        MapVerticalSpace()
        MapTypeOptions(
            uiState.mapsProperties.mapType,
            onMapTypeSelect
        )
        AnimatedVisibility(visible = uiState.mapsProperties.mapType == MapType.NORMAL) {
            MapStyleOptions(
                uiState.mapStyle,
                onMapStyleSelect
            )
        }
        MapSwitchProperty(
            uiState.mapsProperties.isBuildingEnabled,
            "Buildings",
            onBuildingEnabled
        )
        MapSwitchProperty(
            uiState.mapsProperties.isIndoorEnabled,
            "Indoor",
            onIndoorEnabled
        )
        MapSwitchProperty(
            uiState.mapsProperties.isMyLocationEnabled,
            "My Location Layer (Require Permission)",
            onMyLocationEnabled
        )
        MapSwitchProperty(
            uiState.mapsProperties.isTrafficEnabled,
            "Traffic",
            onTrafficEnabled
        )
        MapSwitchProperty(
            uiState.mapUiSettings.compassEnabled,
            "Compass",
            onCompassEnabled
        )
        MapSwitchProperty(
            uiState.mapUiSettings.mapToolbarEnabled,
            "Map Toolbar",
            onMapToolBarEnabled
        )
        MapSwitchProperty(
            uiState.mapUiSettings.myLocationButtonEnabled,
            "My Location Button",
            onMyLocationButtonEnabled
        )
        MapSwitchProperty(
            uiState.mapUiSettings.rotationGesturesEnabled,
            "Rotation Gesture",
            onRotationGestureEnabled
        )
        MapSwitchProperty(
            uiState.mapUiSettings.tiltGesturesEnabled,
            "Tilt Gesture",
            onTiltGestureEnabled
        )
        MapSwitchProperty(
            uiState.mapUiSettings.zoomControlsEnabled,
            "Zoom Control",
            onZoomControlEnabled
        )
        MapSwitchProperty(
            uiState.mapUiSettings.zoomGesturesEnabled,
            "Zoom Gesture",
            onZoomGestureEnabled
        )
    }
}

/**
 * View for different map type options.
 * It includes NONE, NORMAL, SATELLITE, TERRAIN, and HYBRID map type.
 */
@Composable
fun MapTypeOptions(
    mapType: MapType,
    onMapTypeSelect: (MapType) -> Unit
) {
    Column {
        MapTitle("Map Type")
        FlowRow(
            mainAxisSpacing = 12.dp,
            crossAxisSpacing = 6.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            MapType.values().forEach {
                OutlinedButton(onClick = {
                    onMapTypeSelect(it)
                }) {
                    AnimatedVisibility(visible = mapType == it) {
                        Row {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = "",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.requiredWidth(8.dp))
                        }
                    }
                    Text(it.name)
                }
            }
        }
        MapVerticalSpace()
    }
}

/**
 * View for different map style options.
 * This options changes the theme for the map.
 * It includes NORMAL, DARK and NIGHT mode.
 * You can also create your style from [Map Styling Wizard](https://mapstyle.withgoogle.com/)
 */
@Composable
fun MapStyleOptions(
    mapStyle: MapStyle,
    onMapStyleSelect: (MapStyle) -> Unit
) {
    Column {
        MapTitle("Map Style")
        FlowRow(
            mainAxisSpacing = 12.dp,
            crossAxisSpacing = 6.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            MapStyle.values().forEach {
                OutlinedButton(onClick = {
                    onMapStyleSelect(it)
                }) {
                    AnimatedVisibility(visible = mapStyle == it) {
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
                    Text(it.name)
                }
            }
        }
        MapVerticalSpace()
    }
}
//endregion - Basic Map Options

//region Marker Options
/**
 * View for different marker style and properties options.
 * Only MarkerStyleOptions will be applied on new markers.
 */
@Composable
fun GoogleMapMarkerOptions(
    uiState: MarkerMapUIState,
    onMarkerStyleSelect: (MarkerStyle) -> Unit,
    onMarkerIconStyleSelect: (MarkerIconStyle) -> Unit,
    onMarkerHueChange: (Float) -> Unit,
    onMarkerAlphaChange: (Float) -> Unit,
    onMarkerRotationChange: (Float) -> Unit,
    onMarkerDraggableChange: (Boolean) -> Unit,
    onMarkerFlatChange: (Boolean) -> Unit,
    onMarkerVisibilityChange: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        MapVerticalSpace()
        MarkerStyleOptions(uiState.markerStyle, onMarkerStyleSelect)
        MarkerIconOptions(uiState.markerIconStyle, onMarkerIconStyleSelect)
        AnimatedVisibility(visible = uiState.markerIconStyle != MarkerIconStyle.IMAGE) {
            MapSliderProperty(uiState.markerHue, "Marker Color Hue", onMarkerHueChange)
        }
        MapSliderProperty(uiState.alpha, "Alpha", onMarkerAlphaChange, 0f, 1f)
        MapSliderProperty(uiState.rotation, "Rotation", onMarkerRotationChange)
        MapSwitchProperty(uiState.draggable, "Draggable", onMarkerDraggableChange)
        MapSwitchProperty(uiState.flat, "Flat", onMarkerFlatChange)
        MapSwitchProperty(uiState.visible, "Visible", onMarkerVisibilityChange)
    }
}

/**
 * View for different marker icon options.
 * It includes DEFAULT, IMAGE and VECTOR icon type for the marker.
 */
@Composable
fun MarkerIconOptions(
    markerIconStyle: MarkerIconStyle,
    onMarkerIconStyle: (MarkerIconStyle) -> Unit
) {
    Column {
        MapTitle("Marker Icon")
        FlowRow(
            mainAxisSpacing = 12.dp,
            crossAxisSpacing = 6.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            MarkerIconStyle.values().forEach {
                OutlinedButton(onClick = {
                    onMarkerIconStyle(it)
                }) {
                    AnimatedVisibility(visible = markerIconStyle == it) {
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
                    Text(it.name)
                }
            }
        }
        MapVerticalSpace()
    }
}

/**
 * View for different marker info window options.
 * It includes DEFAULT, CUSTOM_INFO_WINDOW_CONTENT, and CUSTOM_INFO_WINDOW.
 */
@Composable
fun MarkerStyleOptions(
    markerStyle: MarkerStyle,
    onMarkerStyleSelect: (MarkerStyle) -> Unit
) {
    Column {
        MapTitle("Marker Style (Will be applied in new markers)")
        FlowRow(
            mainAxisSpacing = 12.dp,
            crossAxisSpacing = 6.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            MarkerStyle.values().forEach {
                OutlinedButton(onClick = {
                    onMarkerStyleSelect(it)
                }) {
                    AnimatedVisibility(visible = markerStyle == it) {
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
//endregion Marker Options

//region Polyline Options
/**
 * View for different polyline style and properties options.
 */
@Composable
fun GoogleMapPolylineOptions(
    uiState: PolylineMapUIState,
    onMarkerDraggableChange: (Boolean) -> Unit,
    onMarkerVisibilityChange: (Boolean) -> Unit,
    onPolylineClickableChange: (Boolean) -> Unit,
    onPolylineColorChange: (Color) -> Unit,
    onPolylineStartCapChange: (PolylineCap) -> Unit,
    onPolylineEndCapChange: (PolylineCap) -> Unit,
    onPolylineGeodesicChange: (Boolean) -> Unit,
    onPolylineJointTypeChange: (StrokeJointType) -> Unit,
    onPolylinePatternChange: (StrokePatternType) -> Unit,
    onPolylineVisibilityChange: (Boolean) -> Unit,
    onPolylineWidthChange: (Float) -> Unit,
    onStrokeAlphaChange: (Float) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        MapVerticalSpace()
        MapSwitchProperty(uiState.isMarkerVisible, "Marker Visible", onMarkerVisibilityChange)
        AnimatedVisibility(visible = uiState.isMarkerVisible) {
            MapSwitchProperty(
                uiState.isMarkerDraggable,
                "Marker Draggable",
                onMarkerDraggableChange
            )
        }
        GoogleMapCommonShapeOptions(
            uiState = uiState,
            onStrokeColorChange = onPolylineColorChange,
            onPatternChange = onPolylinePatternChange,
            onVisibilityChange = onPolylineVisibilityChange,
            onWidthChange = onPolylineWidthChange,
            onClickableChange = onPolylineClickableChange,
            onJointTypeChange = onPolylineJointTypeChange,
            onStrokeColorAlphaChange = onStrokeAlphaChange
        )
        PolyLineCapOptions("Polyline Start Cap", uiState.polylineStartCap, onPolylineStartCapChange)
        PolyLineCapOptions("Polyline End Cap", uiState.polylineEndCap, onPolylineEndCapChange)
        MapSwitchProperty(uiState.geodesic, "Polyline Geodesic", onPolylineGeodesicChange)
    }
}

/**
 * View for different polyline cap options.
 * Includes options BUTT, ROUND, SQUARE and CUSTOM.
 * You can set the start and end cap for the polyline.
 * The default cap for start and end is ButtCap.
 */
@Composable
fun PolyLineCapOptions(
    title: String,
    polylineCap: PolylineCap,
    onPolyLineCapChange: (PolylineCap) -> Unit
) {
    Column {
        MapTitle(title)
        FlowRow(
            mainAxisSpacing = 12.dp,
            crossAxisSpacing = 6.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            PolylineCap.values().forEach {
                OutlinedButton(onClick = {
                    onPolyLineCapChange(it)
                }) {
                    AnimatedVisibility(visible = polylineCap == it) {
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
                    Text(it.name)
                }
            }
        }
        MapVerticalSpace()
    }
}
//endregion Polyline Options

//region Polygon Options
/**
 * View for different polygon style and properties options.
 */
@Composable
fun GoogleMapPolygonOptions(
    uiState: PolygonMapUIState,
    onFillColorChange: (Color) -> Unit,
    onFillColorAlpha: (Float) -> Unit,
    onGeodesicChange: (Boolean) -> Unit,
    onClickableChange: (Boolean) -> Unit,
    onStrokeColorChange: (Color) -> Unit,
    onJointTypeChange: (StrokeJointType) -> Unit,
    onPatternChange: (StrokePatternType) -> Unit,
    onVisibilityChange: (Boolean) -> Unit,
    onStrokeWidthChange: (Float) -> Unit,
    onStrokeAlphaChange: (Float) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        MapVerticalSpace()
        MapColorOptions(
            title = "Fill Color",
            color = uiState.fillColor,
            onColorSelect = onFillColorChange
        )
        MapSliderProperty(
            value = uiState.fillColorAlpha,
            title = "Fill Color Alpha",
            onValueChange = onFillColorAlpha,
            min = 0f,
            max = 1f
        )
        GoogleMapCommonShapeOptions(
            uiState = uiState,
            onStrokeColorChange = onStrokeColorChange,
            onPatternChange = onPatternChange,
            onVisibilityChange = onVisibilityChange,
            onWidthChange = onStrokeWidthChange,
            onClickableChange = onClickableChange,
            onJointTypeChange = onJointTypeChange,
            onStrokeColorAlphaChange = onStrokeAlphaChange
        )
        MapSwitchProperty(uiState.geodesic, "Geodesic", onGeodesicChange)
    }
}
//endregion Polygon Options

//region Circle Options
/**
 * View for different circle style and properties options.
 */
@Composable
fun GoogleMapCircleOptions(
    uiState: CircleMapUIState,
    onMarkerDraggableChange: (Boolean) -> Unit,
    onMarkerVisibilityChange: (Boolean) -> Unit,
    onFillColorChange: (Color) -> Unit,
    onFillColorAlpha: (Float) -> Unit,
    onRadiusChange: (Float) -> Unit,
    onClickableChange: (Boolean) -> Unit,
    onStrokeColorChange: (Color) -> Unit,
    onPatternChange: (StrokePatternType) -> Unit,
    onVisibilityChange: (Boolean) -> Unit,
    onStrokeWidthChange: (Float) -> Unit,
    onStrokeAlphaChange: (Float) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        MapVerticalSpace()
        MapSwitchProperty(uiState.isMarkerVisible, "Marker Visible", onMarkerVisibilityChange)
        AnimatedVisibility(visible = uiState.isMarkerVisible) {
            MapSwitchProperty(
                uiState.isMarkerDraggable,
                "Marker Draggable",
                onMarkerDraggableChange
            )
        }
        MapSliderProperty(
            value = uiState.radius,
            title = "Radius",
            onValueChange = onRadiusChange,
            min = 25f,
            max = 75f
        )
        MapColorOptions(
            title = "Fill Color",
            color = uiState.fillColor,
            onColorSelect = onFillColorChange
        )
        MapSliderProperty(
            value = uiState.fillColorAlpha,
            title = "Fill Color Alpha",
            onValueChange = onFillColorAlpha,
            min = 0f,
            max = 1f
        )
        GoogleMapCommonShapeOptions(
            uiState = uiState,
            onStrokeColorChange = onStrokeColorChange,
            onPatternChange = onPatternChange,
            onVisibilityChange = onVisibilityChange,
            onWidthChange = onStrokeWidthChange,
            onClickableChange = onClickableChange,
            onStrokeColorAlphaChange = onStrokeAlphaChange
        )
    }
}
//endregion Circle Options

//region Overlay Options
/**
 * View for different ground overlay style and properties options.
 */
@Composable
fun GoogleMapGroundOverlayOptions(
    uiState: GroundOverlayMapUIState,
    onNextImageChange: () -> Unit,
    onPreviousImageChange: () -> Unit,
    onBearingChange: (Float) -> Unit,
    onTransparencyChange: (Float) -> Unit,
    onClickableChange: (Boolean) -> Unit,
    onVisibilityChange: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MapVerticalSpace()
        Row {
            OutlinedButton(onClick = onNextImageChange) {
                Text("Next Image")
            }
            Spacer(modifier = Modifier.requiredWidth(10.dp))
            OutlinedButton(onClick = onPreviousImageChange) {
                Text("Previous Image")
            }
        }
        MapVerticalSpace()
        MapSliderProperty(uiState.bearing, "Bearing", onBearingChange)
        MapSliderProperty(uiState.transparency, "Transparency", onTransparencyChange, max = 1f)
        MapSwitchProperty(uiState.visible, "Visible", onVisibilityChange)
        MapSwitchProperty(uiState.clickable, "Clickable", onClickableChange)
    }
}

/**
 * View for different tile overlay style and properties options.
 */
@Composable
fun GoogleMapTileOverlayOptions(
    uiState: TileOverlayMapUIState,
    onTransparencyChange: (Float) -> Unit,
    onFadeInChange: (Boolean) -> Unit,
    onVisibilityChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MapVerticalSpace()
        MapSliderProperty(uiState.transparency, "Transparency", onTransparencyChange, max = 1f)
        MapSwitchProperty(uiState.fadeIn, "Fade In", onFadeInChange)
        MapSwitchProperty(uiState.visible, "Visible", onVisibilityChange)
    }
}
//endregion Overlay Options

//region Navigation Viewer options
/**
 * View for navigation viewer. User can change the route related options from this view.
 */
@Composable
fun GoogleMapNavigationViewerOptions(
    uiState: NavigationViewerUIState,
    onShowAllRouteChange: (Boolean) -> Unit,
    onNextRouteClick: () -> Unit,
    onPreviousRouteClick: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        MapVerticalSpace()
        MapSwitchProperty(
            checked = uiState.showAllRoutes,
            title = "Show All Routes",
            onCheckedChange = onShowAllRouteChange
        )
        AnimatedVisibility(visible = !uiState.showAllRoutes) {
            Row {
                OutlinedButton(onClick = onPreviousRouteClick, modifier = Modifier.weight(1f)) {
                    Text("Previous Route")
                }
                Spacer(modifier = Modifier.requiredWidth(15.dp))
                OutlinedButton(onClick = onNextRouteClick, modifier = Modifier.weight(1f)) {
                    Text("Next Route")
                }
            }
        }
        MapVerticalSpace()
    }
}
//endregion Navigation Viewer options

//region Heat Map options
/**
 * View for different heat map style and properties options.
 */
@Composable
fun GoogleMapHeatMapOptions(
    uiState: TileOverlayMapUIState,
    onTransparencyChange: (Float) -> Unit,
    onFadeInChange: (Boolean) -> Unit,
    onVisibilityChange: (Boolean) -> Unit,
    onHeatMapRadiusChange: ((Float) -> Unit),
    onHeatMapColorChange: ((List<Color>) -> Unit),
    onHeatMapOpacityChange: ((Float) -> Unit)
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoogleMapTileOverlayOptions(
            uiState,
            onTransparencyChange,
            onFadeInChange,
            onVisibilityChange
        )
        MapSliderProperty(uiState.heatMapRadius, "Heatmap Radius", onHeatMapRadiusChange, 10f, 50f)
        OutlinedButton(onClick = {
            onHeatMapColorChange(
                listOf(
                    getRandomColor(),
                    getRandomColor(),
                    getRandomColor()
                )
            )
        }) {
            Text("Change Colors")
        }
        MapVerticalSpace()
        MapSliderProperty(uiState.heatMapOpacity, "Opacity", onHeatMapOpacityChange, max = 1f)
    }
}
//endregion Heat Map options

//region Scale Bar Options
/**
 * View for different scale bar styles.
 */
@Composable
fun GoogleMapScaleBarOptions(
    uiState: ScaleBarMapUIState,
    onTextColorChange: (Color) -> Unit,
    onLineColorChange: (Color) -> Unit,
    onShadowColorAlpha: (Color) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        MapVerticalSpace()
        MapColorOptions(
            title = "Text Color",
            color = uiState.textColor,
            onColorSelect = onTextColorChange
        )
        MapColorOptions(
            title = "Line Color",
            color = uiState.lineColor,
            onColorSelect = onLineColorChange
        )
        MapColorOptions(
            title = "Shadow Color",
            color = uiState.shadowColor,
            onColorSelect = onShadowColorAlpha
        )
    }
}
//endregion Scale Bar Options
