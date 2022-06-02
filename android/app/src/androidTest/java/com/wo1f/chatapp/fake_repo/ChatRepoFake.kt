/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.chatapp.fake_repo

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.model.chat.ChatRes
import com.wo1f.chatapp.domain.repo.ChatRepo
import com.wo1f.chatapp.utils.MockData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ChatRepoFake : ChatRepo {

    override suspend fun getAll(room: String): Flow<DataResource<List<ChatRes>>> {
        return flowOf(DataResource.Success(MockData.mockChatList))
    }
}
