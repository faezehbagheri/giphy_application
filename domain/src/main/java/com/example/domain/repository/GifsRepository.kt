package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.Gif
import com.example.domain.model.GifDetail
import com.example.libraries.common.result.GetResult
import kotlinx.coroutines.flow.Flow

interface GifsRepository {
    fun getTrendingGifs(): Flow<PagingData<Gif>>
    suspend fun getGifDetail(id: String): Flow<GetResult<GifDetail>>
}
