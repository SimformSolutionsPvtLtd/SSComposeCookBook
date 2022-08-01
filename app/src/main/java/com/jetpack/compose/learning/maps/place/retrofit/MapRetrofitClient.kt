package com.jetpack.compose.learning.maps.place.retrofit

import com.jetpack.compose.learning.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapRetrofitClient {
    private fun getClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_MAPS)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: MapRetrofitService = getClient().create(MapRetrofitService::class.java)

    private fun getOkHttpClient() =
        OkHttpClient.Builder().addInterceptor(getLoggingInterceptor()).build()

    private fun getLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC)

    companion object {
        @Volatile
        private var INSTANCE: MapRetrofitClient? = null

        fun getInstance(): MapRetrofitClient {
            if (INSTANCE == null) {
                INSTANCE = MapRetrofitClient()
            }
            return INSTANCE!!
        }
    }
}