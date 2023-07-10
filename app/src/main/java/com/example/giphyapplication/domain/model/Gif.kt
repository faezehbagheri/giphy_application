package com.example.giphyapplication.domain.model

data class Gif(
    val data: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)

data class Pagination(
    val count: Int,
    val offset: Int,
    val totalCount: Int
)

data class Meta(
    val msg: String,
    val responseId: String,
    val status: Int
)

data class Data(
    val id: String,
    val images: Images,
    val rating: String,
    val title: String,
    val type: String,
    val url: String,
    val username: String
)

data class Images(
    val original: Original,
)

data class Original(
    val mp4: String,
    val url: String,
    val webp: String,
)