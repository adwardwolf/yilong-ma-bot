package com.wo1f.domain.usecases.conversation

import com.wo1f.data.datasource.ConversationDataSource
import com.wo1f.domain.models.ConversationRes

interface GetAllConversations {

    suspend operator fun invoke(): List<ConversationRes>
}

class GetAllConversationsImpl(private val dataSource: ConversationDataSource) : GetAllConversations {

    override suspend fun invoke(): List<ConversationRes> {
        return dataSource.getConversations()
    }
}
