package com.wo1f.data.collections

import com.wo1f.Connection.conversationsColl
import com.wo1f.Connection.conversationsDbColl
import com.wo1f.domain.models.ConversationDb
import com.wo1f.domain.models.ConversationRes
import com.wo1f.domain.models.ConversationRq
import com.wo1f.domain.models.toDbObject
import com.wo1f.plugins.DatabaseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.litote.kmongo.descending
import org.litote.kmongo.eq
import org.litote.kmongo.setTo
import org.litote.kmongo.updateOne

class ConversationCollection(private val dispatcher: CoroutineDispatcher) {

    suspend fun insertConversation(conversationRq: ConversationRq) {
        return withContext(dispatcher) {
            val result = conversationsDbColl.insertOne(conversationRq.toDbObject())
            if (!result.wasAcknowledged()) {
                throw DatabaseException("insertConversation failed")
            }
        }
    }

    suspend fun updateConversation(objectId: String, conversationRq: ConversationRq) {
        return withContext(dispatcher) {
            val result = conversationsDbColl.updateOne(
                filter = ConversationDb::id eq ObjectId(objectId),
                updates = arrayOf(
                    ConversationDb::question setTo conversationRq.question,
                    ConversationDb::answer setTo conversationRq.answer,
                    ConversationDb::category setTo conversationRq.category,
                )
            )
            if (!result.wasAcknowledged()) {
                throw DatabaseException("updateConversation failed")
            }
        }
    }

    suspend fun deleteConversation(objectId: String): ConversationRes {
        return withContext(dispatcher) {
            val result = conversationsColl.findOneAndDelete(ConversationDb::id eq ObjectId(objectId))
            result ?: throw DatabaseException("deleteConversation failed")
        }
    }

    suspend fun getAllConversations(): List<ConversationRes> {
        return withContext(dispatcher) {
            conversationsColl
                .find()
                .sort(descending(ConversationDb::createdAt))
                .toList()
        }
    }

    suspend fun getConversationByName(name: String): List<ConversationRes> {
        return withContext(dispatcher) {
            conversationsColl
                .find(ConversationDb::category eq name)
                .sort(descending(ConversationDb::createdAt))
                .toList()
        }
    }
}
