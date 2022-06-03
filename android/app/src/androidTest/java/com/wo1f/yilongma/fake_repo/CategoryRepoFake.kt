/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.yilongma.fake_repo

import com.wo1f.yilongma.data.DataResource
import com.wo1f.yilongma.data.ErrorMsg
import com.wo1f.yilongma.data.model.category.CategoryRes
import com.wo1f.yilongma.data.model.category.CategoryRq
import com.wo1f.yilongma.domain.repo.CategoryRepo
import com.wo1f.yilongma.utils.MockData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.bson.types.ObjectId
import java.time.LocalDate

class CategoryRepoFake : CategoryRepo {

    private val categoryList = ArrayList(MockData.mockCategoryList)

    override suspend fun getAll(): Flow<DataResource<List<CategoryRes>>> {
        return flowOf(DataResource.Success(categoryList))
    }

    override suspend fun insert(body: CategoryRq): Flow<DataResource<Unit>> {
        val categoryRes = CategoryRes(
            id = ObjectId.get().toString(),
            name = body.name,
            createdAt = LocalDate.now().toString(),
            count = 0,
        )
        categoryList.add(categoryRes)
        return flowOf(DataResource.Success())
    }

    override suspend fun update(name: String, body: CategoryRq): Flow<DataResource<Unit>> {
        val item = categoryList.find { it.name == name }
        return if (item == null) {
            flowOf(DataResource.Error(ErrorMsg.UNKNOWN))
        } else {
            categoryList.remove(item)
            categoryList.add(item.copy(name = name))
            flowOf(DataResource.Success())
        }
    }

    override suspend fun delete(name: String): Flow<DataResource<Unit>> {
        val item = categoryList.find { it.name == name }
        return if (item == null) {
            flowOf(DataResource.Error(ErrorMsg.UNKNOWN))
        } else {
            categoryList.remove(item)
            flowOf(DataResource.Success())
        }
    }
}
