/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Chat(
    val id: String,
    val name: String,
    val text: String?,
    val type: Type,
    val date: String
) {

    enum class Type {
        SENDER,
        RECEIVER
    }
}

data class JoinChat(
    val userName: String,
    val roomName: String
)

data class ChatRq(
    val userName: String,
    val roomName: String,
    val text: String,
)

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
