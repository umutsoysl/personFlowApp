package com.umut.soysal.personapp.core.base

sealed class NetworkResult<T>(
    val data: T? = null,
    val error: String = "Unknown"
) {

    class Success<T>(data: T?) : NetworkResult<T>(data)

    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)

    class Loading<T>(data: T? = null) : NetworkResult<T>(data)

    val dataOrNull: T?
        inline get() = if (this is Success) data else null

    val errorOrNull: String?
        inline get() = if (this is Error) error else null

}