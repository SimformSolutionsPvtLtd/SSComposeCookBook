package com.jetpack.compose.learning.list.advancelist.retrofit

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jetpack.compose.learning.BuildConfig
import com.jetpack.compose.learning.list.advancelist.model.Movie
import java.io.IOException
import retrofit2.HttpException

class MovieSource : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val userList = RetrofitClient.apiService.getMovieList(
                api_key = BuildConfig.MOVIEDB_API_KEY,
                page = nextPage
            )
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