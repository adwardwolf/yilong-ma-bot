/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.routes

import com.wo1f.data.inject.inject
import com.wo1f.domain.models.BaseResponse
import com.wo1f.domain.models.CategoryRq
import com.wo1f.domain.usecases.category.DeleteCategory
import com.wo1f.domain.usecases.category.GetCategories
import com.wo1f.domain.usecases.category.InsertCategory
import com.wo1f.domain.usecases.category.UpdateCategory
import com.wo1f.plugins.defaultResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.registerCategoryRoutes() {

    val insertCategory by inject<InsertCategory>()
    val getCategories by inject<GetCategories>()
    val updateCategory by inject<UpdateCategory>()
    val deleteCategory by inject<DeleteCategory>()

    routing {
        categoriesRoutes(
            insertCategory,
            getCategories,
            updateCategory,
            deleteCategory
        )
    }
}

fun Route.categoriesRoutes(
    insertCategory: InsertCategory,
    getCategories: GetCategories,
    updateCategory: UpdateCategory,
    deleteCategory: DeleteCategory
) {

    post<CategoryRq>("/category") { request ->
        insertCategory(request)
        call.respond(HttpStatusCode.Created, defaultResponse)
    }

    get("/category") {
        val result = getCategories()
        call.respond(HttpStatusCode.OK, BaseResponse(result))
    }

    patch<CategoryRq>("/category/{name}") { request ->
        val name = call.parameters["name"]
        if (name != null) {
            updateCategory(name, request)
            call.respond(HttpStatusCode.NoContent, defaultResponse)
        } else {
            call.respond(HttpStatusCode.BadRequest, defaultResponse)
        }
    }

    delete("/category/{name}") {
        val name = call.parameters["name"]
        if (name != null) {
            deleteCategory(name)
            call.respond(HttpStatusCode.NoContent, defaultResponse)
        } else {
            call.respond(HttpStatusCode.BadRequest, defaultResponse)
        }
    }
}
