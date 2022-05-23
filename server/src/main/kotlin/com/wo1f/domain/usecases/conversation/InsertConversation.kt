package com.wo1f.domain.usecases.conversation

import com.wo1f.data.datasource.ConversationDataSource
import com.wo1f.domain.models.ConversationRq

interface InsertConversation {

    suspend operator fun invoke(conversationRq: ConversationRq): Boolean
}

class InsertConversationImpl(private val dataSource: ConversationDataSource) : InsertConversation {

    override suspend fun invoke(conversationRq: ConversationRq): Boolean {
        return dataSource.insertConversation(conversationRq)
    }
}
