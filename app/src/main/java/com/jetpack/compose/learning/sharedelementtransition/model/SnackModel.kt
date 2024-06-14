package com.jetpack.compose.learning.sharedelementtransition.model

import androidx.annotation.DrawableRes

data class SnackModel(
    val name: String,
    @DrawableRes val image: Int
)
