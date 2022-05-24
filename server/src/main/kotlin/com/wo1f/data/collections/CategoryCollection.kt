package com.wo1f.data.collections

import com.wo1f.Connection.categories
import com.wo1f.Connection.categoriesDb
import com.wo1f.domain.models.CategoryRes
import com.wo1f.domain.models.CategoryRq
import com.wo1f.domain.models.toDbObject

class CategoryCollection {

    fun getCategories(): List<CategoryRes> {
        return categories.find().toList()
    }

    fun insertCategory(categoryRq: CategoryRq): Boolean {
        val result = categoriesDb.insertOne(categoryRq.toDbObject())
        return result.wasAcknowledged()
    }
}
