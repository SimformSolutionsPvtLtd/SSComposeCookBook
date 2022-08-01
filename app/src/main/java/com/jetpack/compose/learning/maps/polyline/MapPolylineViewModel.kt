package com.jetpack.compose.learning.maps.polyline

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.jetpack.compose.learning.maps.PolylineCap
import com.jetpack.compose.learning.maps.PolylineMapUIState
import com.jetpack.compose.learning.maps.StrokeJointType
import com.jetpack.compose.learning.maps.StrokePatternType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapPolylineViewModel : ViewModel() {
    private val _state = MutableStateFlow(PolylineMapUIState())
    val state: StateFlow<PolylineMapUIState>
        get() = _state

    fun setMarkerDraggable(draggable: Boolean) {
        _state.value = _state.value.copy(isMarkerDraggable = draggable)
    }

    fun setMarkerVisible(visible: Boolean) {
        _state.value = _state.value.copy(isMarkerVisible = visible)
    }

    fun setPolylineClickable(clickable: Boolean) {
        _state.value = _state.value.copy(clickable = clickable)
    }

    fun setPolylineColor(color: Color) {
        _state.value = _state.value.copy(strokeColor = color)
    }

    fun setPolylineStartCap(polylineCap: PolylineCap) {
        _state.value = _state.value.copy(polylineStartCap = polylineCap)
    }

    fun setPolylineEndCap(polylineCap: PolylineCap) {
        _state.value = _state.value.copy(polylineEndCap = polylineCap)
    }

    fun setPolylineGeodesic(geodesic: Boolean) {
        _state.value = _state.value.copy(geodesic = geodesic)
    }

    fun setPolylineJointType(jointType: StrokeJointType) {
        _state.value = _state.value.copy(jointType = jointType)
    }

    fun setPolylinePatternType(patternType: StrokePatternType) {
        _state.value = _state.value.copy(patternType = patternType)
    }

    fun setPolyLineVisible(visible: Boolean) {
        _state.value = _state.value.copy(visible = visible)
    }

    fun setPolyLineWidth(width: Float) {
        _state.value = _state.value.copy(strokeWidth = width)
    }

    fun setStrokeAlpha(alpha: Float) {
        _state.value = _state.value.copy(strokeColorAlpha = alpha)
    }
}
