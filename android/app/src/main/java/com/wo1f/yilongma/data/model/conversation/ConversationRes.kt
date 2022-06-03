/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.data.model.conversation

import com.google.gson.annotations.SerializedName

/**
 * Data class represents conversation response
 */
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
