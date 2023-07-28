package com.example.giphyapplication.data.utils

import com.example.giphyapplication.common.result.GetResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate

abstract class NetworkResult<TResult, TRemote> {
    protected abstract suspend fun callRemote(): TRemote
    protected abstract fun mapRemoteToResult(data: TRemote): TResult

    @ExperimentalCoroutinesApi
    fun asFlow(): Flow<GetResult<TResult>> =
        flow {
            try {
                emit(GetResult.Loading)
                val response = callRemote()
                emit(GetResult.Success(mapRemoteToResult(response)))
            } catch (ex: Exception) {
                emit(GetResult.Error(ex))
            }
        }.catch { ex -> emit(GetResult.Error(ex)) }
            .conflate()
            .flowOn(Dispatchers.IO)
}

@ExperimentalCoroutinesApi
fun <TResult, TRemote> networkResult(
    callRemote: suspend () -> TRemote,
    mapRemoteToResult: (data: TRemote) -> TResult,
): Flow<GetResult<TResult>> = object : NetworkResult<TResult, TRemote>() {
    override suspend fun callRemote(): TRemote = callRemote()

    override fun mapRemoteToResult(data: TRemote): TResult = mapRemoteToResult(data)
}.asFlow()

