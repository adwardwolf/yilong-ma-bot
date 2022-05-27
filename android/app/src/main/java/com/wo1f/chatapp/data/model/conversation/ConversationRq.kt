/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.data.model.conversation

import com.google.gson.annotations.SerializedName

data class ConversationRq(
    @SerializedName("question")
    val question: String,
    @SerializedName("answer")
    val answer: String,
    @SerializedName("category")
    val category: String
)
