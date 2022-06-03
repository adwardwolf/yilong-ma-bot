/**
 * @author Adwardwo1f
 * @created June 2, 2022
 */

package com.wo1f.routes

import com.wo1f.BaseTest
import com.wo1f.MockData
import com.wo1f.domain.models.BaseResponse
import com.wo1f.domain.models.ChatRes
import com.wo1f.domain.usecases.chat.GetAllChats
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import io.ktor.http.path
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Test

class ChatRouteTest : BaseTest() {

    private lateinit var getAllChats: GetAllChats

    @Test
    fun `test chat GET`() = baseTestApplication {
        getAllChats = mockk()
        routing { chatGet(getAllChats) }
        coEvery { getAllChats.invoke(MockData.room) } returns MockData.mockChatList

        val getClient = baseClient.get {
            url {
                path("chats")
                appendPathSegments(MockData.room)
            }
        }

        getClient.apply {
            status shouldBe HttpStatusCode.OK
            body<BaseResponse<List<ChatRes>>>() shouldBe BaseResponse(MockData.mockChatList)
        }
    }
}
