package com.jetpack.compose.learning.sharedelementtransition.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SharedElementModel(
    val tabInfo: TabInfoModel,
    val offsetX: Dp,
    val offsetY: Dp,
    val size: Dp
) {
    companion object {
        val NONE = SharedElementModel(
            tabInfo = TabInfoModel(0, "", "", 0),
            offsetX = 0.dp,
            offsetY = 0.dp,
            size = 0.dp
        )
    }
}
