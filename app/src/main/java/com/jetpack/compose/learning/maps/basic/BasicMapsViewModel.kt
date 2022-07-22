package com.jetpack.compose.learning.maps.basic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.maps.MapStyle
import com.jetpack.compose.learning.maps.BasicMapUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class BasicMapsViewModel(application: Application) : AndroidViewModel(application) {
    //Map Properties
    private val mapsProperties = MutableStateFlow(MapProperties())
    private val mapsUiSettings = MutableStateFlow(MapUiSettings())
    private val mapStyle = MutableStateFlow(MapStyle.NORMAL)
    private val myLocationLayerEnabled = MutableStateFlow(false)

    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow(BasicMapUIState())
    val state: StateFlow<BasicMapUIState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                mapsProperties,
                mapsUiSettings,
                mapStyle
            ) { mapProperties, mapUiSettings, mapStyle ->
                val resourceId = when (mapStyle) {
                    MapStyle.NORMAL -> R.raw.map_normal_mode
                    MapStyle.DARK -> R.raw.map_dark_mode
                    MapStyle.NIGHT -> R.raw.map_night_mode
                }
                val mapStyleOptions =
                    MapStyleOptions.loadRawResourceStyle(
                        (getApplication() as Application),
                        resourceId
                    )
                val properties = mapProperties.copy(mapStyleOptions = mapStyleOptions)
                BasicMapUIState(properties, mapUiSettings, mapStyle)
            }.collect {
                _state.value = it
            }
        }
    }

    fun changeMapType(mapType: MapType) {
        mapsProperties.value = mapsProperties.value.copy(mapType = mapType)
    }

    fun changeMapStyle(mapStyle: MapStyle) {
        this.mapStyle.value = mapStyle
    }

    fun setBuildingEnable(enable: Boolean) {
        mapsProperties.value = mapsProperties.value.copy(isBuildingEnabled = enable)
    }

    fun setIndoorEnable(enable: Boolean) {
        mapsProperties.value = mapsProperties.value.copy(isIndoorEnabled = enable)
    }

    fun setMyLocationLayerEnable(enable: Boolean) {
        myLocationLayerEnabled.value = enable
    }

    fun updateMyLocationLayer() {
        mapsProperties.value =
            mapsProperties.value.copy(isMyLocationEnabled = myLocationLayerEnabled.value)
    }

    fun setTrafficEnable(enable: Boolean) {
        mapsProperties.value = mapsProperties.value.copy(isTrafficEnabled = enable)
    }

    fun setCompassEnable(enable: Boolean) {
        mapsUiSettings.value = mapsUiSettings.value.copy(compassEnabled = enable)
    }

    fun setMapToolBarEnable(enable: Boolean) {
        mapsUiSettings.value = mapsUiSettings.value.copy(mapToolbarEnabled = enable)
    }

    fun setMyLocationButtonEnable(enable: Boolean) {
        mapsUiSettings.value = mapsUiSettings.value.copy(myLocationButtonEnabled = enable)
    }

    fun setRotationGestureEnable(enable: Boolean) {
        mapsUiSettings.value = mapsUiSettings.value.copy(rotationGesturesEnabled = enable)
    }

    fun setTiltGestureEnable(enable: Boolean) {
        mapsUiSettings.value = mapsUiSettings.value.copy(tiltGesturesEnabled = enable)
    }

    fun setZoomControlEnable(enable: Boolean) {
        mapsUiSettings.value = mapsUiSettings.value.copy(zoomControlsEnabled = enable)
    }

    fun setZoomGestureEnable(enable: Boolean) {
        mapsUiSettings.value = mapsUiSettings.value.copy(zoomGesturesEnabled = enable)
    }
}
