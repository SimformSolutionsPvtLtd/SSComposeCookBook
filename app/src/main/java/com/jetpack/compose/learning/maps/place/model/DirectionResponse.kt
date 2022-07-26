package com.jetpack.compose.learning.maps.place.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

data class DirectionResponse(val status: String, val routes: List<RoutesItem>)

data class RoutesItem(
    val bounds: Bounds,
    val copyrights: String,
    val legs: List<LegsItem>,
    val summary: String,
    val overview_polyline: OverviewPolyline
) {
    fun getPlaceLatLngBound(): LatLngBounds {
        val northeast = LatLng(bounds.northeast.lat, bounds.northeast.lng)
        val southwest = LatLng(bounds.southwest.lat, bounds.southwest.lng)
        return LatLngBounds(southwest, northeast)
    }
}

data class Bounds(val southwest: Location, val northeast: Location)

data class LegsItem(
    val distance: TextValue?,
    val duration: TextValue?,
    val end_address: String,
    val end_location: Location,
    val start_address: String,
    val start_location: Location,
    val steps: List<StepsItem>
)

data class TextValue(val text: String, val value: Int)

data class StepsItem(
    val distance: TextValue?,
    val duration: TextValue,
    val end_location: Location,
    val html_instructions: String,
    val polyline: Polyline,
    val start_location: Location,
    val travel_mode: String,
    val maneuver: String?
)

data class Polyline(val points: String)

data class OverviewPolyline(val points: String)
