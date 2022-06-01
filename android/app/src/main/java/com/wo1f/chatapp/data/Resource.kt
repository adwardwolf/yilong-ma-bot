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

abstract class DataResult<T> {

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
                emit(DataResource.Error(ErrorMsg.SOCKET_TIMEOUT))
            } catch (exception: Exception) {
                exception.printStackTrace()
                emit(DataResource.Error(ErrorMsg.EXCEPTION))
            }
        }
    }

    abstract suspend fun apiCall(): Response<BaseResponse<T>>

    open suspend fun onSuccess(data: T? = null) {
    }
}

sealed class DataResource<out T> {

    class Success<T>(val data: T? = null) : DataResource<T>()

    /**
     * @property msg A Generic error message
     * @property dialogMsg A Specific error message that should show in the dialog
     */
    class Error(
        val msg: ErrorMsg? = null,
        val dialogMsg: Int? = null
    ) : DataResource<Nothing>()

    object Loading : DataResource<Nothing>()
//
//    object Empty : DataResource<Nothing>()

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

/**
 * Generics error message
 */
enum class ErrorMsg(val value: Int) {
    SOCKET_TIMEOUT(R.string.error_socket_time_out),
    UNKNOWN(R.string.error_unknown),
    UNAUTHENTICATED(R.string.error_unauthenticated),
    EXCEPTION(R.string.error_exception),
    NOT_FOUND(R.string.error_not_found)
}

/**
 * Returns [DataResource] based on [Response.code]
 */
private fun <T> Response<BaseResponse<T>>.getResult(): DataResource<T> {
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
        DataResource.Error(ErrorMsg.UNAUTHENTICATED)
    } else if (this.code() == 404) {
        DataResource.Error(ErrorMsg.NOT_FOUND)
    } else {
        DataResource.Error(ErrorMsg.UNKNOWN)
    }
}
