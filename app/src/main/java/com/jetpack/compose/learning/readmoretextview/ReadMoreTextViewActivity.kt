package com.jetpack.compose.learning.readmoretextview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.readmoretextview.components.ExpandableTextView


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpandableTextView(
                text = getString(R.string.expandable_text),
                enableActionIcons = true,
                enableActionText = true
            )

//                RecyclerView()
//                CardView()
        }
    }
}
/*
@Composable
fun CardView() {
    Card(modifier = Modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp), elevation = CardDefaults.cardElevation()) {
        ExpandableTextView(
            text = getString(LocalContext.current, R.string.expandable_text)
        )
    }
}

@Composable
fun RecyclerView() {
    LazyColumn {
        items(20) {
            CardView()
        }
    }
}

 */


