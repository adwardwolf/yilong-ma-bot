package com.wo1f.chatapp.data.repo

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.DataSourceResult
import com.wo1f.chatapp.data.api.CategoryService
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.model.category.CategoryRq
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepo @Inject constructor(private val service: CategoryService) {

    suspend fun getCategories(): Flow<DataResource<List<CategoryRes>>> {
        return object : DataSourceResult<List<CategoryRes>>() {
            override suspend fun apiCall(): Response<List<CategoryRes>> {
                return service.getCategories()
            }
        }.getResult()
    }

    suspend fun addCategory(body: CategoryRq): Flow<DataResource<Unit>> {
        return object : DataSourceResult<Unit>() {
            override suspend fun apiCall(): Response<Unit> {
                return service.insertCategory(body)
            }
        }.getResult()
    }
}
