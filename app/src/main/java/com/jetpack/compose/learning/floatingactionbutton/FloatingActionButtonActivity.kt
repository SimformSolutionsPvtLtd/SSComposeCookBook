package com.jetpack.compose.learning.floatingactionbutton

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class FloatingActionButtonActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }

    @Preview
    @Composable
    private fun MainContent() {
        Scaffold (topBar = {
            TopAppBar(
                title = { Text("Floating Action Buttons") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },  //FAB Button inside Scaffold
            floatingActionButton = {
                FloatingActionButton(onClick = { onClick("Fab Button using Scaffold") }) {
                    Icon(Icons.Filled.Add, "")
                }
            }
        ) {
            FabButtons()
        }
    }

    @Composable
    private fun FabButtons() {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)) {
            val spaceModifier = Modifier.height(30.dp)

            //Simple FAB
            FloatingActionButton(onClick = { onClick("Simple FAB") }) {
                Icon(Icons.Filled.Add, "")
            }

            Spacer(modifier = spaceModifier)

            //FAB custom color
            FloatingActionButton(
                onClick = { onClick("Fab with custom color") },
                backgroundColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, "")
            }

            Spacer(modifier = spaceModifier)

            //Square FAB
            FloatingActionButton(
                onClick = { onClick("Square FAB") },
                shape = RectangleShape
            ) {
                Icon(Icons.Filled.Add, "")
            }

            Spacer(modifier = spaceModifier)

            //Simple FAB with custom content (similar of Exntended FAB)
            FloatingActionButton(onClick = { onClick("Simple FAB with custom content") },
                contentColor = Color.White,
                backgroundColor = Color.Blue,
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                ) {
                    Icon(Icons.Filled.Add, "")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Simple FAB with custom content")
                }
            })

            Spacer(modifier = spaceModifier)

            //EXTENDED FAB
            ExtendedFloatingActionButton(
                text = {  Text(text = "Extended FAB with icon") },
                onClick = { onClick("Extended FAB with icon") },
                icon ={ Icon(Icons.Filled.Add,"")}
            )

            Spacer(modifier = spaceModifier)

            //EXTENDED FAB WITHOUT ICON
            ExtendedFloatingActionButton(
                text = {
                    Text(text = "Extended FAB without icon")
                },
                onClick = { onClick("Extended FAB without Icon") }
            )

            Spacer(modifier = spaceModifier)

            //EXTENDED FAB WITH SQUARE SHAPE
            ExtendedFloatingActionButton(
                text = {
                    Text(text = "Extended FAB with square shape")
                },
                onClick = { onClick("Extended FAB with square shape") },
                shape = RectangleShape
            )
        }
    }

    fun onClick(textMessage: String) {
        Toast.makeText(applicationContext, textMessage, Toast.LENGTH_SHORT).show()
    }
}