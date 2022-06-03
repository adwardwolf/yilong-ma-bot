/**
 * @author Adwardwo1f
 * @created May 28, 2022
 */

package com.wo1f.routes

import com.wo1f.data.inject.inject
import com.wo1f.domain.models.BaseResponse
import com.wo1f.domain.usecases.chat.GetAllChats
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.registerChatRoutes() {

    val getAllChats by inject<GetAllChats>()

    routing {
        chatGet(getAllChats)
    }
}

fun Route.chatGet(getAllChats: GetAllChats) {
    get("/chats/{room}") {
        val room = call.parameters["room"]
        if (room != null) {
            val result = getAllChats(room)
            call.respond(HttpStatusCode.OK, BaseResponse(result))
        }
    }
}
