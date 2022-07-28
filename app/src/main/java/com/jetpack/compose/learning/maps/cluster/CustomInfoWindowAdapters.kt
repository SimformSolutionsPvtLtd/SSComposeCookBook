package com.jetpack.compose.learning.maps.cluster

import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.Marker

/**
 * ClusterInfoWindowAdapter is based on official compose info window adapter.
 * Have a look at ComposeInfoWindowAdapter.kt
 */
class ClusterInfoWindowAdapter(
    private val viewGroup: ViewGroup,
    private val compositionContext: CompositionContext
) : GoogleMap.InfoWindowAdapter {

    private val infoWindowView: ComposeView
        get() = ComposeView(viewGroup.context).apply {
            viewGroup.addView(this)
        }

    override fun getInfoContents(marker: Marker): View? {
        //Override this method if you want to set only content with default background
        return null
    }

    override fun getInfoWindow(marker: Marker): View {
        return infoWindowView.applyAndRemove(compositionContext) {
            MarkerContent(marker)
        }
    }

    /**
     * Sets parent composition component on compose view and removes the parent of compose view.
     */
    private fun ComposeView.applyAndRemove(
        parentContext: CompositionContext,
        content: @Composable () -> Unit
    ): ComposeView {
        val composeView = this.apply {
            setParentCompositionContext(parentContext)
            setContent(content)
        }
        (this.parent as? MapView)?.removeView(this)
        return composeView
    }

    @Composable
    fun MarkerContent(marker: Marker) {
        Column(
            modifier = Modifier
                .background(Color.LightGray, RoundedCornerShape(4.dp))
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("ID-${marker.id} Custom Cluster Info Window.\nAny click event inside info window will be handle by cluster manager's setOnClusterInfoWindowClickListener")
        }
    }
}
