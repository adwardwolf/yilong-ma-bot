package com.wo1f.domain.usecases.conversation

import com.wo1f.data.datasource.ConversationDataSource
import com.wo1f.domain.models.ConversationRes

interface GetConversationsByCategory {

    suspend operator fun invoke(name: String): List<ConversationRes>
}

class GetConversationsByCategoryImpl(
    private val dataSource: ConversationDataSource
) : GetConversationsByCategory {

    override suspend fun invoke(name: String): List<ConversationRes> {
        return dataSource.getConversationByCategory(name)
    }
}
