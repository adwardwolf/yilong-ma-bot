package com.wo1f.data.collections

import com.wo1f.Connection.conversationsDb
import com.wo1f.domain.models.ConversationDb
import com.wo1f.domain.models.ConversationRq
import com.wo1f.domain.models.toDbObject
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.setTo
import org.litote.kmongo.updateOne

class ConversationCollection {

    suspend fun insertConversation(conversationRq: ConversationRq): Boolean {
        val result = conversationsDb.insertOne(conversationRq.toDbObject())
        return result.wasAcknowledged()
    }

    suspend fun updateConversation(objectId: String, conversationRq: ConversationRq): Boolean {
        val result = conversationsDb.updateOne(
            ConversationDb::id eq ObjectId(objectId),
            arrayOf(
                ConversationDb::question setTo conversationRq.question,
                ConversationDb::answer setTo conversationRq.answer,
                ConversationDb::category setTo conversationRq.category,
            )
        )
        return result.wasAcknowledged()
    }

    suspend fun deleteConversation(objectId: String): Boolean {
        val result = conversationsDb.deleteOne(ConversationDb::id eq ObjectId(objectId))
        return result.wasAcknowledged()
    }
}
