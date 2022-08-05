package com.jetpack.compose.learning.viewpager.viewmodel

import androidx.lifecycle.ViewModel
import com.jetpack.compose.learning.viewpager.ComposableFun
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class AddRemovePagerViewModel : ViewModel() {

    private val composableList = ArrayList<Pair<ComposableFun, Int>>()
    var selectedDropdownValue = MutableStateFlow("")
    var isShowDropdown = MutableStateFlow(false)
    var positionValueTextField = MutableStateFlow("")
    var isShowPositionError = MutableStateFlow(false)

    private val composableListChannel = Channel<ArrayList<Pair<ComposableFun, Int>>>()
    val composableListFlow = composableListChannel.receiveAsFlow()

    suspend fun addComposable(position: Int, item: ComposableFun) {
        composableList.add(position, Pair(item, composableList.size))
        composableListChannel.send(composableList)
    }

    suspend fun removeComposable(position: Int) {
        composableList.removeAt(position)
        composableListChannel.send(composableList)
    }

    fun getComposableNumber(index: Int): Int {
        return composableList[index].second
    }

    fun getComposableList(): ArrayList<Pair<ComposableFun, Int>> {
        return composableList
    }
}

