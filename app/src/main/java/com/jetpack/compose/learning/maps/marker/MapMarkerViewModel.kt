package com.jetpack.compose.learning.maps.marker

import androidx.lifecycle.ViewModel
import com.jetpack.compose.learning.maps.MarkerIconStyle
import com.jetpack.compose.learning.maps.MarkerStyle
import com.jetpack.compose.learning.maps.MarkerMapUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapMarkerViewModel : ViewModel() {
    private val _state = MutableStateFlow(MarkerMapUIState())
    val state: StateFlow<MarkerMapUIState>
        get() = _state

    fun changeMarkerType(markerStyle: MarkerStyle) {
        _state.value = _state.value.copy(markerStyle = markerStyle)
    }

    fun changeMarkerAlpha(alpha: Float) {
        _state.value = _state.value.copy(alpha = alpha)
    }

    fun setMarkerDraggable(draggable: Boolean) {
        _state.value = _state.value.copy(draggable = draggable)
    }

    fun setMarkerFlat(flat: Boolean) {
        _state.value = _state.value.copy(flat = flat)
    }

    fun setMarkerIconStyle(markerIconStyle: MarkerIconStyle) {
        _state.value = _state.value.copy(markerIconStyle = markerIconStyle)
    }

    fun setMarkerColorHue(colorHue: Float) {
        _state.value = _state.value.copy(markerHue = colorHue)
    }

    fun setMarkerRotation(rotation: Float) {
        _state.value = _state.value.copy(rotation = rotation)
    }

    fun setMarkerVisible(visible: Boolean) {
        _state.value = _state.value.copy(visible = visible)
    }
}
