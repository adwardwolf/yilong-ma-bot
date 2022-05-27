package com.wo1f.chatapp.data.repo

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.DataSourceResult
import com.wo1f.chatapp.data.api.ConversationService
import com.wo1f.chatapp.data.model.BaseResponse
import com.wo1f.chatapp.data.model.conversation.ConversationRq
import com.wo1f.chatapp.data.model.conversation.GetConversationRes
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationRepo @Inject constructor(private val service: ConversationService) {

    suspend fun addConversation(body: ConversationRq): Flow<DataResource<*>> {
        return object : DataSourceResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.addConversation(body)
            }
        }.getResult()
    }

    suspend fun getConversations(name: String): Flow<DataResource<GetConversationRes>> {
        return object : DataSourceResult<GetConversationRes>() {
            override suspend fun apiCall(): Response<BaseResponse<GetConversationRes>> {
                return service.getConversations(name)
            }
        }.getResult()
    }

    suspend fun updateConversation(id: String, body: ConversationRq): Flow<DataResource<*>> {
        return object : DataSourceResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.updateConversation(id, body)
            }
        }.getResult()
    }

    suspend fun deleteConversation(id: String): Flow<DataResource<*>> {
        return object : DataSourceResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.deleteConversation(id)
            }
        }.getResult()
    }
}
