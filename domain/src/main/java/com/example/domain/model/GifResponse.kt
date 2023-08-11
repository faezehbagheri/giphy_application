package com.example.domain.model

import java.io.Serializable

data class GifResponse(
    val gifs: List<Gif>,
    val meta: Meta,
    val pagination: Pagination
): Serializable

data class Pagination(
    val count: Int,
    val offset: Int,
    val totalCount: Int
): Serializable

data class Meta(
    val msg: String,
    val responseId: String,
    val status: Int
): Serializable

data class Gif(
    val id: String,
    val images: Images,
    val rating: String,
    val title: String,
    val type: String,
    val url: String,
    val username: String
): Serializable

data class Images(
    val original: Original,
): Serializable

data class Original(
    val mp4: String,
    val url: String,
    val webp: String,
): Serializable