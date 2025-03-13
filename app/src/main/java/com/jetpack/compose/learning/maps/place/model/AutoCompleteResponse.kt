package com.jetpack.compose.learning.maps.place.model

import com.google.gson.annotations.SerializedName

data class AutoCompleteResponse(
    var predictions: List<AutoCompleteItem>,
    var status: String
)

data class AutoCompleteItem(
    var description: String,
    @SerializedName("matched_substrings")
    var matchedSubstrings: List<MatchedSubStrings>,
    @SerializedName("place_id")
    var placeId: String,
    var reference: String
)

data class MatchedSubStrings(val length: Int, val offset: Int)
