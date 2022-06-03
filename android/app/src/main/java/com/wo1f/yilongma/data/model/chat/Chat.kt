/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.data.model.chat

/**
 * Data class represents domain chat model
 * @property name Name of the sender
 */
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
