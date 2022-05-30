/**
 * @author Adwardwo1f
 * @created May 28, 2022
 */

package com.wo1f.data.collections

import com.wo1f.Connection.chatsColl
import com.wo1f.domain.models.ChatRes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.litote.kmongo.eq

class ChatCollection(private val dispatcher: CoroutineDispatcher) {

    /**
     * Get all chats from database
     */
    suspend fun getAll(roomName: String): List<ChatRes> = withContext(dispatcher) {
        chatsColl.find(ChatRes::roomName eq roomName).toList()
    }
}
