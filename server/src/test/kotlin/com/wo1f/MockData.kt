/**
 * @author Adwardwo1f
 * @created June 2, 2022
 */

package com.wo1f

import com.wo1f.domain.models.CategoryRes
import com.wo1f.domain.models.CategoryRq
import com.wo1f.domain.models.ChatRes
import com.wo1f.domain.models.ConversationRes
import com.wo1f.domain.models.ConversationRq
import com.wo1f.domain.models.GetConversationRes

object MockData {

    val name = "history"
    val id = "1"
    val room = "3242"
    val conversationRq = ConversationRq(
        question = "How are you?",
        answer = "I'm good",
        category = "greetings"
    )
    val categoryRq = CategoryRq(name)
    val mockCategoryRes = CategoryRes(
        id = "1",
        name = "greetings",
        createdAt = "March 13, 2022",
        count = 2
    )
    val mockConverList = listOf(
        ConversationRes(
            id = "1",
            answer = "How are you",
            question = "I'm good",
            category = "greetings",
            createdAt = "March 13, 2022",
            isTrained = true
        ),
        ConversationRes(
            id = "2",
            answer = "Hello",
            question = "Hello",
            category = "greetings",
            createdAt = "March 22, 2022",
            isTrained = true
        )
    )
    val mockCategoryList = listOf(
        CategoryRes(id = "1", "english", count = 12, createdAt = "March 13, 2022"),
        CategoryRes(id = "2", "history", count = 24, createdAt = "June 13, 2022"),
        CategoryRes(id = "3", "physics", count = 42, createdAt = "August 13, 2022"),
        CategoryRes(id = "4", "greetings", count = 242, createdAt = "May 13, 2022")
    )
    val mockGetCategoryRes = GetConversationRes(mockCategoryRes, mockConverList)
    val mockChatList = listOf(
        ChatRes(id = "1", userName = "adwardwo1f", roomName = "1", text = "Hi", date = ""),
        ChatRes(id = "2", userName = "Yilong Ma", roomName = "1", text = "Yes hi", date = ""),
        ChatRes(id = "3", userName = "adwardwo1f", roomName = "1", text = "Hello", date = "")
    )
}
