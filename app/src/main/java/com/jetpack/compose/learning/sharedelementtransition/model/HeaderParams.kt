package com.jetpack.compose.learning.sharedelementtransition.model

import androidx.annotation.DrawableRes

data class HeaderParams(
    val sharedElementParams: SharedElementParams,
    @DrawableRes val coverId: Int,
    val title: String,
    val author: String,
)