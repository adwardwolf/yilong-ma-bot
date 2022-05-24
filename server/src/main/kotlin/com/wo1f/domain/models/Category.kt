package com.wo1f.domain.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant
import java.util.Date

@Serializable
data class CategoryRes(
    @BsonId
    val id: String,
    val name: String,
    val createdAt: String,
    val count: Long
)

@Serializable
data class CategoryDb(
    @BsonId
    @Contextual
    val id: ObjectId,
    val name: String,
    @Contextual
    val createdAt: Date,
    val count: Long
)

@Serializable
data class CategoryRq(
    val name: String
)

fun CategoryRq.toDbObject(): CategoryDb {
    return CategoryDb(
        id = ObjectId.get(),
        name = this.name,
        count = 0,
        createdAt = Date.from(Instant.now())
    )
}
