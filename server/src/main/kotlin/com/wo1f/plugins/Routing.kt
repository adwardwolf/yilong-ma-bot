package com.wo1f.plugins

import com.wo1f.routes.registerCategoryRoutes
import com.wo1f.routes.registerConversationRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureRouting() {
    install(StatusPages) {
        exception<AuthenticationException> { call, _ ->
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> { call, _ ->
            call.respond(HttpStatusCode.Forbidden)
        }
    }

    registerConversationRoutes()
    registerCategoryRoutes()
}
class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
