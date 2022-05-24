package com.wo1f.chatapp.data.model.category

import com.google.gson.annotations.SerializedName

data class CategoryRq(
    @SerializedName("name")
    val name: String
)
