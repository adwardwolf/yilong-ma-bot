/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.data.model.conversation

import com.google.gson.annotations.SerializedName

data class ConversationRes(
    @SerializedName("id")
    val id: String,
    @SerializedName("answer")
    val answer: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("createdAt")
    val createdAt: String
)
