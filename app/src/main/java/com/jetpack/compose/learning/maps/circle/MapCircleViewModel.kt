package com.jetpack.compose.learning.maps.circle

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.jetpack.compose.learning.maps.CircleMapUiState
import com.jetpack.compose.learning.maps.StrokeJointType
import com.jetpack.compose.learning.maps.StrokePatternType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapCircleViewModel : ViewModel() {

    private val _state = MutableStateFlow(CircleMapUiState())

    val state: StateFlow<CircleMapUiState>
        get() = _state

    fun setMarkerDraggable(draggable: Boolean) {
        _state.value = _state.value.copy(markerDraggable = draggable)
    }

    fun setMarkerVisible(visible: Boolean) {
        _state.value = _state.value.copy(markerVisibility = visible)
    }

    fun setClickable(clickable: Boolean) {
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

    fun setRadius(radius: Float) {
        _state.value = _state.value.copy(radius = radius)
    }

    fun setStrokeAlpha(alpha: Float) {
        _state.value = _state.value.copy(strokeColorAlpha = alpha)
    }

    fun setJointType(jointType: StrokeJointType) {
        _state.value = _state.value.copy(jointType = jointType)
    }

    fun setPatternType(patternType: StrokePatternType) {
        _state.value = _state.value.copy(patternType = patternType)
    }

    fun setVisible(visible: Boolean) {
        _state.value = _state.value.copy(visibility = visible)
    }

    fun setStrokeWidth(width: Float) {
        _state.value = _state.value.copy(strokeWidth = width)
    }
}