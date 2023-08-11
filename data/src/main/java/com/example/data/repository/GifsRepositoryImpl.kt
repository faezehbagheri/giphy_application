package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.common.result.GetResult
import com.example.data.datasource.GifsPagingSource
import com.example.data.remote.GifsService
import com.example.domain.model.Gif
import com.example.domain.repository.GifsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val LIMIT = 15

class GifsRepositoryImpl @Inject constructor(
    private val gifsService: GifsService,
) : GifsRepository {
    override fun getTrendingGifs() =
        Pager(
            config = PagingConfig(
                pageSize = LIMIT,
            ),
            pagingSourceFactory = {
                GifsPagingSource(gifsService)
            }
        ).flow.map { it.map { data -> data.toDomain() } }

    override fun getGifById(id: String): Flow<GetResult<Gif>> = flow {
        val response = gifsService.getGifById(id)
        val gif = response.gifs.map { it.toDomain() }.first()
        emit(GetResult.Success(gif))
    }.catch {
        GetResult.Error(it)
    }.flowOn(Dispatchers.IO)

}