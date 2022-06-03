/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.data.repo

import com.wo1f.yilongma.data.DataResource
import com.wo1f.yilongma.data.DataResult
import com.wo1f.yilongma.data.api.CategoryService
import com.wo1f.yilongma.data.model.BaseResponse
import com.wo1f.yilongma.data.model.category.CategoryRes
import com.wo1f.yilongma.data.model.category.CategoryRq
import com.wo1f.yilongma.domain.repo.CategoryRepo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepoImpl @Inject constructor(
    private val service: CategoryService
) : CategoryRepo {

    override suspend fun getAll(): Flow<DataResource<List<CategoryRes>>> {
        return object : DataResult<List<CategoryRes>>() {
            override suspend fun apiCall(): Response<BaseResponse<List<CategoryRes>>> {
                return service.getAll()
            }
        }.getResult()
    }

    override suspend fun insert(body: CategoryRq): Flow<DataResource<Unit>> {
        return object : DataResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.insert(body)
            }
        }.getResult()
    }

    override suspend fun update(name: String, body: CategoryRq): Flow<DataResource<Unit>> {
        return object : DataResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.update(name, body)
            }
        }.getResult()
    }

    override suspend fun delete(name: String): Flow<DataResource<Unit>> {
        return object : DataResult<Unit>() {
            override suspend fun apiCall(): Response<BaseResponse<Unit>> {
                return service.delete(name)
            }
        }.getResult()
    }
}
