package com.wo1f.routes

import com.wo1f.data.inject.inject
import com.wo1f.domain.models.BaseResponse
import com.wo1f.domain.models.ConversationRq
import com.wo1f.domain.usecases.conversation.DeleteConversation
import com.wo1f.domain.usecases.conversation.GetAllConversations
import com.wo1f.domain.usecases.conversation.GetConversationsByCategory
import com.wo1f.domain.usecases.conversation.InsertConversation
import com.wo1f.domain.usecases.conversation.UpdateConversation
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

fun Application.registerConversationRoutes() {

    val insertConversation by inject<InsertConversation>()
    val updateConversation by inject<UpdateConversation>()
    val deleteConversation by inject<DeleteConversation>()
    val getAllConversations by inject<GetAllConversations>()
    val getConversationsByCategory by inject<GetConversationsByCategory>()

    routing {
        conversationRoutes(
            insertConversation,
            updateConversation,
            deleteConversation,
            getAllConversations,
            getConversationsByCategory
        )
    }
}

fun Route.conversationRoutes(
    insertConversation: InsertConversation,
    updateConversation: UpdateConversation,
    deleteConversation: DeleteConversation,
    getAllConversations: GetAllConversations,
    getConversationsByCategory: GetConversationsByCategory
) {

    post<ConversationRq>("/conversation") { request ->
        insertConversation(request)
        call.respond(HttpStatusCode.Created, BaseResponse<Unit>())
    }

    patch<ConversationRq>("/conversation/{id}") { request ->
        val id = call.parameters["id"]
        if (id != null) {
            updateConversation(id, request)
            call.respond(HttpStatusCode.NoContent, BaseResponse<Unit>())
        } else {
            call.respond(HttpStatusCode.BadRequest, BaseResponse<Unit>())
        }
    }

    delete("/conversation/{id}") {
        val id = call.parameters["id"]
        if (id != null) {
            deleteConversation(id)
            call.respond(HttpStatusCode.NoContent, BaseResponse<Unit>())
        } else {
            call.respond(HttpStatusCode.BadRequest, BaseResponse<Unit>())
        }
    }

    get("/{name}/conversation") {
        val name = call.parameters["name"]
        if (name != null) {
            if (name == "all") {
                val result = getAllConversations()
                call.respond(HttpStatusCode.OK, BaseResponse(result))
            } else {
                val result = getConversationsByCategory(name)
                call.respond(HttpStatusCode.OK, BaseResponse(result))
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, BaseResponse<Unit>())
        }
    }
}
