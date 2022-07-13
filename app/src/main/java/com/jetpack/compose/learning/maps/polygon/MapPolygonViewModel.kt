package com.jetpack.compose.learning.maps.polygon

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.jetpack.compose.learning.maps.PolygonMapUIState
import com.jetpack.compose.learning.maps.StrokeJointType
import com.jetpack.compose.learning.maps.StrokePatternType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapPolygonViewModel : ViewModel() {
    private val _state = MutableStateFlow(PolygonMapUIState())
    val state: StateFlow<PolygonMapUIState>
        get() = _state

    fun setPolygonClickable(clickable: Boolean) {
        _state.value = _state.value.copy(clickable = clickable)
    }

    fun setStrokeColor(color: Color) {
        _state.value = _state.value.copy(strokeColor = color)
    }

    fun setFillColor(color: Color) {
        _state.value = _state.value.copy(fillColor = color)
    }

    fun setFillColorAlpha(alpha: Float) {
        _state.value = _state.value.copy(fillColorAlpha = alpha)
    }

    fun setGeodesic(geodesic: Boolean) {
        _state.value = _state.value.copy(geodesic = geodesic)
    }

    fun setJointType(jointType: StrokeJointType) {
        _state.value = _state.value.copy(jointType = jointType)
    }

    fun setPatternType(patternType: StrokePatternType) {
        _state.value = _state.value.copy(patternType = patternType)
    }

    fun setVisible(visible: Boolean) {
        _state.value = _state.value.copy(visible = visible)
    }

    fun setStrokeWidth(width: Float) {
        _state.value = _state.value.copy(strokeWidth = width)
    }

    fun setStrokeAlpha(alpha: Float) {
        _state.value = _state.value.copy(strokeColorAlpha = alpha)
    }
}
