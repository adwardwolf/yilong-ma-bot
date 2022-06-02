/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.chatapp.domain.repo

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.model.chat.ChatRes
import kotlinx.coroutines.flow.Flow

interface ChatRepo {

    suspend fun getAll(room: String): Flow<DataResource<List<ChatRes>>>
}
