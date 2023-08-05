package com.example.data.utils

import com.example.common.result.GetResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate

abstract class NetworkResult<TResult, TRemote> {
    protected abstract suspend fun callRemote(): TRemote
    protected abstract fun mapRemoteToResult(data: TRemote): TResult

    @ExperimentalCoroutinesApi
    fun asFlow(): Flow<com.example.common.result.GetResult<TResult>> =
        flow {
            try {
                emit(com.example.common.result.GetResult.Loading)
                val response = callRemote()
                emit(com.example.common.result.GetResult.Success(mapRemoteToResult(response)))
            } catch (ex: Exception) {
                emit(com.example.common.result.GetResult.Error(ex))
            }
        }.catch { ex -> emit(com.example.common.result.GetResult.Error(ex)) }
            .conflate()
            .flowOn(Dispatchers.IO)
}

@ExperimentalCoroutinesApi
fun <TResult, TRemote> networkResult(
    callRemote: suspend () -> TRemote,
    mapRemoteToResult: (data: TRemote) -> TResult,
): Flow<com.example.common.result.GetResult<TResult>> = object : NetworkResult<TResult, TRemote>() {
    override suspend fun callRemote(): TRemote = callRemote()

    override fun mapRemoteToResult(data: TRemote): TResult = mapRemoteToResult(data)
}.asFlow()

