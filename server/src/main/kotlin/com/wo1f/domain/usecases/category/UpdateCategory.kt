package com.wo1f.domain.usecases.category

import com.wo1f.data.datasource.CategoryDataSource
import com.wo1f.domain.models.CategoryRq

interface UpdateCategory {

    suspend operator fun invoke(name: String, categoryRq: CategoryRq)
}

class UpdateCategoryImpl(private val dataSource: CategoryDataSource) : UpdateCategory {

    override suspend fun invoke(name: String, categoryRq: CategoryRq) {
        dataSource.updateOne(
            name = name,
            categoryRq = categoryRq.apply {
                this.name = this.name.lowercase().trim()
            }
        )
    }
}
