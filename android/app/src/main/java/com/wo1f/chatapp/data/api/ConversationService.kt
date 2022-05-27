package com.wo1f.chatapp.data.api

import com.wo1f.chatapp.data.model.BaseResponse
import com.wo1f.chatapp.data.model.conversation.ConversationRq
import com.wo1f.chatapp.data.model.conversation.GetConversationRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ConversationService {

    @POST("conversation")
    suspend fun addConversation(
        @Body body: ConversationRq
    ): Response<BaseResponse<Unit>>

    @PATCH("conversation/{id}")
    suspend fun updateConversation(
        @Path("id") id: String,
        @Body body: ConversationRq
    ): Response<BaseResponse<Unit>>

    @DELETE("conversation/{id}")
    suspend fun deleteConversation(@Path("id") id: String): Response<BaseResponse<Unit>>

    @GET("{name}/conversation")
    suspend fun getConversations(
        @Path("name") name: String
    ): Response<BaseResponse<GetConversationRes>>
}
