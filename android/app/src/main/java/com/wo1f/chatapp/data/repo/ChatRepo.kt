/**
 * @author Adwardwo1f
 * @created May 28, 2022
 */

package com.wo1f.chatapp.data.repo

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.DataSourceResult
import com.wo1f.chatapp.data.api.ChatService
import com.wo1f.chatapp.data.model.BaseResponse
import com.wo1f.chatapp.data.model.ChatRes
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepo @Inject constructor(private val service: ChatService) {

    suspend fun getAll(roomName: String): Flow<DataResource<List<ChatRes>>> {
        return object : DataSourceResult<List<ChatRes>>() {
            override suspend fun apiCall(): Response<BaseResponse<List<ChatRes>>> {
                return service.getAll(roomName)
            }
        }.getResult()
    }
}
