package com.jetpack.compose.learning.maps

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.compose.learning.maps.basic.BasicMapActivity
import com.jetpack.compose.learning.maps.circle.MapCircleActivity
import com.jetpack.compose.learning.maps.marker.MapMarkerActivity
import com.jetpack.compose.learning.maps.polygon.MapPolygonActivity
import com.jetpack.compose.learning.maps.polyline.MapPolylineActivity
import com.jetpack.compose.learning.model.Component
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController

class GoogleMapsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                MainContent()
            }
        }
    }

    @Composable
    fun MainContent() {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Google Maps") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        }) {
            LazyColumn(modifier = Modifier.padding(it)) {
                items(getComponents(), key = { item -> item.className.name }) { item ->
                    ButtonComponent(item)
                }
            }
        }
    }

    @Composable
    fun ButtonComponent(component: Component) {
        OutlinedButton(
            onClick = {
                startActivity(Intent(this, component.className))
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary.copy(0.04f)
            ),
            border = BorderStroke(2.dp, MaterialTheme.colors.primary.copy(0.5f)),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = component.componentName,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )
        }
    }

    private fun getComponents(): List<Component> = listOf(
        Component("Basic Map", BasicMapActivity::class.java),
        Component("Markers", MapMarkerActivity::class.java),
        Component("Polyline", MapPolylineActivity::class.java),
        Component("Polygon", MapPolygonActivity::class.java),
        Component("Circle", MapCircleActivity::class.java)
    )
}
