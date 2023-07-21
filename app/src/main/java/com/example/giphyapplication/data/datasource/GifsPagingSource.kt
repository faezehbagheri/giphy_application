package com.example.giphyapplication.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.giphyapplication.common.LIMIT
import com.example.giphyapplication.data.entity.GifEntity
import com.example.giphyapplication.data.remote.GifsService

class GifsPagingSource(
    private val gifsService: GifsService,
): PagingSource<Int, GifEntity>() {
    override fun getRefreshKey(state: PagingState<Int, GifEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifEntity> {
        return try {
            val page = params.key ?: 0
            val response = gifsService.getTrendingGifs(offset = page * LIMIT, limit = LIMIT)

            LoadResult.Page(
                data = response.gifs,
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (response.gifs.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}