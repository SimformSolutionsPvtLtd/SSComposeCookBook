package com.jetpack.compose.learning.sharedelementtransition.model

import androidx.annotation.DrawableRes

data class CoffeeModel(
    val id: Int,
    val name: String,
    val description: String,
    @DrawableRes val image: Int
)