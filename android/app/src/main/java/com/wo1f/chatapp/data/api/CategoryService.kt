package com.wo1f.chatapp.data.api

import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.model.category.CategoryRq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoryService {

    @GET("category")
    suspend fun getCategories(): Response<List<CategoryRes>>

    @POST("category")
    suspend fun insertCategory(@Body body: CategoryRq): Response<Unit>
}
