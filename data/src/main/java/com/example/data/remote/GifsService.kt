package com.example.data.remote

import com.example.data.BuildConfig
import com.example.data.entity.GifResponseEntity
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

    @GET("gifs")
    suspend fun getGifById(
        @Query("ids")
        id: String,
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY,
    ): GifResponseEntity

}