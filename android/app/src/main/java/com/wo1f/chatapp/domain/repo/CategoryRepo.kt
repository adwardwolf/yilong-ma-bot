/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.chatapp.domain.repo

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.model.category.CategoryRq
import kotlinx.coroutines.flow.Flow

interface CategoryRepo {

    suspend fun getAll(): Flow<DataResource<List<CategoryRes>>>

    suspend fun insert(body: CategoryRq): Flow<DataResource<Unit>>

    suspend fun update(name: String, body: CategoryRq): Flow<DataResource<Unit>>

    suspend fun delete(name: String): Flow<DataResource<Unit>>
}
