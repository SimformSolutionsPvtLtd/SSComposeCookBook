package com.jetpack.compose.learning.sharedelementtransition.model

import androidx.annotation.DrawableRes

data class TabInfoModel(
    @DrawableRes val cover: Int,
    val title: String,
    val author: String,
    val year: Int,
)
