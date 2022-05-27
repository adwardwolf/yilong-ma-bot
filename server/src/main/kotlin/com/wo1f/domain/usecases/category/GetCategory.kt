package com.wo1f.domain.usecases.category

import com.wo1f.data.datasource.CategoryDataSource
import com.wo1f.domain.models.CategoryRes

interface GetCategories {

    suspend operator fun invoke(): List<CategoryRes>
}

class GetCategoriesImpl(private val dataSource: CategoryDataSource) : GetCategories {

    override suspend fun invoke(): List<CategoryRes> {
        return dataSource.getAll()
    }
}
