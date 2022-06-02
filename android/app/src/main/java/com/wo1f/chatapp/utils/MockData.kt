/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.chatapp.utils

import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.model.chat.ChatRes
import com.wo1f.chatapp.data.model.conversation.ConversationRes
import com.wo1f.chatapp.data.model.conversation.GetConversationRes

object MockData {

    const val mockCategory = "english"
    val mockCategoryList = listOf(
        CategoryRes(id = "1", "english", count = 12, createdAt = "March 13, 2022"),
        CategoryRes(id = "2", "history", count = 24, createdAt = "June 13, 2022"),
        CategoryRes(id = "3", "physics", count = 42, createdAt = "August 13, 2022"),
        CategoryRes(id = "4", "greetings", count = 242, createdAt = "May 13, 2022")
    )
    const val mockRoom = "1"
    val mockChatList = listOf(
        ChatRes(id = "1", userName = "adwardwo1f", roomName = "1", text = "Hi", null),
        ChatRes(id = "2", userName = "Yilong Ma", roomName = "1", text = "Yes hi", null),
        ChatRes(id = "3", userName = "adwardwo1f", roomName = "1", text = "Hello", null)
    )
    val mockChatRes = ChatRes(
        id = "4",
        userName = "adwardwo1f",
        roomName = "1",
        text = "How are you doing son?",
        null
    )
    const val mockName = "history"
    val mockCategoryRes = CategoryRes(
        id = "1",
        name = "greetings",
        createdAt = "March 13, 2022",
        count = 2
    )
    val mockConversationRes = ConversationRes(
        id = "1",
        answer = "How are you",
        question = "I'm good",
        category = "greetings",
        createdAt = "March 13, 2022"
    )
    val mockConverList = listOf(
        ConversationRes(
            id = "1",
            answer = "How are you",
            question = "I'm good",
            category = "greetings",
            createdAt = "March 13, 2022"
        ),
        ConversationRes(
            id = "2",
            answer = "Hello",
            question = "Hello",
            category = "greetings",
            createdAt = "March 22, 2022"
        )
    )
    val mockGetCategoryRes = GetConversationRes(mockCategoryRes, mockConverList)
}
