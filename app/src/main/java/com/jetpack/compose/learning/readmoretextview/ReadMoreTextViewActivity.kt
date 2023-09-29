package com.jetpack.compose.learning.readmoretextview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.readmoretextview.components.ExpandableTextView


class ReadMoreTextViewActivity : ComponentActivity() {
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


