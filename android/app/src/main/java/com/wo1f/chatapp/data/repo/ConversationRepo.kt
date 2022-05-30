/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.data.repo

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.DataResult
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

    suspend fun add(body: ConversationRq): Flow<DataResource<*>> {
        return object : DataResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.addConversation(body)
            }
        }.getResult()
    }

    /**
     * Get conversations by category name
     */
    suspend fun getAll(name: String): Flow<DataResource<GetConversationRes>> {
        return object : DataResult<GetConversationRes>() {
            override suspend fun apiCall(): Response<BaseResponse<GetConversationRes>> {
                return service.getAll(name)
            }
        }.getResult()
    }

    suspend fun update(id: String, body: ConversationRq): Flow<DataResource<*>> {
        return object : DataResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.update(id, body)
            }
        }.getResult()
    }

    suspend fun delete(id: String): Flow<DataResource<*>> {
        return object : DataResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.delete(id)
            }
        }.getResult()
    }
}
