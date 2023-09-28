package com.jetpack.compose.learning.readmoretextview.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleTextView(titleText: String) {
    Text(
        text = titleText,
        Modifier.padding(horizontal = 10.dp),
        fontSize = 18.sp
    )
}