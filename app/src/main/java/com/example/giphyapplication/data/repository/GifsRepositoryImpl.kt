package com.example.giphyapplication.data.repository

import com.example.giphyapplication.data.remote.GifsService
import com.example.giphyapplication.data.utils.networkResult
import com.example.giphyapplication.domain.repository.GifsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GifsRepositoryImpl @Inject constructor(
    private val gifsService: GifsService
) : GifsRepository {
    override fun getTrendingGifs() =
        networkResult(
            {
                gifsService.getTrendingGifs(limit = 20, offset = 0)
            },
            { data ->
                data.gifs.map { it.toDomain() }
            }
        )
}