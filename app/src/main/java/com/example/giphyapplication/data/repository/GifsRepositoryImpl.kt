package com.example.giphyapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.giphyapplication.common.LIMIT
import com.example.giphyapplication.data.datasource.GifsPagingSource
import com.example.giphyapplication.data.remote.GifsService
import com.example.giphyapplication.data.utils.networkResult
import com.example.giphyapplication.domain.repository.GifsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GifsRepositoryImpl @Inject constructor(
    private val gifsService: GifsService,
) : GifsRepository {
    override fun getTrendingGifs() =
        Pager(
            config = PagingConfig(
                pageSize = LIMIT,
            ),
            pagingSourceFactory = {
                GifsPagingSource(gifsService)
            }
        ).flow.map { it.map { data -> data.toDomain() } }


    override fun getGifById(id: String) =
        networkResult(
            {
                gifsService.getGifById(id)
            },
            { data ->
                data.gifs.map { it.toDomain() }.first()
            }
        )
}