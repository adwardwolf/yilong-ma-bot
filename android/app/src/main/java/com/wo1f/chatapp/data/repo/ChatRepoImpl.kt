/**
 * @author Adwardwo1f
 * @created May 28, 2022
 */

package com.wo1f.chatapp.data.repo

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.DataResult
import com.wo1f.chatapp.data.api.ChatService
import com.wo1f.chatapp.data.model.BaseResponse
import com.wo1f.chatapp.data.model.chat.ChatRes
import com.wo1f.chatapp.domain.repo.ChatRepo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepoImpl @Inject constructor(private val service: ChatService) : ChatRepo {

    override suspend fun getAll(room: String): Flow<DataResource<List<ChatRes>>> {
        return object : DataResult<List<ChatRes>>() {
            override suspend fun apiCall(): Response<BaseResponse<List<ChatRes>>> {
                return service.getAll(room)
            }
        }.getResult()
    }
}
