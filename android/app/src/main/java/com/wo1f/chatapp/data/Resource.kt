/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.data

import com.wo1f.chatapp.R
import com.wo1f.chatapp.data.model.BaseResponse
import com.wo1f.chatapp.data.model.MsgCode
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

    abstract suspend fun apiCall(): Response<BaseResponse<T>>

    open suspend fun onSuccess(data: T? = null) {
    }
}

sealed class DataResource<out T> {
    class Success<T>(val data: T? = null) : DataResource<T>()
    class Error(
        val msg: ErrorMessage? = null,
        val dialogMsg: Int ? = null
    ) : DataResource<Nothing>()
    object Loading : DataResource<Nothing>()
    object Empty : DataResource<Nothing>()

    inline fun onSuccess(block: (T?) -> Unit): DataResource<T> = apply {
        if (this is Success) {
            block(data)
        }
    }

    inline fun onFailure(block: (Int?, Int?) -> Unit): DataResource<T> = apply {
        if (this is Error) {
            block(msg?.value, dialogMsg)
        }
    }

    inline fun onLoading(block: () -> Unit): DataResource<T> = apply {
        if (this is Loading) {
            block()
        }
    }
}

enum class ErrorMessage(val value: Int) {
    SOCKET_TIMEOUT(R.string.error_socket_time_out),
    UNKNOWN(R.string.error_unknown),
    UNAUTHENTICATED(R.string.error_unauthenticated),
    EXCEPTION(R.string.error_exception),
    NOT_FOUND(R.string.error_not_found)
}

fun <T> Response<BaseResponse<T>>.getResult(): DataResource<T> {
    return if (this.isSuccessful) {
        val body = this.body()
        if (body != null) {
            val msgCode = body.msgCode
            if (msgCode == 0) {
                DataResource.Success(body.data)
            } else {
                DataResource.Error(dialogMsg = MsgCode.getMessageRes(msgCode))
            }
        } else {
            DataResource.Success(null)
        }
    } else if (this.code() == 401) {
        DataResource.Error(ErrorMessage.UNAUTHENTICATED)
    } else if (this.code() == 404) {
        DataResource.Error(ErrorMessage.NOT_FOUND)
    } else {
        DataResource.Error(ErrorMessage.UNKNOWN)
    }
}
