package com.jetpack.compose.learning.sharedelementtransition.model

import androidx.annotation.DrawableRes

data class ProfileModel(
    val id: Int,
    @DrawableRes val image: Int,
    val name: String
)