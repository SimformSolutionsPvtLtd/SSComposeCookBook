package com.jetpack.compose.learning.list.advancelist.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    var page: Int,
    var results: List<Movie>
)

data class Movie(
    var id: Int,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("original_language")
    var originalLanguage: String,
    var overview: String,
    var title: String,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("vote_average")
    var voteAverage: String,
    @SerializedName("vote_count")
    var voteCount: String
)
