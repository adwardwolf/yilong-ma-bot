/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

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
import org.litote.kmongo.updateMany
import org.litote.kmongo.updateOne

class ConversationCollection(private val dispatcher: CoroutineDispatcher) {

    suspend fun insertOne(conversationRq: ConversationRq) {
        return withContext(dispatcher) {
            val result = conversationsDbColl.insertOne(conversationRq.toDbObject())
            if (!result.wasAcknowledged()) {
                throw DatabaseException("insertOne failed")
            }
        }
    }

    suspend fun updateOne(id: String, conversationRq: ConversationRq) {
        return withContext(dispatcher) {
            val result = conversationsDbColl.updateOne(
                filter = ConversationDb::id eq ObjectId(id),
                updates = arrayOf(
                    ConversationDb::question setTo conversationRq.question,
                    ConversationDb::answer setTo conversationRq.answer,
                    ConversationDb::category setTo conversationRq.category,
                )
            )
            if (!result.wasAcknowledged()) {
                throw DatabaseException("updateOne failed")
            }
        }
    }

    suspend fun updateToNewCategory(old: String, new: String) = withContext(dispatcher) {
        val result = conversationsDbColl.updateMany(
            filter = ConversationDb::category eq old,
            updates = arrayOf(ConversationDb::category setTo new)
        )
        if (!result.wasAcknowledged()) {
            throw DatabaseException("updateToNewCategory failed")
        }
    }

    suspend fun deleteOne(id: String): ConversationRes = withContext(dispatcher) {
        conversationsColl.findOneAndDelete(ConversationDb::id eq ObjectId(id))
            ?: throw DatabaseException("deleteOne failed")
    }

    suspend fun deleteByCategory(category: String) = withContext(dispatcher) {
        val result = conversationsColl.deleteMany(ConversationDb::category eq category)
        if (!result.wasAcknowledged()) {
            throw DatabaseException("deleteByCategory failed")
        }
    }

    suspend fun getAll(): List<ConversationRes> {
        return withContext(dispatcher) {
            conversationsColl
                .find()
                .sort(descending(ConversationDb::createdAt))
                .toList()
        }
    }

    suspend fun getByCategory(name: String): List<ConversationRes> {
        return withContext(dispatcher) {
            conversationsColl
                .find(ConversationDb::category eq name)
                .sort(descending(ConversationDb::createdAt))
                .toList()
        }
    }
}
