/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.yilongma.domain.repo

import com.wo1f.yilongma.data.DataResource
import com.wo1f.yilongma.data.model.conversation.ConversationRq
import com.wo1f.yilongma.data.model.conversation.GetConversationRes
import kotlinx.coroutines.flow.Flow

interface ConversationRepo {

    suspend fun insert(body: ConversationRq): Flow<DataResource<*>>

    /**
     * Get conversations by category name
     */
    suspend fun getAll(name: String): Flow<DataResource<GetConversationRes>>

    suspend fun update(id: String, body: ConversationRq): Flow<DataResource<*>>

    suspend fun delete(id: String): Flow<DataResource<*>>
}
