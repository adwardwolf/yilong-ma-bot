/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.domain.usecases.conversation

import com.wo1f.data.datasource.ConversationDataSource
import com.wo1f.domain.models.GetConversationRes

interface GetConversationsByCategory {

    suspend operator fun invoke(name: String): GetConversationRes
}

class GetConversationsByCategoryImpl(
    private val dataSource: ConversationDataSource
) : GetConversationsByCategory {

    override suspend fun invoke(name: String): GetConversationRes {
        return dataSource.getByCategory(name)
    }
}
