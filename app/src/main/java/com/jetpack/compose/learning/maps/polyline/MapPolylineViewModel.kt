package com.jetpack.compose.learning.maps.polyline

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.jetpack.compose.learning.maps.PolyLineCap
import com.jetpack.compose.learning.maps.PolyLineMapUiState
import com.jetpack.compose.learning.maps.StrokeJointType
import com.jetpack.compose.learning.maps.StrokePatternType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapPolylineViewModel : ViewModel() {

    private val _state = MutableStateFlow(PolyLineMapUiState())

    val state: StateFlow<PolyLineMapUiState>
        get() = _state

    fun setMarkerDraggable(draggable: Boolean) {
        _state.value = _state.value.copy(markerDraggable = draggable)
    }

    fun setMarkerVisible(visible: Boolean) {
        _state.value = _state.value.copy(markerVisibility = visible)
    }

    fun setPolylineClickable(clickable: Boolean) {
        _state.value = _state.value.copy(clickable = clickable)
    }

    fun setPolylineColor(color: Color) {
        _state.value = _state.value.copy(strokeColor = color)
    }

    fun setPolylineStartCap(polyLineCap: PolyLineCap) {
        _state.value = _state.value.copy(polyLineStartCap = polyLineCap)
    }

    fun setPolylineEndCap(polyLineCap: PolyLineCap) {
        _state.value = _state.value.copy(polyLineEndCap = polyLineCap)
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
        _state.value = _state.value.copy(visibility = visible)
    }

    fun setPolyLineWidth(width: Float) {
        _state.value = _state.value.copy(strokeWidth = width)
    }

    fun setStrokeAlpha(alpha: Float) {
        _state.value = _state.value.copy(strokeColorAlpha = alpha)
    }
}