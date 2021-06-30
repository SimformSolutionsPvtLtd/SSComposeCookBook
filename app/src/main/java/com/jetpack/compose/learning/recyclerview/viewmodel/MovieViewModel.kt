package com.jetpack.compose.learning.recyclerview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jetpack.compose.learning.recyclerview.model.Movie
import com.jetpack.compose.learning.recyclerview.retrofit.PageSource
import kotlinx.coroutines.flow.Flow

class MovieViewModel : ViewModel() {
    val user: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 6, prefetchDistance = 2)) {
        PageSource()
    }.flow.cachedIn(viewModelScope)
}
