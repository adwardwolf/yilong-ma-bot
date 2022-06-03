/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.routes

import com.wo1f.data.inject.inject
import com.wo1f.domain.models.BaseResponse
import com.wo1f.domain.models.ConversationRq
import com.wo1f.domain.usecases.conversation.DeleteConversation
import com.wo1f.domain.usecases.conversation.GetAllConversations
import com.wo1f.domain.usecases.conversation.GetConversationsByCategory
import com.wo1f.domain.usecases.conversation.InsertConversation
import com.wo1f.domain.usecases.conversation.UpdateConversation
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

fun Application.registerConversationRoutes() {

    val insertConversation by inject<InsertConversation>()
    val updateConversation by inject<UpdateConversation>()
    val deleteConversation by inject<DeleteConversation>()
    val getAllConversations by inject<GetAllConversations>()
    val getConversationsByCategory by inject<GetConversationsByCategory>()

    routing {
        conversationPost(insertConversation)
        conversationPatch(updateConversation)
        conversationDelete(deleteConversation)
        conversationGet(getConversationsByCategory, getAllConversations)
    }
}

fun Route.conversationPost(insertConversation: InsertConversation) {
    post<ConversationRq>("/conversation") { request ->
        insertConversation(request)
        call.respond(HttpStatusCode.Created, defaultResponse)
    }
}

fun Route.conversationPatch(updateConversation: UpdateConversation) {
    patch<ConversationRq>("/conversation/{id}") { request ->
        val id = call.parameters["id"]
        if (id != null) {
            updateConversation(id, request)
            call.respond(HttpStatusCode.NoContent, defaultResponse)
        }
    }
}

fun Route.conversationDelete(deleteConversation: DeleteConversation) {
    delete("/conversation/{id}") {
        val id = call.parameters["id"]
        if (id != null) {
            deleteConversation(id)
            call.respond(HttpStatusCode.NoContent, defaultResponse)
        }
    }
}

fun Route.conversationGet(
    getConversationsByCategory: GetConversationsByCategory,
    getAllConversations: GetAllConversations
) {
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
        }
    }
}
