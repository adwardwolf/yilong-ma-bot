/**
 * @author Adwardwo1f
 * @created June 2, 2022
 */

package com.wo1f.routes

import com.wo1f.BaseTest
import com.wo1f.MockData
import com.wo1f.domain.models.BaseResponse
import com.wo1f.domain.models.GetConversationRes
import com.wo1f.domain.usecases.conversation.DeleteConversation
import com.wo1f.domain.usecases.conversation.GetAllConversations
import com.wo1f.domain.usecases.conversation.GetConversationsByCategory
import com.wo1f.domain.usecases.conversation.InsertConversation
import com.wo1f.domain.usecases.conversation.UpdateConversation
import com.wo1f.plugins.defaultResponse
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import io.ktor.http.path
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Test

class ConversationRouteTest : BaseTest() {

    private lateinit var insertConversation: InsertConversation
    private lateinit var updateConversation: UpdateConversation
    private lateinit var deleteConversation: DeleteConversation
    private lateinit var getAllConversations: GetAllConversations
    private lateinit var getConversationsByCategory: GetConversationsByCategory

    @Test
    fun `test category POST`() = baseTestApplication {
        insertConversation = mockk()
        routing { conversationPost(insertConversation) }
        coEvery { insertConversation.invoke(MockData.conversationRq) } just Runs

        val postRqClient = baseClient.post {
            setBody(MockData.conversationRq)
            url("/conversation")
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        postRqClient.apply {
            status shouldBe HttpStatusCode.Created
            body<BaseResponse<Unit>>() shouldBe defaultResponse
        }
    }

    @Test
    fun `test category GET`() = baseTestApplication {
        getAllConversations = mockk()
        getConversationsByCategory = mockk()
        routing { conversationGet(getConversationsByCategory, getAllConversations) }
        coEvery { getConversationsByCategory.invoke(MockData.name) } returns MockData.mockGetCategoryRes

        val getRqClient = baseClient.get {
            url("{name}/conversation")
            parameter("name", MockData.name)
        }

        getRqClient.apply {
            status shouldBe HttpStatusCode.OK
            body<BaseResponse<GetConversationRes>>() shouldBe BaseResponse(MockData.mockGetCategoryRes)
        }
    }

    @Test
    fun `test category PATCH`() = baseTestApplication {
        updateConversation = mockk()
        routing { conversationPatch(updateConversation) }
        coEvery { updateConversation.invoke(MockData.id, MockData.conversationRq) } just Runs

        val patchRqClient = baseClient.patch {
            url {
                path("conversation")
                appendPathSegments(MockData.id)
            }
            setBody(MockData.conversationRq)
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        patchRqClient.apply {
            status shouldBe HttpStatusCode.NoContent
            body<BaseResponse<Unit>>() shouldBe defaultResponse
        }
    }

    @Test
    fun `test category DELETE`() = baseTestApplication {
        deleteConversation = mockk()
        routing { conversationDelete(deleteConversation) }
        coEvery { deleteConversation.invoke(MockData.id) } just Runs

        val deleteRqClient = baseClient.delete {
            url {
                path("conversation")
                appendPathSegments(MockData.id)
            }
        }

        deleteRqClient.apply {
            status shouldBe HttpStatusCode.NoContent
            body<BaseResponse<Unit>>() shouldBe defaultResponse
        }
    }
}
