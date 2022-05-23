package com.wo1f.data.datasource

import com.wo1f.data.collections.ConversationCollection
import com.wo1f.domain.models.ConversationRq
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ConversationDataSource(
    private val dispatcher: CoroutineDispatcher,
    private val collection: ConversationCollection
) {

    suspend fun insertConversation(conversationRq: ConversationRq): Boolean {
        return withContext(dispatcher) {
            collection.insertConversation(conversationRq)
        }
    }

    suspend fun updateConversation(objectId: String, conversationRq: ConversationRq): Boolean {
        return withContext(dispatcher) {
            collection.updateConversation(objectId, conversationRq)
        }
    }

    suspend fun deleteConversation(objectId: String): Boolean {
        return withContext(dispatcher) {
            collection.deleteConversation(objectId)
        }
    }
}
