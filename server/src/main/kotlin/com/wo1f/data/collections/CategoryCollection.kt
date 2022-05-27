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
import org.litote.kmongo.setTo
import org.litote.kmongo.updateOne

class CategoryCollection(private val dispatcher: CoroutineDispatcher) {

    suspend fun getAll(): List<CategoryRes> = withContext(dispatcher) {
        val categories = categoriesColl
            .find(CategoryDb::name ne "all")
            .sort(descending(CategoryDb::createdAt))
            .toList()
        val all = categoriesColl.findOne(CategoryDb::name eq "all")
        val list = ArrayList<CategoryRes>()

        if (all != null) {
            list.add(all)
        } else {
            insertOne(CategoryRq("all"))
            getAll()
        }
        list.addAll(categories)
        list
    }

    suspend fun getByName(name: String): CategoryRes = withContext(dispatcher) {
        categoriesColl.findOne(CategoryDb::name eq name)
            ?: throw DatabaseException("getByName failed")
    }

    suspend fun insertOne(categoryRq: CategoryRq) = withContext(dispatcher) {
        try {
            val result = categoriesDbColl.insertOne(categoryRq.toDbObject())
            if (!result.wasAcknowledged()) {
                throw DatabaseException("insert failed")
            }
        } catch (exception: MongoWriteException) {
            if (exception.message?.contains("name") == true) {
                throw ReturnException(MsgCode.CATEGORY_ALREADY_EXIST)
            } else {
                throw DatabaseException("insertOne failed")
            }
        }
    }

    suspend fun updateOne(name: String, categoryRq: CategoryRq) = withContext(dispatcher) {
        val result = categoriesDbColl.updateOne(
            filter = CategoryDb::name eq name,
            updates = arrayOf(CategoryDb::name setTo categoryRq.name)
        )
        if (!result.wasAcknowledged()) {
            throw DatabaseException("updateOne failed")
        }
    }

    suspend fun deleteOne(name: String): CategoryRes = withContext(dispatcher) {
        val result = categoriesColl.findOneAndDelete(CategoryDb::name eq name)
            ?: throw DatabaseException("deleteOne failed")
        result
    }

    suspend fun increaseCount(name: String, number: Long) = withContext(dispatcher) {
        val result = categoriesDbColl.updateMany(
            CategoryDb::name `in` listOf(name, "all"),
            inc(CategoryDb::count, number)
        )
        if (!result.wasAcknowledged()) {
            throw DatabaseException("increaseCount failed")
        }
    }

    suspend fun decreaseCount(name: String, number: Long) = withContext(dispatcher) {
        val result = categoriesDbColl.updateMany(
            CategoryDb::name `in` listOf(name, "all"),
            inc(CategoryDb::count, number)
        )
        if (!result.wasAcknowledged()) {
            throw DatabaseException("decreaseCount failed")
        }
    }
}
