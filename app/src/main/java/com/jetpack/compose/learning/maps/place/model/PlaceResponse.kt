package com.jetpack.compose.learning.maps.place.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.gson.annotations.SerializedName
import com.jetpack.compose.learning.BuildConfig
import java.util.Locale

data class PlaceResponse(val result: PlaceDetail?, val status: String)

data class PlaceDetail(
    val utcOffset: Int,
    @SerializedName("formatted_address")
    val formattedAddress: String?,
    val types: List<String>?,
    val icon: String,
    @SerializedName("icon_background_color")
    val iconBackgroundColor: String,
    @SerializedName("address_components")
    val addressComponents: List<AddressComponentsItem>?,
    val photos: List<PhotosItem>?,
    val url: String,
    val reference: String,
    val name: String,
    val geometry: Geometry,
    @SerializedName("icon_mask_base_uri")
    val iconMaskBaseUri: String,
    val vicinity: String,
    @SerializedName("adr_address")
    val adrAddress: String,
    @SerializedName("place_id")
    val placeId: String,
    val rating: Float?,
    val reviews: List<Reviews>?,
    @SerializedName("user_ratings_total")
    val userRatingsTotal: Int?,
) {
    fun getLocation() = geometry.location.getLatLng()

    fun getLatLngBound(): LatLngBounds {
        val viewport = geometry.viewport
        val northeast = LatLng(viewport.northeast.lat, viewport.northeast.lng)
        val southwest = LatLng(viewport.southwest.lat, viewport.southwest.lng)
        return LatLngBounds(southwest, northeast)
    }

    fun getAddress(): String {
        if (formattedAddress == null) {
            return name
        }
        if (addressComponents.isNullOrEmpty()) {
            return "$name $formattedAddress"
        }
        val placeCodes = addressComponents.filter { it.types.contains("plus_code") }
            .map { Pair(it.longName, it.shortName) }
        var address = "$name $formattedAddress"
        placeCodes.forEach {
            if (address.contains(it.first))
                address = address.replace(it.first, "")
            if (address.contains(it.second))
                address = address.replace(it.second, "")
        }
        return address
    }

    fun getPlaceResult(): PlaceResult {
        return PlaceResult(getAddress(), geometry, placeId)
    }
}

data class Geometry(val viewport: Viewport, val location: Location)

data class Location(val lng: Double, val lat: Double) {
    fun getLatLng() = LatLng(lat, lng)
}

data class Viewport(val southwest: Location, val northeast: Location)

data class PhotosItem(val photoReference: String, val width: Int, val height: Int) {
    fun getPhotoURL() =
        String.format(Locale.getDefault(), BuildConfig.MAP_PHOTOS_END_POINT, width, photoReference, BuildConfig.MAPS_API_KEY)
}

data class AddressComponentsItem(
    val types: List<String>,
    @SerializedName("short_name")
    val shortName: String,
    @SerializedName("long_name")
    val longName: String
)

data class Reviews(
    @SerializedName("author_name")
    val authorName: String,
    @SerializedName("author_url")
    val authorUrl: String,
    @SerializedName("profile_photo_url")
    val profilePhotoUrl: String,
    val rating: Float,
    @SerializedName("relative_time_description")
    val relativeTimeDescription: String,
    val text: String,
    val time: Long
)

data class PlaceResult(val address: String, val geometry: Geometry, val placeId: String)
