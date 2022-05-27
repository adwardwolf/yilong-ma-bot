/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.domain.usecases.conversation

import com.wo1f.data.datasource.ConversationDataSource

interface DeleteConversation {

    suspend operator fun invoke(id: String)
}

class DeleteConversationImpl(private val dataSource: ConversationDataSource) : DeleteConversation {

    override suspend fun invoke(id: String) {
        dataSource.deleteOne(id)
    }
}
