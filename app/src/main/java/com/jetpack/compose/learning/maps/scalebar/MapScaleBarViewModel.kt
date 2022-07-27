package com.jetpack.compose.learning.maps.scalebar

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.jetpack.compose.learning.maps.ScaleBarMapUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapScaleBarViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScaleBarMapUIState())
    val state: StateFlow<ScaleBarMapUIState>
        get() = _state

    fun setLineColor(color: Color) {
        _state.value = _state.value.copy(lineColor = color)
    }

    fun setTextColor(color: Color) {
        _state.value = _state.value.copy(textColor = color)
    }

    fun setShadowColor(color: Color) {
        _state.value = _state.value.copy(shadowColor = color)
    }
}