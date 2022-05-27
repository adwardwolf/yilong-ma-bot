package com.wo1f.chatapp.data.api

import com.wo1f.chatapp.data.model.BaseResponse
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.model.category.CategoryRq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryService {

    @GET("category")
    suspend fun getAll(): Response<BaseResponse<List<CategoryRes>>>

    @POST("category")
    suspend fun insert(@Body body: CategoryRq): Response<BaseResponse<Unit>>

    @PATCH("category/{name}")
    suspend fun update(
        @Path("name") name: String,
        @Body body: CategoryRq
    ): Response<BaseResponse<Unit>>

    @DELETE("category/{name}")
    suspend fun delete(@Path("name") name: String): Response<BaseResponse<Unit>>
}
