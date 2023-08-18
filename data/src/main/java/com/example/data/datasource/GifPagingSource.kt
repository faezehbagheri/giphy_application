package com.example.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.entity.GifEntity
import com.example.data.repository.LIMIT
import retrofit2.HttpException
import java.io.IOException

class GifPagingSource(private val gifDataSource: GifDataSource) : PagingSource<Int, GifEntity>() {

    override fun getRefreshKey(state: PagingState<Int, GifEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)
                ?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)
                    ?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifEntity> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val gifs = gifDataSource.getGifs(offset = page * LIMIT, limit = LIMIT)
            val nextKey = (page + 1).let {
                if (gifs.size < LIMIT || it == params.key) {
                    null
                } else {
                    it
                }
            }
            val prevKey = if (page == STARTING_PAGE_INDEX) null else page
            LoadResult.Page(gifs, prevKey = prevKey, nextKey = nextKey)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}