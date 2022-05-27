package com.wo1f.domain.usecases.category

import com.wo1f.data.datasource.CategoryDataSource

interface DeleteCategory {

    suspend operator fun invoke(name: String)
}

class DeleteCategoryImpl(private val dataSource: CategoryDataSource) : DeleteCategory {

    override suspend fun invoke(name: String) {
        dataSource.deleteOne(name)
    }
}
