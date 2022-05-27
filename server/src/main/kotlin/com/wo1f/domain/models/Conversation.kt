package com.wo1f.domain.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant
import java.util.Date

@Serializable
data class ConversationRes(
    @BsonId
    val id: String,
    val question: String,
    val answer: String,
    val category: String,
    val isTrained: Boolean,
    val createdAt: String
)

@Serializable
data class ConversationDb(
    @Contextual
    @BsonId
    val id: ObjectId,
    val question: String,
    val answer: String,
    val category: String,
    val isTrained: Boolean,
    @Contextual
    val createdAt: Date
)

@Serializable
data class ConversationRq(
    var question: String,
    var answer: String,
    val category: String
)

@Serializable
data class GetConversationRes(
    val category: CategoryRes,
    val conversations: List<ConversationRes>
)

fun ConversationRq.toDbObject(): ConversationDb {
    return ConversationDb(
        id = ObjectId.get(),
        question = this.question,
        answer = this.answer,
        category = this.category,
        isTrained = false,
        createdAt = Date.from(Instant.now())
    )
}
