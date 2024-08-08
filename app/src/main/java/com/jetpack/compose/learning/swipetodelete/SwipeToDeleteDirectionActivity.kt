package com.jetpack.compose.learning.swipetodelete

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

@ExperimentalMaterialApi
class SwipeToDeleteDirectionActivity: ComponentActivity() {

    var swipeDirection: SwipeDirection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        swipeDirection = intent.extras?.get("SwipeDirection") as? SwipeDirection
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainContent()
            }
        }
    }

    @Preview
    @Composable
    fun MainContent() {
        val context = LocalContext.current
        val items = remember { mutableStateListOf<Int>() }
        items.addAll(0..10)

        Scaffold(topBar = {
            TopAppBar(
                title = { Text(when (swipeDirection) {
                    SwipeDirection.LEFT -> "Swipe Left"
                    SwipeDirection.RIGHT -> "Swipe Right"
                    else -> "Swipe Left + Right"
                }) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }) { contentPadding ->
            LazyColumn(
                modifier = Modifier.padding(contentPadding),
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items.size) {
                    CellItem(number = items[it], context)
                }
            }
        }
    }

    @Composable
    fun CellItem(number: Int, context: Context) {
        SwipeAbleItemCell(number = number, swipeDirection = swipeDirection ?: SwipeDirection.BOTH, onEditClick = {
            Toast.makeText(context, "Edit button clicked. Position :- $it", Toast.LENGTH_SHORT).show()
        }, onDeleteClicked = {
            Toast.makeText(context, "Delete button clicked. Position :- $it", Toast.LENGTH_SHORT).show()
        })
    }
}