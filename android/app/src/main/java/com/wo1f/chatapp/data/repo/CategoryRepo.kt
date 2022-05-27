package com.wo1f.chatapp.data.repo

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.DataSourceResult
import com.wo1f.chatapp.data.api.CategoryService
import com.wo1f.chatapp.data.model.BaseResponse
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.model.category.CategoryRq
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepo @Inject constructor(private val service: CategoryService) {

    suspend fun getAll(): Flow<DataResource<List<CategoryRes>>> {
        return object : DataSourceResult<List<CategoryRes>>() {
            override suspend fun apiCall(): Response<BaseResponse<List<CategoryRes>>> {
                return service.getAll()
            }
        }.getResult()
    }

    suspend fun insert(body: CategoryRq): Flow<DataResource<Unit>> {
        return object : DataSourceResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.insert(body)
            }
        }.getResult()
    }

    suspend fun update(name: String, body: CategoryRq): Flow<DataResource<Unit>> {
        return object : DataSourceResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.update(name, body)
            }
        }.getResult()
    }

    suspend fun delete(name: String): Flow<DataResource<Unit>> {
        return object : DataSourceResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.delete(name)
            }
        }.getResult()
    }
}
