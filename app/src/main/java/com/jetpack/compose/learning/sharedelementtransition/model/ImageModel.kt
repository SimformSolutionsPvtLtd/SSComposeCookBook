package com.jetpack.compose.learning.sharedelementtransition.model

import androidx.annotation.DrawableRes

data class ImageModel(
    val id: Int,
    val title: String,
    @DrawableRes val image: Int,
)
