package com.jetpack.compose.learning.maps.place.component

sealed class MapPlaceScreens(var route: String, val title: String) {
    object Main : MapPlaceScreens("main", "Main")
    object PlacePicker : MapPlaceScreens("place_picker", "Place Picker")
}