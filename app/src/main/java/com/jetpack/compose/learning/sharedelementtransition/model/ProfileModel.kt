package com.jetpack.compose.learning.sharedelementtransition.model

import androidx.annotation.DrawableRes

data class ProfileModel(
    val id: Int,
    val name: String,
    @DrawableRes val image: Int,
)