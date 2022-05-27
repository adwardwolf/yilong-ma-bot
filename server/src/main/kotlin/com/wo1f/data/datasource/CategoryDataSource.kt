package com.wo1f.data.datasource

import com.wo1f.data.collections.CategoryCollection
import com.wo1f.data.collections.ConversationCollection
import com.wo1f.domain.models.CategoryRes
import com.wo1f.domain.models.CategoryRq

class CategoryDataSource(
    private val categoryColl: CategoryCollection,
    private val conversationColl: ConversationCollection
) {

    suspend fun getAll(): List<CategoryRes> {
        return categoryColl.getAll()
    }

    suspend fun insertOne(categoryRq: CategoryRq) {
        categoryColl.insertOne(categoryRq)
    }

    suspend fun updateOne(name: String, categoryRq: CategoryRq) {
        categoryColl.updateOne(name, categoryRq)
        conversationColl.updateToNewCategory(name, categoryRq.name)
    }

    suspend fun deleteOne(name: String) {
        val category = categoryColl.deleteOne(name)
        conversationColl.deleteByCategory(name)
        categoryColl.decreaseCount(name, -(category.count))
    }
}
