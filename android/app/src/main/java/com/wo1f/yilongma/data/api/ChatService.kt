/**
 * @author Adwardwo1f
 * @created May 28, 2022
 */

package com.wo1f.yilongma.data.api

import com.wo1f.yilongma.data.model.BaseResponse
import com.wo1f.yilongma.data.model.chat.ChatRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatService {

    @GET("chats/{room}")
    suspend fun getAll(@Path("room") room: String): Response<BaseResponse<List<ChatRes>>>
}
