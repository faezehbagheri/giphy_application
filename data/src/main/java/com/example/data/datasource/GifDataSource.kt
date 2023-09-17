package com.example.data.datasource

import androidx.paging.PagingSource
import com.example.data.entity.GifEntity
import com.example.data.remote.GifService
import com.example.libraries.common.exception.GifNotFountException
import javax.inject.Inject

interface GifDataSource {
    fun getGifs(): PagingSource<Int, GifEntity>
    fun searchOnGifs(searchTerms: String): PagingSource<Int, GifEntity>
    suspend fun getGifDetail(id: String): GifEntity
}

class GifDataSourceImpl @Inject constructor(
    private val gifService: GifService,
) : GifDataSource {
    override fun getGifs(): PagingSource<Int, GifEntity> =
        GifPagingSource(gifService = gifService)

    override fun searchOnGifs(searchTerms: String): PagingSource<Int, GifEntity> =
        SearchPagingSource(gifService, searchTerms)

    override suspend fun getGifDetail(id: String): GifEntity {
        val response = gifService.getGifDetail(id)
        return response.gifs.firstOrNull() ?: throw GifNotFountException
    }
}
