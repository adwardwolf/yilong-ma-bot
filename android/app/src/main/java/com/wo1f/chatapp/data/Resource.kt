package com.wo1f.chatapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException

abstract class DataSourceResult<T> {

    suspend fun getResult(): Flow<DataResource<T>> {
        return flow {
            emit(DataResource.Loading)
            try {
                val result = withContext(Dispatchers.IO) {
                    apiCall().getResult()
                }
                if (result is DataResource.Success) {
                    onSuccess(result.data)
                }
                emit(result)
            } catch (exception: SocketTimeoutException) {
                emit(DataResource.Error(ErrorMessage.SOCKET_TIMEOUT))
            } catch (exception: Exception) {
                exception.printStackTrace()
                emit(DataResource.Error(ErrorMessage.EXCEPTION))
            }
        }
    }

    abstract suspend fun apiCall(): Response<T>

    open suspend fun onSuccess(data: T? = null) {
    }
}

sealed class DataResource<out T> {
    class Success<T>(val data: T? = null) : DataResource<T>()
    class Error(val message: ErrorMessage) : DataResource<Nothing>()
    object Loading : DataResource<Nothing>()
    object Empty : DataResource<Nothing>()

    inline fun onSuccess(block: (T?) -> Unit): DataResource<T> = apply {
        if (this is Success) {
            block(data)
        }
    }

    inline fun onFailure(block: (String) -> Unit): DataResource<T> = apply {
        if (this is Error) {
            block(message.value)
        }
    }

    inline fun onLoading(block: () -> Unit): DataResource<T> = apply {
        if (this is Loading) {
            block()
        }
    }
}

enum class ErrorMessage(val value: String) {
    SOCKET_TIMEOUT("Socket timeout error"),
    UNKNOWN("Unknown error occurred"),
    UNAUTHENTICATED("Unauthenticated"),
    EXCEPTION("Exception error"),
    NOT_FOUND("Not found error")
}

fun <T> Response<T>.getResult(): DataResource<T> {
    return if (this.code() == 200 || this.code() == 201 || this.code() == 204) {
        DataResource.Success(this.body())
    } else if (this.code() == 401) {
        DataResource.Error(ErrorMessage.UNAUTHENTICATED)
    } else if (this.code() == 404) {
        DataResource.Error(ErrorMessage.NOT_FOUND)
    } else {
        DataResource.Error(ErrorMessage.UNKNOWN)
    }
}
