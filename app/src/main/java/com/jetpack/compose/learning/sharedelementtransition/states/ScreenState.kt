package com.jetpack.compose.learning.sharedelementtransition.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Constraints

enum class TABPREVIEW {
    PREVIEW
}

enum class TABPREVIEWDETAIL {
    NONE,
    PREVIEWDETAIL
}

@Composable
fun rememberScreenState(
    constraints: Constraints,
) = remember(constraints) {
    ScreenState(constraints)
}

class ScreenState(constraints: Constraints) {
    @Stable
    var maxContentWidth = constraints.maxWidth
    var currentScreen by mutableStateOf(TABPREVIEW.PREVIEW)
}
