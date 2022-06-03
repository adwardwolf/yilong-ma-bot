/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.data.model.conversation

import com.google.gson.annotations.SerializedName

/**
 * Data class represents conversation request
 */
data class ConversationRq(
    @SerializedName("question")
    val question: String,
    @SerializedName("answer")
    val answer: String,
    @SerializedName("category")
    val category: String
)
