package com.example.libraries.common.result

sealed class GetResult<out T> {
    object Loading : GetResult<Nothing>()
    data class Success<out T>(val data: T) : GetResult<T>()
    data class Error(val throwable: Throwable) : GetResult<Nothing>()
}
