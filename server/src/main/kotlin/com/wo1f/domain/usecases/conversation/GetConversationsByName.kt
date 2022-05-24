package com.wo1f.domain.usecases.conversation

import com.wo1f.data.datasource.ConversationDataSource
import com.wo1f.domain.models.ConversationRes

interface GetConversationsByName {

    suspend operator fun invoke(name: String): List<ConversationRes>
}

class GetConversationsByNameImpl(private val dataSource: ConversationDataSource) : GetConversationsByName {

    override suspend fun invoke(name: String): List<ConversationRes> {
        return dataSource.getConversationByName(name)
    }
}
