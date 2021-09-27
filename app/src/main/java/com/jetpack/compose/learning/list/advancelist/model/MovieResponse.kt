package com.jetpack.compose.learning.list.advancelist.model

data class MovieResponse(
    var page: Int,
    var results: List<Movie>
)

data class Movie(
    var id: Int,
    var poster_path: String,
    var original_language: String,
    var overview: String,
    var title: String,
    var original_title: String,
    var backdrop_path: String,
    var vote_average: String,
    var vote_count: String
)
