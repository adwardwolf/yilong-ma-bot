/**
 * @author Adwardwo1f
 * @created May 28, 2022
 */

package com.wo1f.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class ChatRes(
    @BsonId
    @SerialName("_id")
    val id: String,
    val roomName: String,
    val userName: String,
    val text: String? = null,
    val date: String
)
