package com.jetpack.compose.learning.maps.place.model

data class AutoCompleteResponse(
    var predictions: List<AutoCompleteItem>,
    var status: String
)

data class AutoCompleteItem(
    var description: String,
    var matched_substrings: List<MatchedSubStrings>,
    var place_id: String,
    var reference: String
)

data class MatchedSubStrings(val length: Int, val offset: Int)
