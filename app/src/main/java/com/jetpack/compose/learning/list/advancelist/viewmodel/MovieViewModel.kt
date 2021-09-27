package com.jetpack.compose.learning.list.advancelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jetpack.compose.learning.list.advancelist.model.Movie
import com.jetpack.compose.learning.list.advancelist.retrofit.MovieSource
import kotlinx.coroutines.flow.Flow

class MovieViewModel : ViewModel() {
    val user: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 6, prefetchDistance = 2)) {
        MovieSource()
    }.flow.cachedIn(viewModelScope)
}
