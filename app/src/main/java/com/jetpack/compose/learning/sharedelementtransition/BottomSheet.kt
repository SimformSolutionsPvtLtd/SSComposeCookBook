package com.jetpack.compose.learning.sharedelementtransition

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheet(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .background(
                    Color(38, 52, 61),
                    RoundedCornerShape(8.dp)
                )
        ) {
            item { Item(label = "Document") }
            item { Item(label = "Camera") }
            item { Item(label = "Gallery") }
            item { Item(label = "Audio") }
            item { Item(label = "Location") }
            item { Item(label = "Payment") }
            item { Item(label = "Contact") }
            item { Item(label = "Poll") }
        }
    }
}

@Composable
private fun Item(label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Image(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(48.dp)
            )
        }
        Text(
            text = label,
            color = Color(0xFF888888)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}