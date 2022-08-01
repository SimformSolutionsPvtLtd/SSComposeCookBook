package com.jetpack.compose.learning.maps.place.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.jetpack.compose.learning.BuildConfig

data class PlaceResponse(val result: PlaceDetail?, val status: String)

data class PlaceDetail(
    val utcOffset: Int,
    val formatted_address: String?,
    val types: List<String>?,
    val icon: String,
    val icon_background_color: String,
    val address_components: List<AddressComponentsItem>?,
    val photos: List<PhotosItem>?,
    val url: String,
    val reference: String,
    val name: String,
    val geometry: Geometry,
    val icon_mask_base_uri: String,
    val vicinity: String,
    val adr_address: String,
    val place_id: String,
    val rating: Float?,
    val reviews: List<Reviews>?,
    val user_ratings_total: Int?,
) {
    fun getLocation() = geometry.location.getLatLng()

    fun getLatLngBound(): LatLngBounds {
        val viewport = geometry.viewport
        val northeast = LatLng(viewport.northeast.lat, viewport.northeast.lng)
        val southwest = LatLng(viewport.southwest.lat, viewport.southwest.lng)
        return LatLngBounds(southwest, northeast)
    }

    fun getAddress(): String {
        if (formatted_address == null) {
            return name
        }
        if (address_components.isNullOrEmpty()) {
            return "$name $formatted_address"
        }
        val placeCodes = address_components.filter { it.types.contains("plus_code") }
            .map { Pair(it.long_name, it.short_name) }
        var address = "$name $formatted_address"
        placeCodes.forEach {
            if (address.contains(it.first))
                address = address.replace(it.first, "")
            if (address.contains(it.second))
                address = address.replace(it.second, "")
        }
        return address
    }

    fun getPlaceResult(): PlaceResult {
        return PlaceResult(getAddress(), geometry, place_id)
    }
}

data class Geometry(val viewport: Viewport, val location: Location)

data class Location(val lng: Double, val lat: Double) {
    fun getLatLng() = LatLng(lat, lng)
}

data class Viewport(val southwest: Location, val northeast: Location)

data class PhotosItem(val photo_reference: String, val width: Int, val height: Int) {
    fun getPhotoURL() =
        String.format(BuildConfig.MAP_PHOTOS_END_POINT, width, photo_reference, BuildConfig.MAPS_API_KEY)
}

data class AddressComponentsItem(
    val types: List<String>,
    val short_name: String,
    val long_name: String
)

data class Reviews(
    val author_name: String,
    val author_url: String,
    val profile_photo_url: String,
    val rating: Float,
    val relative_time_description: String,
    val text: String,
    val time: Long
)

data class PlaceResult(val address: String, val geometry: Geometry, val place_id: String)
