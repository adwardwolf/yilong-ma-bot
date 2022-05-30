/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.plugins

import com.wo1f.domain.models.BaseResponse
import com.wo1f.routes.registerCategoryRoutes
import com.wo1f.routes.registerChatRoutes
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
        exception<DatabaseException> { call, _ ->
            call.respond(HttpStatusCode.InternalServerError)
        }
        exception<ReturnException> { call, exception ->
            call.respond(HttpStatusCode.OK, BaseResponse<Unit>(msgCode = exception.msgCode))
        }
    }

    registerConversationRoutes()
    registerCategoryRoutes()
    registerChatRoutes()
}

val defaultResponse: BaseResponse<Unit>
    get() = BaseResponse()

class AuthenticationException : RuntimeException()

class AuthorizationException : RuntimeException()

class DatabaseException(override val message: String) : RuntimeException(message)

/**
 * An exception will be thrown by domain-specific errors
 * @property msgCode A meaningful message code that front end can understand
 */
class ReturnException(val msgCode: Int) : RuntimeException()
