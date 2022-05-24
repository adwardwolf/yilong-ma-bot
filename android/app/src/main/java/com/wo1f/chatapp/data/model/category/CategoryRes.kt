package com.wo1f.chatapp.data.model.category

import com.google.gson.annotations.SerializedName

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
