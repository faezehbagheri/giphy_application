package com.example.giphyapplication.common.result

sealed class GetResult<out T>(
    val data: T? = null,
    val message: Throwable? = null
) {
    class Success<T>(data: T) : GetResult<T>(data)
    class Loading<T>(data: T? = null) : GetResult<T>(data)
    class Error<T>(message: Throwable, data: T? = null) : GetResult<T>(data, message)
}
