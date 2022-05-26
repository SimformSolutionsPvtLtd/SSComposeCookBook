package com.jetpack.compose.learning.backdrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jetpack.compose.learning.theme.AppThemeState
import com.jetpack.compose.learning.theme.BaseView
import com.jetpack.compose.learning.theme.SystemUiController
import kotlinx.coroutines.launch

class BackdropScaffoldActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(appTheme.value, systemUiController) {
                BackDropExample()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun BackDropExample() {
        val backDropState = rememberBackdropScaffoldState(BackdropValue.Concealed)
        val stateScope = rememberCoroutineScope()

        var persistentAppBar by rememberSaveable { mutableStateOf(true) }
        var stickyFrontLayer by rememberSaveable { mutableStateOf(true) }
        var gestureEnabled by rememberSaveable { mutableStateOf(true) }

        BackdropScaffold(
            scaffoldState = backDropState,
            appBar = {
                TopAppBar(
                    title = { Text("Backdrop Scaffold") },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            },
            backLayerContent = {
                BackLayer()
            },
            frontLayerContent = {
                FrontLayer(
                    isReveal = backDropState.isRevealed,
                    onRevealClick = {
                        if (backDropState.isRevealed) {
                            stateScope.launch {
                                backDropState.conceal()
                            }
                        } else {
                            stateScope.launch {
                                backDropState.reveal()
                            }
                        }
                    },
                    isPersistentAppBar = persistentAppBar,
                    onPersistentAppBarChange = { change ->
                        persistentAppBar = change
                    },
                    stickyFrontLayer = stickyFrontLayer,
                    onStickyFrontLayerChange = { change ->
                        stickyFrontLayer = change
                    },
                    gestureEnabled = gestureEnabled,
                    onGestureEnableChange = { change ->
                        gestureEnabled = change
                    },
                )
            },
            backLayerBackgroundColor = MaterialTheme.colors.primary,
            backLayerContentColor = Color.White,
            persistentAppBar = persistentAppBar,
            frontLayerScrimColor = Color.Black.copy(alpha = 0.1f),
            stickyFrontLayer = stickyFrontLayer,
            gesturesEnabled = gestureEnabled,
        )
    }

    @Composable
    fun BackLayer() {
        LazyColumn {
            items(5) { index ->
                BackLayerItem("Menu $index")
            }
        }
    }

    @Composable
    fun BackLayerItem(text: String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text, modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp))
            Divider(color = Color.White)
        }
    }

    @Composable
    fun FrontLayer(
        modifier: Modifier = Modifier,
        isReveal: Boolean,
        onRevealClick: () -> Unit,
        isPersistentAppBar: Boolean,
        onPersistentAppBarChange: (Boolean) -> Unit,
        stickyFrontLayer: Boolean,
        onStickyFrontLayerChange: (Boolean) -> Unit,
        gestureEnabled: Boolean,
        onGestureEnableChange: (Boolean) -> Unit,
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VerticalSpace(20)
            OutlinedButton(
                onClick = onRevealClick,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(if (isReveal) "Conceal" else "Reveal")
            }
            VerticalSpace()
            CheckBoxText("Persistent AppBar", isPersistentAppBar, onPersistentAppBarChange)
            CheckBoxText("Sticky front layer", stickyFrontLayer, onStickyFrontLayerChange)
            CheckBoxText("Gesture Enabled", gestureEnabled, onGestureEnableChange)
        }
    }

    @Composable
    fun CheckBoxText(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
            onCheckedChange.invoke(!checked)
        }) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
            )
            Text(text)
        }
        VerticalSpace()
    }

    @Composable
    fun VerticalSpace(sizeInDP: Int = 10) {
        Spacer(Modifier.height(sizeInDP.dp))
    }
}