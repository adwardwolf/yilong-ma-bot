package com.wo1f.data.datasource

import com.wo1f.data.collections.CategoryCollection
import com.wo1f.data.collections.ConversationCollection
import com.wo1f.domain.models.ConversationRes
import com.wo1f.domain.models.ConversationRq

class ConversationDataSource(
    private val collection: ConversationCollection,
    private val categoryCollection: CategoryCollection
) {

    suspend fun insertConversation(conversationRq: ConversationRq) {
        collection.insertConversation(conversationRq)
        categoryCollection.increaseCategoryCount(conversationRq.category)
    }

    suspend fun updateConversation(objectId: String, conversationRq: ConversationRq) {
        collection.updateConversation(objectId, conversationRq)
    }

    suspend fun deleteConversation(objectId: String) {
        val conversation = collection.deleteConversation(objectId)
        categoryCollection.decreaseCategoryCount(conversation.category)
    }

    suspend fun getConversations(): List<ConversationRes> {
        return collection.getAllConversations()
    }

    suspend fun getConversationByCategory(name: String): List<ConversationRes> {
        return collection.getConversationByName(name)
    }
}
