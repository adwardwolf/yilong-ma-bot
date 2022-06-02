/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.chatapp.fake_repo

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.ErrorMsg
import com.wo1f.chatapp.data.model.conversation.ConversationRes
import com.wo1f.chatapp.data.model.conversation.ConversationRq
import com.wo1f.chatapp.data.model.conversation.GetConversationRes
import com.wo1f.chatapp.domain.repo.ConversationRepo
import com.wo1f.chatapp.utils.MockData.mockCategoryRes
import com.wo1f.chatapp.utils.MockData.mockConverList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ConversationRepoFake : ConversationRepo {

    private val list = ArrayList(mockConverList)

    override suspend fun insert(body: ConversationRq): Flow<DataResource<Unit>> {
        val conversationRes = ConversationRes(
            id = "1",
            answer = body.answer,
            question = body.question,
            category = body.category,
            createdAt = "March 13, 2022"
        )
        list.add(conversationRes)
        return flowOf(DataResource.Success())
    }

    override suspend fun getAll(name: String): Flow<DataResource<GetConversationRes>> {
        return flowOf(DataResource.Success(GetConversationRes(mockCategoryRes, list)))
    }

    override suspend fun update(id: String, body: ConversationRq): Flow<DataResource<Unit>> {
        val item = list.find { it.id == id }
        return if (item == null) {
            flowOf(DataResource.Error(ErrorMsg.UNKNOWN))
        } else {
            list.remove(item)
            list.add(
                item.copy(
                    question = body.question,
                    answer = body.answer,
                    category = body.category
                )
            )
            flowOf(DataResource.Success())
        }
    }

    override suspend fun delete(id: String): Flow<DataResource<Unit>> {
        val item = list.find { it.id == id }
        return if (item == null) {
            flowOf(DataResource.Error(ErrorMsg.UNKNOWN))
        } else {
            list.remove(item)
            flowOf(DataResource.Success())
        }
    }
}
