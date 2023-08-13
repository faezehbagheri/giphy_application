package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.data.datasource.GifDataSource
import com.example.data.datasource.GifsPagingSource
import com.example.domain.model.Gif
import com.example.domain.model.GifDetail
import com.example.domain.repository.GifsRepository
import com.example.libraries.common.result.GetResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val LIMIT = 30

class GifsRepositoryImpl @Inject constructor(
    private val gifDataSource: GifDataSource,
) : GifsRepository {
    override fun getTrendingGifs() = Pager(
        config = PagingConfig(
            pageSize = LIMIT,
        ),
        pagingSourceFactory = {
            GifsPagingSource(gifDataSource)
        }
    ).flow.map { it.map { data -> data.toDomainGif() } }

    override fun getGifById(id: String): Flow<GetResult<GifDetail>> = flow {
        val gif = gifDataSource.getGifDetail(id).toDomainGifDetail()
        emit(GetResult.Success(gif))
    }.catch {
        GetResult.Error(it)
    }.flowOn(Dispatchers.IO)

}
