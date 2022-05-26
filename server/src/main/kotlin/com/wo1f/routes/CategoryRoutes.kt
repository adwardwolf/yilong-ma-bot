package com.wo1f.routes

import com.wo1f.data.inject.inject
import com.wo1f.domain.models.BaseResponse
import com.wo1f.domain.models.CategoryRq
import com.wo1f.domain.usecases.category.GetCategories
import com.wo1f.domain.usecases.category.InsertCategory
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.registerCategoryRoutes() {

    val insertCategory by inject<InsertCategory>()
    val getCategories by inject<GetCategories>()

    routing {
        conversationRoutes(
            insertCategory,
            getCategories
        )
    }
}

fun Route.conversationRoutes(
    insertCategory: InsertCategory,
    getCategories: GetCategories
) {

    post<CategoryRq>("/category") { request ->
        insertCategory(request)
        call.respond(HttpStatusCode.Created, BaseResponse<Unit>())
    }

    get("/category") {
        val result = getCategories()
        call.respond(HttpStatusCode.OK, BaseResponse(result))
    }
}
