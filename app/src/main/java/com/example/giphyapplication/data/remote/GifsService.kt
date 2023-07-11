package com.example.giphyapplication.data.remote

import com.example.giphyapplication.BuildConfig
import com.example.giphyapplication.data.entity.GifResponseEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface GifsService {
    @GET("gifs/trending")
    suspend fun getTrendingGifs(
        @Query("limit")
        limit: Int,
        @Query("offset")
        offset: Int,
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY
    ): GifResponseEntity

}