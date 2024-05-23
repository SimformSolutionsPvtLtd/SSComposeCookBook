package com.jetpack.compose.learning.sharedelementtransition

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.model.Component
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class SharedElementTransitionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Shared Element Transition") },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed() })
                                { Icon(Icons.Filled.ArrowBack, contentDescription = "") }
                            }
                        )
                    }
                ) {
                    SharedTransitionElementExample(Modifier.padding(it))
                }
            }
        }
    }

    @Composable
    fun SharedTransitionElementExample(modifier: Modifier = Modifier) {
        LazyColumn(
            modifier = modifier
        ) {
            items(getComponent().size) {
                ButtonComponent(
                    text = getComponent()[it].componentName,
                    screen = getComponent()[it].className
                )
            }
        }
    }

    @Composable
    fun ButtonComponent(text: String, screen: Class<*>) {
        Button(
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 2.dp
            ),
            onClick = {
                startActivity(
                    Intent(
                        this,
                        screen
                    )
                )
            },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = text,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }

    private fun getComponent(): List<Component> = listOf(
        Component("SET With Navigation", SharedElementTransitionWithNavigationActivity::class.java),
        Component("Text Transform Animation", TextTransformAnimationActivity::class.java),
        Component("Image Animation", ImageAnimationActivity::class.java),
        Component("Search Box Animation", SearchBoxAnimationActivity::class.java),
        Component("Sheet Animation", SheetAnimationActivity::class.java),
        Component("Nested Column", NestedLazyColumnActivity::class.java)
    )
}