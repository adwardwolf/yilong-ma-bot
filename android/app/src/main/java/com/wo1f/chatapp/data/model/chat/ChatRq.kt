/**
 * @author Adwardwo1f
 * @created May 30, 2022
 */

package com.wo1f.chatapp.data.model.chat

/**
 * Data class represents chat request
 */
data class ChatRq(
    val userName: String,
    val roomName: String,
    val text: String,
)
