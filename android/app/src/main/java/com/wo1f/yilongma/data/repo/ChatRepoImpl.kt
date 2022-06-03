/**
 * @author Adwardwo1f
 * @created May 28, 2022
 */

package com.wo1f.yilongma.data.repo

import com.wo1f.yilongma.data.DataResource
import com.wo1f.yilongma.data.DataResult
import com.wo1f.yilongma.data.api.ChatService
import com.wo1f.yilongma.data.model.BaseResponse
import com.wo1f.yilongma.data.model.chat.ChatRes
import com.wo1f.yilongma.domain.repo.ChatRepo
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
