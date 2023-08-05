package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.data.datasource.GifsPagingSource
import com.example.data.remote.GifsService
import com.example.data.utils.networkResult
import com.example.domain.repository.GifsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val LIMIT = 15

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