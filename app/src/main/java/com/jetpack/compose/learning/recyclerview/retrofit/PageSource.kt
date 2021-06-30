package com.jetpack.compose.learning.recyclerview.retrofit

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jetpack.compose.learning.recyclerview.model.Movie
import java.io.IOException
import retrofit2.HttpException

class PageSource : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val userList = RetrofitClient.apiService.getMovieList(api_key = "8f3845576311ddddc2ae7f7801641fdb",
                page = nextPage)
            LoadResult.Page(
                data = userList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (userList.results.isEmpty()) null else userList.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}