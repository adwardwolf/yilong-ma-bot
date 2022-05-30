/**
 * @author Adwardwo1f
 * @created May 28, 2022
 */

package com.wo1f.domain.usecases.chat

import com.wo1f.data.datasource.ChatDataSource
import com.wo1f.domain.models.ChatRes

interface GetAllChats {

    suspend operator fun invoke(roomName: String): List<ChatRes>
}

class GetAllChatsImpl(private val dataSource: ChatDataSource) : GetAllChats {

    override suspend fun invoke(roomName: String): List<ChatRes> {
        return dataSource.getAll(roomName)
    }
}
