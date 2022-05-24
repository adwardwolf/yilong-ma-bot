package com.wo1f.data.datasource

import com.wo1f.data.collections.CategoryCollection
import com.wo1f.domain.models.CategoryRes
import com.wo1f.domain.models.CategoryRq
import kotlinx.coroutines.CoroutineDispatcher

class CategoryDataSource(
    private val dispatcher: CoroutineDispatcher,
    private val collection: CategoryCollection
) {

    fun getCategories(): List<CategoryRes> {
        return collection.getCategories()
    }

    fun insertCategory(categoryRq: CategoryRq): Boolean {
        return collection.insertCategory(categoryRq)
    }
}
