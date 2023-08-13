package com.example.data.datasource

import com.example.data.entity.GifEntity
import com.example.data.remote.GifsService
import com.example.libraries.common.exception.GifNotFountException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface GifDataSource {
    suspend fun getGifs(offset: Int, limit: Int): List<GifEntity>
    suspend fun getGifDetail(id: String): GifEntity
}

class GifDataSourceImpl @Inject constructor(
    private val gifService: GifsService,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : GifDataSource {
    override suspend fun getGifs(offset: Int, limit: Int): List<GifEntity> = withContext(
        context = coroutineContext,
    ) {
        val response = gifService.getTrendingGifs(offset, limit)
        return@withContext response.gifs
    }

    override suspend fun getGifDetail(id: String): GifEntity = withContext(
        context = coroutineContext,
    ) {
        val response = gifService.getGifById(id)
        return@withContext response.gifs.firstOrNull() ?: throw GifNotFountException
    }
}
