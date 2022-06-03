/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.yilongma.domain.repo

import com.wo1f.yilongma.data.DataResource
import com.wo1f.yilongma.data.model.chat.ChatRes
import kotlinx.coroutines.flow.Flow

interface ChatRepo {

    suspend fun getAll(room: String): Flow<DataResource<List<ChatRes>>>
}
