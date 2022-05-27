package com.wo1f.chatapp.data.model

import com.google.gson.annotations.SerializedName
import com.wo1f.chatapp.R

data class BaseResponse<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("msg_code")
    val msgCode: Int,
)

object MsgCode {
    private const val CATEGORY_ALREADY_EXIST = 41

    fun getMessageRes(msgCode: Int): Int? {
        return when (msgCode) {
            CATEGORY_ALREADY_EXIST -> R.string.msg_code_category_already_exists
            else -> null
        }
    }
}