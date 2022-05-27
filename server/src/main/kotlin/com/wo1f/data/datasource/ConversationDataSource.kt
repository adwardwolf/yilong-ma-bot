/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.data.datasource

import com.wo1f.data.collections.CategoryCollection
import com.wo1f.data.collections.ConversationCollection
import com.wo1f.domain.models.ConversationRq
import com.wo1f.domain.models.GetConversationRes

class ConversationDataSource(
    private val collection: ConversationCollection,
    private val categoryCollection: CategoryCollection
) {

    suspend fun insertOne(conversationRq: ConversationRq) {
        collection.insertOne(conversationRq)
        categoryCollection.increaseCount(conversationRq.category, 1)
    }

    suspend fun updateOne(objectId: String, conversationRq: ConversationRq) {
        collection.updateOne(objectId, conversationRq)
    }

    suspend fun deleteOne(objectId: String) {
        val conversation = collection.deleteOne(objectId)
        categoryCollection.decreaseCount(conversation.category, -1)
    }

    suspend fun getAll(): GetConversationRes {
        val conversations = collection.getAll()
        val category = categoryCollection.getByName("all")
        return GetConversationRes(category, conversations)
    }

    suspend fun getByCategory(name: String): GetConversationRes {
        val conversations = collection.getByCategory(name)
        val category = categoryCollection.getByName(name)
        return GetConversationRes(category, conversations)
    }
}
