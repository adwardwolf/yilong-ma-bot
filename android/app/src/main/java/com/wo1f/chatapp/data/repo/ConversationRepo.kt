package com.wo1f.chatapp.data.repo

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.DataSourceResult
import com.wo1f.chatapp.data.api.ConversationService
import com.wo1f.chatapp.data.model.conversation.ConversationRes
import com.wo1f.chatapp.data.model.conversation.ConversationRq
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
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

    suspend fun getConversations(name: String): Flow<DataResource<List<ConversationRes>>> {
        return object : DataSourceResult<List<ConversationRes>>() {
            override suspend fun apiCall(): Response<List<ConversationRes>> {
                return service.getConversations(name)
            }
        }.getResult()
    }
}
