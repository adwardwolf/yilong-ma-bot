/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.yilongma.fake_repo

import com.wo1f.yilongma.data.DataResource
import com.wo1f.yilongma.data.model.chat.ChatRes
import com.wo1f.yilongma.domain.repo.ChatRepo
import com.wo1f.yilongma.utils.MockData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ChatRepoFake : ChatRepo {

    override suspend fun getAll(room: String): Flow<DataResource<List<ChatRes>>> {
        return flowOf(DataResource.Success(MockData.mockChatList))
    }
}
