package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.datasource.GifDataSource
import com.example.data.mapper.toDomainGif
import com.example.data.mapper.toDomainGifDetail
import com.example.domain.model.Gif
import com.example.domain.model.GifDetail
import com.example.domain.repository.GifsRepository
import com.example.libraries.common.result.GetResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

const val LIMIT = 30

class GifsRepositoryImpl @Inject constructor(
    private val gifDataSource: GifDataSource,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : GifsRepository {
    override fun getTrendingGifs(): Flow<PagingData<Gif>> = Pager(
        config = PagingConfig(
            pageSize = LIMIT,
            initialLoadSize = LIMIT,
        ),
        pagingSourceFactory = { gifDataSource.getGifs() }
    ).flow.map { it.map { data -> data.toDomainGif() } }

    override suspend fun getGifDetail(id: String) = withContext(coroutineContext) {
        flow<GetResult<GifDetail>> {
            val gif = gifDataSource.getGifDetail(id).toDomainGifDetail()
            emit(GetResult.Success(gif))
        }.catch {
            emit(GetResult.Error(it))
        }
    }

}
