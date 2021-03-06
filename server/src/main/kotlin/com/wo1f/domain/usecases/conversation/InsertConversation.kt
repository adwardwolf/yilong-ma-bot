/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.domain.usecases.conversation

import com.wo1f.data.datasource.ConversationDataSource
import com.wo1f.domain.models.ConversationRq

interface InsertConversation {

    suspend operator fun invoke(conversationRq: ConversationRq)
}

class InsertConversationImpl(private val dataSource: ConversationDataSource) : InsertConversation {

    override suspend fun invoke(conversationRq: ConversationRq) {
        dataSource.insertOne(
            conversationRq.apply {
                this.answer = answer.trim()
                this.question = question.trim()
            }
        )
    }
}
