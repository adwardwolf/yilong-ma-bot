package com.wo1f.chatapp.data.repo

import com.wo1f.chatapp.data.api.ConversationService
import com.wo1f.chatapp.data.model.ConversationRq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationRepo @Inject constructor(private val service: ConversationService) {

    suspend fun addConversation(body: ConversationRq): Flow<DataResource<*>> {
        return object : DataSourceResult<Unit>() {
            override suspend fun apiCall(): Response<Unit> {
                return service.addConversation(body)
            }
        }.getResult()
    }
}

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
    class Success<out T>(val data: T? = null) : DataResource<T>()
    class Error(val errorMessage: ErrorMessage) : DataResource<Nothing>()
    object Loading : DataResource<Nothing>()
    object Empty : DataResource<Nothing>()
}

enum class ErrorMessage {
    SOCKET_TIMEOUT,
    UNKNOWN,
    UNAUTHENTICATED,
    EXCEPTION,
    NOT_FOUND
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
