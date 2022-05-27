/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.domain.usecases.category

import com.wo1f.data.datasource.CategoryDataSource
import com.wo1f.domain.models.CategoryRq

interface InsertCategory {

    suspend operator fun invoke(categoryRq: CategoryRq)
}

class InsertCategoryImpl(private val dataSource: CategoryDataSource) : InsertCategory {

    override suspend fun invoke(categoryRq: CategoryRq) {
        dataSource.insertOne(
            categoryRq.apply {
                name = name.lowercase().trim()
            }
        )
    }
}
