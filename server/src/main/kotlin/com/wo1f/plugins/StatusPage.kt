/**
 * @author Adwardwo1f
 * @created June 2, 2022
 */

package com.wo1f.plugins

import com.wo1f.domain.models.BaseResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureStatusPage() {
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
}
