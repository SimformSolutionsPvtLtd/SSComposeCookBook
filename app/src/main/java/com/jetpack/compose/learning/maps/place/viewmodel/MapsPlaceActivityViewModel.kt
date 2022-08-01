package com.jetpack.compose.learning.maps.place.viewmodel

import androidx.lifecycle.ViewModel
import com.jetpack.compose.learning.maps.place.model.PlaceResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapsPlaceActivityViewModel : ViewModel() {
    private val _placeResult = MutableStateFlow<PlaceResult?>(null)
    val placeResult: StateFlow<PlaceResult?>
        get() = _placeResult

    fun selectPlace(placeResult: PlaceResult) {
        _placeResult.value = placeResult
    }
}
