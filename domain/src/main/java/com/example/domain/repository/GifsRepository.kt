package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.Gif
import com.example.common.result.GetResult
import kotlinx.coroutines.flow.Flow

interface GifsRepository {
    fun getTrendingGifs(): Flow<PagingData<Gif>>
    fun getGifById(id: String): Flow<GetResult<Gif>>
}
