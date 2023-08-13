package com.example.data.repository

import com.example.data.entity.GifEntity
import com.example.domain.model.*

fun GifEntity.toDomainGif(): Gif =
    Gif(
        id = id,
        thumbnail = images.original.url,
    )

fun GifEntity.toDomainGifDetail(): GifDetail = GifDetail(
    id = id,
    gif = images.original.url,
    username = username,
    title = title,
    rating = rating,
)
