/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.data.model.category

import com.google.gson.annotations.SerializedName

/**
 * Represents a category response from service
 * @property [count] number of all conversations in this category
 */
data class CategoryRes(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("count")
    val count: Long
)
