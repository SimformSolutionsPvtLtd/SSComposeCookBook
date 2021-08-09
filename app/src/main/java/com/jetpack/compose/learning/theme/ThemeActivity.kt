package com.jetpack.compose.learning.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ThemeActivity : ComponentActivity() {

    lateinit var appTheme: MutableState<AppThemeState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                ThemeSample()
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview
    @Composable
    fun ThemeSample() {
        val spaceHeight = 24.dp
        Column {
            TopAppBar(
                title = { Text("Theme") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            Column(Modifier.padding(20.dp)) {
                Text(text = "Change theme of this screen : ", style = MaterialTheme.typography.h6)
                Spacer(Modifier.requiredHeight(spaceHeight))
                val colorPallet = ColorPalette.values()
                LazyVerticalGrid(cells = GridCells.Fixed(3)){
                    items(colorPallet.size, itemContent = {
                        val isSelected = colorPallet.get(it) == appTheme.value.pallet
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                Modifier
                                    .padding(20.dp)
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(colorPallet.get(it).getMaterialColor())
                            .clickable {
                                appTheme.value = AppThemeState(false, colorPallet.get(it))
                            }
                            .then(
                                if (isSelected) {
                                    Modifier.border(
                                        BorderStroke(2.dp, MaterialTheme.colors.onSurface),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                } else Modifier
                            )
                            ){
                                if (isSelected) {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = "Theme Selected",
                                        modifier = Modifier.align(Alignment.Center).size(40.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}