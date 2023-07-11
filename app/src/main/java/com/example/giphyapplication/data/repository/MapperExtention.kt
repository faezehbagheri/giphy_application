package com.example.giphyapplication.data.repository

import com.example.giphyapplication.data.entity.*
import com.example.giphyapplication.domain.model.*

fun GifResponseEntity.toDomain(): GifResponse =
    GifResponse(
        gifs.map { it.toDomain() }, meta.toDomain(), pagination.toDomain()
    )

fun PaginationEntity.toDomain(): Pagination =
    Pagination(
        count, offset, totalCount
    )

fun MetaEntity.toDomain(): Meta =
    Meta(
        msg, responseId, status
    )

fun GifEntity.toDomain(): Gif =
    Gif(
        id,
        images.toDomain(),
        rating,
        title,
        type,
        url,
        username
    )

fun ImagesEntity.toDomain(): Images = Images(
    original.toDomain(),
)

fun OriginalEntity.toDomain(): Original = Original(
    mp4,
    url,
    webp,
)