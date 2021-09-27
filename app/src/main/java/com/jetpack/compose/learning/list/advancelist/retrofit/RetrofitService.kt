package com.jetpack.compose.learning.list.advancelist.retrofit

import com.jetpack.compose.learning.list.advancelist.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("popular")
    suspend fun getMovieList(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): MovieResponse
}