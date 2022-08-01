package com.jetpack.compose.learning.maps.overlay

import androidx.lifecycle.ViewModel
import com.jetpack.compose.learning.data.DataProvider
import com.jetpack.compose.learning.maps.GroundOverlayMapUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapGroundOverlayViewModel : ViewModel() {
    private val imageList = DataProvider.getGroundOverlayImages()
    private var currentImageIndex = 0
    private val _state =
        MutableStateFlow(GroundOverlayMapUIState(imageResource = imageList[currentImageIndex]))
    val state: StateFlow<GroundOverlayMapUIState>
        get() = _state

    fun nextImage() {
        currentImageIndex += 1
        if (currentImageIndex >= imageList.size) {
            currentImageIndex = 0
        }
        _state.value = _state.value.copy(imageResource = imageList[currentImageIndex])
    }

    fun previousImage() {
        currentImageIndex -= 1
        if (currentImageIndex < 0) {
            currentImageIndex = imageList.size - 1
        }
        _state.value = _state.value.copy(imageResource = imageList[currentImageIndex])
    }

    fun setBearing(bearing: Float) {
        _state.value = _state.value.copy(bearing = bearing)
    }

    fun setTransparency(transparency: Float) {
        _state.value = _state.value.copy(transparency = transparency)
    }

    fun setClickable(clickable: Boolean) {
        _state.value = _state.value.copy(clickable = clickable)
    }

    fun setVisible(visible: Boolean) {
        _state.value = _state.value.copy(visible = visible)
    }
}