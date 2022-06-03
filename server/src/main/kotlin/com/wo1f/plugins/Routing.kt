/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.plugins

import com.wo1f.domain.models.BaseResponse
import com.wo1f.routes.registerCategoryRoutes
import com.wo1f.routes.registerChatRoutes
import com.wo1f.routes.registerConversationRoutes
import io.ktor.server.application.Application

fun Application.configureRouting() {
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
