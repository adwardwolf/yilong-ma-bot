package com.wo1f.domain.usecases.conversation

import com.wo1f.data.datasource.ConversationDataSource
import com.wo1f.domain.models.GetConversationRes

interface GetAllConversations {

    suspend operator fun invoke(): GetConversationRes
}

class GetAllConversationsImpl(private val dataSource: ConversationDataSource) : GetAllConversations {

    override suspend fun invoke(): GetConversationRes {
        return dataSource.getAll()
    }
}
