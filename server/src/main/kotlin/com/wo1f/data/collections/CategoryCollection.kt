package com.wo1f.data.collections

import com.mongodb.MongoWriteException
import com.wo1f.Connection.categoriesColl
import com.wo1f.Connection.categoriesDbColl
import com.wo1f.domain.models.CategoryDb
import com.wo1f.domain.models.CategoryRes
import com.wo1f.domain.models.CategoryRq
import com.wo1f.domain.models.MsgCode
import com.wo1f.domain.models.toDbObject
import com.wo1f.plugins.DatabaseException
import com.wo1f.plugins.ReturnException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.litote.kmongo.descending
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.`in`
import org.litote.kmongo.inc
import org.litote.kmongo.ne

class CategoryCollection(private val dispatcher: CoroutineDispatcher) {

    suspend fun getCategories(): List<CategoryRes> = withContext(dispatcher) {
        val categories = categoriesColl
            .find(CategoryDb::name ne "all")
            .sort(descending(CategoryDb::createdAt))
            .toList()
        val all = categoriesColl.findOne(CategoryDb::name eq "all")
        val list = ArrayList<CategoryRes>()
        if (all != null) {
            list.add(all)
        } else {
            insertCategory(CategoryRq("all"))
            getCategories()
        }
        list.addAll(categories)
        list
    }

    suspend fun insertCategory(categoryRq: CategoryRq) = withContext(dispatcher) {
        try {
            val result = categoriesDbColl.insertOne(categoryRq.toDbObject())
            if (!result.wasAcknowledged()) {
                throw DatabaseException("insertCategory failed")
            }
        } catch (exception: MongoWriteException) {
            if (exception.message?.contains("name") == true) {
                throw ReturnException(MsgCode.CATEGORY_ALREADY_EXIST)
            } else {
                throw DatabaseException("insertCategory failed")
            }
        }
    }

    suspend fun increaseCategoryCount(name: String) = withContext(dispatcher) {
        val result = categoriesDbColl.updateMany(
            CategoryDb::name `in` listOf(name, "all"),
            inc(CategoryDb::count, 1)
        )
        if (!result.wasAcknowledged()) {
            throw DatabaseException("increaseCategoryCount failed")
        }
    }

    suspend fun decreaseCategoryCount(name: String) = withContext(dispatcher) {
        val result = categoriesDbColl.updateMany(
            CategoryDb::name `in` listOf(name, "all"),
            inc(CategoryDb::count, -1)
        )
        if (!result.wasAcknowledged()) {
            throw DatabaseException("decreaseCategoryCount failed")
        }
    }
}
