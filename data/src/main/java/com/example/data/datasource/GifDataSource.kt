package com.example.data.datasource

import com.example.data.entity.GifEntity
import com.example.data.remote.GifsService
import com.example.libraries.common.exception.GifNotFountException
import javax.inject.Inject

interface GifDataSource {
    suspend fun getTrendingGifs(offset: Int, limit: Int): List<GifEntity>
    suspend fun getGifDetail(id: String): GifEntity
}

class GifDataSourceImpl @Inject constructor(
    private val gifService: GifsService,
) : GifDataSource {
    override suspend fun getTrendingGifs(offset: Int, limit: Int): List<GifEntity> {
        val response = gifService.getTrendingGifs(offset, limit)
        return response.gifs
    }

    override suspend fun getGifDetail(id: String): GifEntity {
        val response = gifService.getGifById(id)
        return response.gifs.firstOrNull() ?: throw GifNotFountException
    }
}
