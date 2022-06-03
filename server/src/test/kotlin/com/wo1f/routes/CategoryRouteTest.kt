/**
 * @author Adwardwo1f
 * @created June 2, 2022
 */

package com.wo1f.routes

import com.wo1f.BaseTest
import com.wo1f.MockData
import com.wo1f.domain.models.BaseResponse
import com.wo1f.domain.models.CategoryRes
import com.wo1f.domain.usecases.category.DeleteCategory
import com.wo1f.domain.usecases.category.GetCategories
import com.wo1f.domain.usecases.category.InsertCategory
import com.wo1f.domain.usecases.category.UpdateCategory
import com.wo1f.plugins.defaultResponse
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import io.ktor.http.path
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Test

class CategoryRouteTest : BaseTest() {

    private lateinit var insertCategory: InsertCategory
    private lateinit var updateCategory: UpdateCategory
    private lateinit var deleteCategory: DeleteCategory
    private lateinit var getCategories: GetCategories

    @Test
    fun `test category POST`() = baseTestApplication {
        insertCategory = mockk()
        routing { categoryPost(insertCategory) }
        coEvery { insertCategory.invoke(MockData.categoryRq) } just Runs

        val postClient = baseClient.post {
            url("category")
            setBody(MockData.categoryRq)
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        postClient.apply {
            status shouldBe HttpStatusCode.Created
            body<BaseResponse<Unit>>() shouldBe defaultResponse
        }
    }

    @Test
    fun `test category GET`() = baseTestApplication {
        getCategories = mockk()
        routing { categoryGet(getCategories) }
        coEvery { getCategories.invoke() } returns MockData.mockCategoryList

        val getClient = baseClient.get {
            url("category")
        }

        getClient.apply {
            status shouldBe HttpStatusCode.OK
            body<BaseResponse<List<CategoryRes>>>() shouldBe BaseResponse(MockData.mockCategoryList)
        }
    }

    @Test
    fun `test category PATCH`() = baseTestApplication {
        updateCategory = mockk()
        routing { categoryPatch(updateCategory) }
        coEvery { updateCategory.invoke(MockData.name, MockData.categoryRq) } just Runs

        val patchClient = baseClient.patch {
            url {
                path("category")
                setBody(MockData.categoryRq)
                appendPathSegments(MockData.name)
                header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
        }

        patchClient.apply {
            status shouldBe HttpStatusCode.NoContent
            body<BaseResponse<Unit>>() shouldBe defaultResponse
        }
    }

    @Test
    fun `test category DELETE`() = baseTestApplication {
        deleteCategory = mockk()
        routing { categoryDelete(deleteCategory) }
        coEvery { deleteCategory(MockData.name) } just Runs

        val deleteClient = baseClient.delete {
            url {
                path("category")
                appendPathSegments(MockData.name)
            }
        }

        deleteClient.apply {
            status shouldBe HttpStatusCode.NoContent
            body<BaseResponse<Unit>>() shouldBe defaultResponse
        }
    }
}
