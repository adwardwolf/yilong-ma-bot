package com.wo1f.chatapp.data.api

import com.wo1f.chatapp.data.model.ConversationRq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ConversationService {

    @POST("conversation")
    suspend fun addConversation(
        @Body body: ConversationRq
    ): Response<Unit>

    @PATCH("conversation/[id]")
    suspend fun updateConversation(
        @Path("id") id: String,
        @Body body: ConversationRq
    ): Response<Unit>

    @DELETE("conversation/[id]")
    suspend fun deleteConversation(@Path("id") id: String)
}
