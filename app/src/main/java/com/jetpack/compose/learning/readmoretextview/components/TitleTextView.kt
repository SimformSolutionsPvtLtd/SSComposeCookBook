package com.jetpack.compose.learning.readmoretextview.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Custom composable for showing Title text for ReadMoreTextView usage examples in ReadMoreTextViewActivity
 *
 * @param titleText Text to be shown for usage example title
 */
@Composable
fun TitleTextView(titleText: String) {
    Text(
        text = titleText,
        modifier = Modifier.padding(horizontal = 10.dp),
        fontSize = 18.sp
    )
}