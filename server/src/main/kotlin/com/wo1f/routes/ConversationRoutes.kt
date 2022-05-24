package com.wo1f.routes

import com.wo1f.data.inject.inject
import com.wo1f.domain.models.ConversationRq
import com.wo1f.domain.usecases.conversation.DeleteConversation
import com.wo1f.domain.usecases.conversation.GetAllConversations
import com.wo1f.domain.usecases.conversation.GetConversationsByName
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
    val getConversationsByName by inject<GetConversationsByName>()

    routing {
        conversationRoutes(
            insertConversation,
            updateConversation,
            deleteConversation,
            getAllConversations,
            getConversationsByName
        )
    }
}

fun Route.conversationRoutes(
    insertConversation: InsertConversation,
    updateConversation: UpdateConversation,
    deleteConversation: DeleteConversation,
    getAllConversations: GetAllConversations,
    getConversationsByName: GetConversationsByName
) {

    post<ConversationRq>("/conversation") { request ->
        val success = insertConversation(request)
        if (success) {
            call.respond(HttpStatusCode.Created)
        } else {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    patch<ConversationRq>("/conversation/{id}") { request ->
        val id = call.parameters["id"]
        if (id != null) {
            val success = updateConversation(id, request)
            if (success) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
            }
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    delete("/conversation/{id}") {
        val id = call.parameters["id"]
        if (id != null) {
            val success = deleteConversation(id)
            if (success) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
            }
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    get("/{name}/conversation") {
        val name = call.parameters["name"]
        if (name != null) {
            if (name == "all") {
                val result = getAllConversations()
                call.respond(HttpStatusCode.OK, result)
            } else {
                val result = getConversationsByName(name)
                call.respond(HttpStatusCode.OK, result)
            }
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
