/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.domain.usecases.conversation

import com.wo1f.data.datasource.ConversationDataSource
import com.wo1f.domain.models.ConversationRq

interface UpdateConversation {

    suspend operator fun invoke(id: String, conversationRq: ConversationRq)
}

class UpdateConversationImpl(private val dataSource: ConversationDataSource) : UpdateConversation {

    override suspend fun invoke(id: String, conversationRq: ConversationRq) {
        dataSource.updateOne(
            objectId = id,
            conversationRq.apply {
                this.question = question.trim()
                this.answer = answer.trim()
            }
        )
    }
}
