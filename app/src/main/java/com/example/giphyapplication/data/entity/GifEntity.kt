package com.example.giphyapplication.data.entity


import com.google.gson.annotations.SerializedName

data class GifEntity(
    @SerializedName("data")
    val data: List<DataEntity>,
    @SerializedName("meta")
    val meta: MetaEntity,
    @SerializedName("pagination")
    val pagination: PaginationEntity
)

data class PaginationEntity(
    @SerializedName("count")
    val count: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("total_count")
    val totalCount: Int
)

data class MetaEntity(
    @SerializedName("msg")
    val msg: String,
    @SerializedName("response_id")
    val responseId: String,
    @SerializedName("status")
    val status: Int
)

data class DataEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: ImagesEntity,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("username")
    val username: String
)

data class ImagesEntity(
    @SerializedName("original")
    val original: OriginalEntity,
)

data class OriginalEntity(
    @SerializedName("mp4")
    val mp4: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("webp")
    val webp: String,
)