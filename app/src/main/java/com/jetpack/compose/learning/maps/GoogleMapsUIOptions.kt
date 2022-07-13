package com.jetpack.compose.learning.maps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.maps.android.compose.MapType

//region - Basic Map Options
@Composable
fun GoogleMapUIOptions(
    uiState: BasicMapUiState,
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
        MapCheckBoxProperty(
            uiState.mapsProperties.isBuildingEnabled,
            "Buildings",
            onBuildingEnabled
        )
        MapCheckBoxProperty(
            uiState.mapsProperties.isIndoorEnabled,
            "Indoor",
            onIndoorEnabled
        )
        MapCheckBoxProperty(
            uiState.mapsProperties.isMyLocationEnabled,
            "My Location Layer (Require Permission)",
            onMyLocationEnabled
        )
        MapCheckBoxProperty(
            uiState.mapsProperties.isTrafficEnabled,
            "Traffic",
            onTrafficEnabled
        )
        MapCheckBoxProperty(
            uiState.mapUiSettings.compassEnabled,
            "Compass",
            onCompassEnabled
        )
        MapCheckBoxProperty(
            uiState.mapUiSettings.mapToolbarEnabled,
            "Map Toolbar",
            onMapToolBarEnabled
        )
        MapCheckBoxProperty(
            uiState.mapUiSettings.myLocationButtonEnabled,
            "My Location Button",
            onMyLocationButtonEnabled
        )
        MapCheckBoxProperty(
            uiState.mapUiSettings.rotationGesturesEnabled,
            "Rotation Gesture",
            onRotationGestureEnabled
        )
        MapCheckBoxProperty(
            uiState.mapUiSettings.tiltGesturesEnabled,
            "Tilt Gesture",
            onTiltGestureEnabled
        )
        MapCheckBoxProperty(
            uiState.mapUiSettings.zoomControlsEnabled,
            "Zoom Control",
            onZoomControlEnabled
        )
        MapCheckBoxProperty(
            uiState.mapUiSettings.zoomGesturesEnabled,
            "Zoom Gesture",
            onZoomGestureEnabled
        )
    }
}

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
@Composable
fun GoogleMapMarkerOptions(
    uiState: MarkerMapUiState,
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
        MapCheckBoxProperty(uiState.draggable, "Draggable", onMarkerDraggableChange)
        MapCheckBoxProperty(uiState.flat, "Flat", onMarkerFlatChange)
        MapCheckBoxProperty(uiState.visible, "Visible", onMarkerVisibilityChange)
    }
}

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
@Composable
fun GoogleMapPolylineOptions(
    uiState: PolyLineMapUiState,
    onMarkerDraggableChange: (Boolean) -> Unit,
    onMarkerVisibilityChange: (Boolean) -> Unit,
    onPolylineClickableChange: (Boolean) -> Unit,
    onPolylineColorChange: (Color) -> Unit,
    onPolylineStartCapChange: (PolyLineCap) -> Unit,
    onPolylineEndCapChange: (PolyLineCap) -> Unit,
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
        MapCheckBoxProperty(uiState.markerVisibility, "Marker Visible", onMarkerVisibilityChange)
        AnimatedVisibility(visible = uiState.markerVisibility) {
            MapCheckBoxProperty(
                uiState.markerDraggable,
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
        PolyLineCapOptions("Polyline Start Cap", uiState.polyLineStartCap, onPolylineStartCapChange)
        PolyLineCapOptions("Polyline End Cap", uiState.polyLineEndCap, onPolylineEndCapChange)
        MapCheckBoxProperty(uiState.geodesic, "Polyline Geodesic", onPolylineGeodesicChange)
    }
}

@Composable
fun PolyLineCapOptions(
    title: String,
    polylineCap: PolyLineCap,
    onPolyLineCapChange: (PolyLineCap) -> Unit
) {
    Column {
        MapTitle(title)
        FlowRow(
            mainAxisSpacing = 12.dp,
            crossAxisSpacing = 6.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            PolyLineCap.values().forEach {
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
@Composable
fun GoogleMapPolygonOptions(
    uiState: PolygonMapUiState,
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
        MapCheckBoxProperty(uiState.geodesic, "Geodesic", onGeodesicChange)
    }
}
//endregion Polygon Options

//region Circle Options
@Composable
fun GoogleMapCircleOptions(
    uiState: CircleMapUiState,
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
        MapCheckBoxProperty(uiState.markerVisibility, "Marker Visible", onMarkerVisibilityChange)
        AnimatedVisibility(visible = uiState.markerVisibility) {
            MapCheckBoxProperty(
                uiState.markerDraggable,
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