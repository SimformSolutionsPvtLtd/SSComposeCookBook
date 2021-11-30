package com.jetpack.compose.learning.demosamples.instagramdemo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.demosamples.instagramdemo.data.Items

@Composable
fun InstagramStoriesItem(post: Items) {

    val blue = Color(0xFF515BD4)
    val purple = Color(0xFF8134AF)
    val orange = Color(0xFFF58529)
    val yellow = Color(0xFFFEDA77)
    val pink = Color(0xFFDD2A7B)

    val imageModifier =
        Modifier
            .padding(8.dp)
            .size(60.dp)
            .clip(CircleShape)
            .border(
                width = 3.dp,
                brush = Brush.linearGradient(listOf(orange, yellow, pink, purple, blue)),
                shape = CircleShape
            )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = post.profilePic),
            contentDescription = null,
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
        Text(text = post.title)
    }
}