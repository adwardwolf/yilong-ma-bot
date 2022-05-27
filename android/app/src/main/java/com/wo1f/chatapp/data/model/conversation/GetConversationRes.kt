/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.data.model.conversation

import com.google.gson.annotations.SerializedName
import com.wo1f.chatapp.data.model.category.CategoryRes

data class GetConversationRes(
    @SerializedName("category")
    val category: CategoryRes,
    @SerializedName("conversations")
    val conversations: List<ConversationRes>
)
