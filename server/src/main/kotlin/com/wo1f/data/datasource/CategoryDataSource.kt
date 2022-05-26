package com.wo1f.data.datasource

import com.wo1f.data.collections.CategoryCollection
import com.wo1f.domain.models.CategoryRes
import com.wo1f.domain.models.CategoryRq

class CategoryDataSource(private val collection: CategoryCollection) {

    suspend fun getCategories(): List<CategoryRes> {
        return collection.getCategories()
    }

    suspend fun insertCategory(categoryRq: CategoryRq) {
        collection.insertCategory(categoryRq)
    }
}
