package com.jetpack.compose.learning.sharedelementtransition.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import com.jetpack.compose.learning.sharedelementtransition.toDp

@Composable
fun SharedElementContainer(
    modifier: Modifier = Modifier,
    coverOffset: Offset,
    coverSize: Dp,
    coverCornersRadius: Dp,
    topMenu: @Composable BoxScope.() -> Unit,
    labels: @Composable BoxScope.() -> Unit,
    sharedElement: @Composable BoxScope.() -> Unit,
) {
    val density = LocalDensity.current

    Box(
        modifier = modifier,
    ) {
        topMenu()
        Column {
            Box(
                modifier = modifier
                    .padding(top = coverOffset.y.toDp(density))
                    .offset {
                        IntOffset(x = coverOffset.x.toInt(), y = 0)
                    }
                    .size(coverSize)
                    .clip(RoundedCornerShape(coverCornersRadius)),
                content = sharedElement,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                content = labels,
            )
        }
    }
}