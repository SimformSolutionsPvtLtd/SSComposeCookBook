package com.jetpack.compose.learning.sharedelementtransition.model

data class ParentDataClass(
    val id: Int,
    val title: String,
    val childList: List<ChildDataClass>
)