package com.wo1f.domain.usecases.conversation

import com.wo1f.data.datasource.ConversationDataSource
import com.wo1f.domain.models.ConversationRq

interface UpdateConversation {

    suspend operator fun invoke(objectId: String, conversationRq: ConversationRq)
}

class UpdateConversationImpl(private val dataSource: ConversationDataSource) : UpdateConversation {

    override suspend fun invoke(objectId: String, conversationRq: ConversationRq) {
        dataSource.updateConversation(
            objectId = objectId,
            conversationRq.apply {
                this.question = question.trim()
                this.answer = answer.trim()
            }
        )
    }
}
