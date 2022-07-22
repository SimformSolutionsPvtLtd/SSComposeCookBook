package com.jetpack.compose.learning.maps.overlay

import androidx.lifecycle.ViewModel
import com.jetpack.compose.learning.maps.TileOverlayMapUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapTileOverlayViewModel : ViewModel() {
    private val _state = MutableStateFlow(TileOverlayMapUIState())
    val state: StateFlow<TileOverlayMapUIState>
        get() = _state

    fun setTransparency(transparency: Float) {
        _state.value = _state.value.copy(transparency = transparency)
    }

    fun setFadeIn(fadeIn: Boolean) {
        _state.value = _state.value.copy(fadeIn = fadeIn)
    }

    fun setVisible(visible: Boolean) {
        _state.value = _state.value.copy(visible = visible)
    }
}