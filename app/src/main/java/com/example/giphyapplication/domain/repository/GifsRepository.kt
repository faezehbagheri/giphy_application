package com.example.giphyapplication.domain.repository

import androidx.paging.PagingData
import com.example.giphyapplication.common.result.GetResult
import com.example.giphyapplication.domain.model.Gif
import kotlinx.coroutines.flow.Flow

interface GifsRepository {
    fun getTrendingGifs(): Flow<PagingData<Gif>>
}