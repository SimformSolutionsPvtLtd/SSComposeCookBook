package com.jetpack.compose.learning.demosamples.instagramdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.demosamples.instagramdemo.components.InstaGramPostItem
import com.jetpack.compose.learning.demosamples.instagramdemo.components.InstagramStoriesItem
import com.jetpack.compose.learning.data.DataProvider

class InstagramHomeActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                InstagramHomeScreen()
            }
        }
    }

    @Composable
    fun InstagramHomeScreen() {

        Column {
            InstagramHeader()
            Divider()
            InstagramStories()
            Divider()
            InstagramPost()
        }
    }

    @Composable
    fun InstagramHeader() {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_instagram_text),
                contentDescription = null,
                modifier =
                Modifier
                    .size(width = 150.dp, height = 50.dp)
                    .weight(1f),
                alignment = Alignment.TopStart
            )
            Icon(painter = painterResource(id = R.drawable.ic_collapse_plus), contentDescription = null, modifier = Modifier
                .padding(8.dp)
                .size(25.dp))
            Icon(painter = painterResource(id = R.drawable.ic_instagram_share), contentDescription = null, modifier = Modifier.padding(8.dp))
        }
    }

    @Composable
    fun InstagramStories() {
        val stories = remember { DataProvider.postList }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = stories,
                itemContent = {
                    InstagramStoriesItem(it)
                }
            )
        }
    }

    @Composable
    fun InstagramPost() {
        val posts = remember { DataProvider.postList }
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = posts,
                itemContent = {
                    InstaGramPostItem(it)
                }
            )
        }
    }
}
