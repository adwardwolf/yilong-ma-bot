/**
 * @author Adwardwo1f
 * @created May 30, 2022
 */

package com.wo1f.yilongma.data.model.chat

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

/**
 * Data class represents chat response
 */
data class ChatRes(
    @SerializedName("_id")
    val id: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("roomName")
    val roomName: String,
    @SerializedName("text")
    val text: String?,
    @SerializedName("date")
    val date: String? = null,
)

/**
 * Map chat response to chat domain
 * @param name Name of current user
 */
fun ChatRes.toChat(name: String): Chat {
    return Chat(
        id = this.id,
        name = this.userName,
        text = this.text,
        type = if (name == this.userName)
            Chat.Type.SENDER
        else
            Chat.Type.RECEIVER,
        date = LocalDateTime.now().toString()
    )
}
