package com.jetpack.compose.learning.sharedelementtransition.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.sharedelementtransition.model.TabInfoModel

@Composable
fun TabPreviewScreen(
    tabInfoList: List<TabInfoModel>,
    sharedProgress: Float,
    onInfoClick: (albumInfo: TabInfoModel, offsetX: Float, offsetY: Float, size: Int) -> Unit
) {
    var clickedItemIndex by remember { mutableStateOf(-1) }
    val transitionInProgress by remember(sharedProgress) {
        derivedStateOf { sharedProgress > 0f }
    }

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) {
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(tabInfoList) { tabInfoModel ->
                    val index = tabInfoList.indexOf(tabInfoModel)
                    Spacer(modifier = Modifier.width(if (index == 0) 24.dp else 16.dp))
                    val itemAlpha = if (clickedItemIndex == index && transitionInProgress) 0f else 1f
                    CompositionLocalProvider(LocalContentAlpha provides itemAlpha) {
                        TabInfoItem(
                            tabInfo = tabInfoModel,
                            onClick = { info, offset, size ->
                                clickedItemIndex = index
                                onInfoClick(info, offset.x, offset.y, size)
                            }
                        )
                    }
                    if (index == tabInfoList.lastIndex) {
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TabInfoItem(
    modifier: Modifier = Modifier,
    tabInfo: TabInfoModel,
    onClick: (info: TabInfoModel, offset: Offset, size: Int) -> Unit
) {
    var tabItemOffset by remember { mutableStateOf(Offset.Unspecified) }
    var tabItemSize by remember { mutableStateOf(0) }

    Column(
        modifier = modifier.width(100.dp).padding(10.dp),
    ) {
        Image(
            painter = painterResource(id = tabInfo.cover),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .onGloballyPositioned { coordinates ->
                    tabItemOffset = coordinates.positionInRoot()
                    tabItemSize = coordinates.size.width
                }
                .clip(RoundedCornerShape(10.dp))
                .alpha(LocalContentAlpha.current)
                .clickable { onClick(tabInfo, tabItemOffset, tabItemSize) },
        )
    }
}
