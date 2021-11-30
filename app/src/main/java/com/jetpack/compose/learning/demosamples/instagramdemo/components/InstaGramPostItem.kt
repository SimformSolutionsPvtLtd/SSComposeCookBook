package com.jetpack.compose.learning.demosamples.instagramdemo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
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
import com.jetpack.compose.learning.R

@Composable
fun InstaGramPostItem(post: Items) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column{
            ProfileInfo(post)
            Image(
                painter = painterResource(id = post.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(top = 8.dp, bottom = 8.dp),
                contentScale = ContentScale.Crop

            )
            IconSection()
            LikedBySection(post)
            Text(
                text = "View all 6 comments",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                color = Color.Gray
            )
            AddCommentSection(post)
            Text(
                text = "10 min ago",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp, bottom = 8.dp),
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ProfileInfo(post: Items) {

    val blue = Color(0xFF515BD4)
    val purple = Color(0xFF8134AF)
    val orange = Color(0xFFF58529)
    val yellow = Color(0xFFFEDA77)
    val pink = Color(0xFFDD2A7B)

    Row(
        Modifier
            .padding(start = 8.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = post.profilePic),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(40.dp)
                .clip(CircleShape)
                .border(
                    3.dp,
                    brush = Brush.linearGradient(listOf(orange, yellow, pink, purple, blue)),
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
        Text(text = post.title, Modifier.weight(1f))
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun IconSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_instagram_comment),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(20.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_instagram_share),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_instagram_save),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterEnd)
        )
    }

}

@Composable
fun LikedBySection(post: Items) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = post.profilePic),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(20.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(text = "Liked by ${post.title} and 10 others", Modifier.weight(1f))
    }
}

@Composable
fun AddCommentSection(post: Items) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = post.profilePic),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(25.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop

        )
        Text(text = "Add a comment...", Modifier.weight(1f), color = Color.LightGray)
    }
}