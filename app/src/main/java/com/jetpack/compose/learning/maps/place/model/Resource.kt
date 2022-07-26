package com.jetpack.compose.learning.maps.place.model

/**
 * A Resource interface to check the different states from API.
 */
sealed interface Resource<out T> {
    object Loading : Resource<Nothing>
    data class Success<T>(val data: T) : Resource<T>
    data class Error(val message: String = "", val throwable: Throwable? = null) : Resource<Nothing>
}

fun <T> getResultAccordingToStatus(data: T, status: String): Resource<T> {
    return when (status) {
        "OK" -> Resource.Success(data)
        "OVER_QUERY_LIMIT" -> Resource.Error("Over Query Limit")
        else -> Resource.Error("No Data Found")
    }
}
