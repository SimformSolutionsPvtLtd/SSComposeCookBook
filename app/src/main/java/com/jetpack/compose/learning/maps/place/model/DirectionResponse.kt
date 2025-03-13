package com.jetpack.compose.learning.maps.place.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.gson.annotations.SerializedName

data class DirectionResponse(val status: String, val routes: List<RoutesItem>)

data class RoutesItem(
    val bounds: Bounds,
    val copyrights: String,
    val legs: List<LegsItem>,
    val summary: String,
    @SerializedName("overview_polyline")
    val overviewPolyline: OverviewPolyline
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
    @SerializedName("end_address")
    val endAddress: String,
    @SerializedName("end_location")
    val endLocation: Location,
    @SerializedName("start_address")
    val startAddress: String,
    @SerializedName("start_location")
    val startLocation: Location,
    val steps: List<StepsItem>
)

data class TextValue(val text: String, val value: Int)

data class StepsItem(
    val distance: TextValue?,
    val duration: TextValue,
    @SerializedName("end_location")
    val endLocation: Location,
    @SerializedName("html_instructions")
    val htmlInstructions: String,
    val polyline: Polyline,
    @SerializedName("start_location")
    val startLocation: Location,
    @SerializedName("travel_mode")
    val travelMode: String,
    val maneuver: String?
)

data class Polyline(val points: String)

data class OverviewPolyline(val points: String)
