package com.jetpack.compose.learning.maps.place.retrofit

import com.jetpack.compose.learning.BuildConfig
import com.jetpack.compose.learning.maps.place.model.AutoCompleteResponse
import com.jetpack.compose.learning.maps.place.model.DirectionResponse
import com.jetpack.compose.learning.maps.place.model.PlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MapRetrofitService {
    @GET("place/autocomplete/json")
    suspend fun getAutoCompletePlaces(
        @Query("input") input: String,
        @Query("key") api_key: String = BuildConfig.MAPS_API_KEY
    ): AutoCompleteResponse

    @GET("place/details/json")
    suspend fun getPlaceDetail(
        @Query("placeid") placeId: String,
        @Query("key") api_key: String = BuildConfig.MAPS_API_KEY
    ): PlaceResponse

    @GET("directions/json")
    suspend fun getDirections(
        @Query("origin") originPlaceId: String,
        @Query("destination") destinationPlaceId: String,
        @Query("alternatives") alternatives: Boolean = true,
        @Query("key") api_key: String = BuildConfig.MAPS_API_KEY
    ): DirectionResponse
}