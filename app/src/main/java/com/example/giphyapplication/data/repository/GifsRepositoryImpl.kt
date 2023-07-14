package com.example.giphyapplication.data.repository

import com.example.giphyapplication.common.LIMIT
import com.example.giphyapplication.data.remote.GifsService
import com.example.giphyapplication.data.utils.networkResult
import com.example.giphyapplication.domain.repository.GifsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GifsRepositoryImpl @Inject constructor(
    private val gifsService: GifsService
) : GifsRepository {
    override fun getTrendingGifs(page: Int) =
        networkResult(
            {
                gifsService.getTrendingGifs(limit = LIMIT, offset = page * LIMIT)
            },
            { data ->
                data.gifs.map { it.toDomain() }
            }
        )
}