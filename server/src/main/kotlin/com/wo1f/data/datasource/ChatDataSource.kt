/**
 * @author Adwardwo1f
 * @created May 28, 2022
 */

package com.wo1f.data.datasource

import com.wo1f.data.collections.ChatCollection
import com.wo1f.domain.models.ChatRes

class ChatDataSource(private val collection: ChatCollection) {

    suspend fun getAll(roomName: String): List<ChatRes> {
        return collection.getAll(roomName)
    }
}
