package com.wo1f.domain.usecases.conversation

import com.wo1f.data.datasource.ConversationDataSource

interface DeleteConversation {

    suspend operator fun invoke(objectId: String)
}

class DeleteConversationImpl(private val dataSource: ConversationDataSource) : DeleteConversation {

    override suspend fun invoke(objectId: String) {
        dataSource.deleteConversation(objectId)
    }
}
