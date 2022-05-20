package com.wo1f.chatapp.data

import java.time.LocalTime

data class Chat(
    val name: String,
    val text: String,
    val date: LocalTime,
    val type: Type
) {

    enum class Type {
        SENDER,
        RECEIVER,
        JOINED,
        LEFT
    }
}

data class JoinChat(
    val userName: String,
    val roomName: String
)

data class ChatRes(
    val userName: String,
    val roomName: String,
    val text: String,
)