package com.example.giphyapplication.domain.repository

import com.example.giphyapplication.common.result.GetResult
import com.example.giphyapplication.domain.model.Gif
import kotlinx.coroutines.flow.Flow

interface GifsRepository {
    fun getTrendingGifs(): Flow<GetResult<List<Gif>>>
}