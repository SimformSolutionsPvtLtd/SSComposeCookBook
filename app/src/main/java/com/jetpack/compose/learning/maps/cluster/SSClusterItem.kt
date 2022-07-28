package com.jetpack.compose.learning.maps.cluster

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class SSClusterItem(
    private val latLng: LatLng,
    private val title: String,
    private val snippet: String
) : ClusterItem {
    override fun getPosition(): LatLng {
        return latLng
    }

    override fun getTitle(): String {
        return title
    }

    override fun getSnippet(): String {
        return snippet
    }
}
