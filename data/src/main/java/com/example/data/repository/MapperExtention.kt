package com.example.data.repository

import com.example.data.entity.GifEntity
import com.example.data.entity.GifResponseEntity
import com.example.data.entity.ImagesEntity
import com.example.data.entity.MetaEntity
import com.example.data.entity.OriginalEntity
import com.example.data.entity.PaginationEntity
import com.example.domain.model.*

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