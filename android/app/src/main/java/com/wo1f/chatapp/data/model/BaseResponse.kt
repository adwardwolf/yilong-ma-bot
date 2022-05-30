/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.data.model

import com.google.gson.annotations.SerializedName
import com.wo1f.chatapp.R

/**
 * @property msgCode A domain message code
 */
data class BaseResponse<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("msg_code")
    val msgCode: Int,
)

/**
 * Contains all message code that might return from service
 */
object MsgCode {
    private const val CATEGORY_ALREADY_EXIST = 41

    /**
     * Return string id based on [msgCode]
     */
    fun getMessageRes(msgCode: Int): Int? {
        return when (msgCode) {
            CATEGORY_ALREADY_EXIST -> R.string.msg_code_category_already_exists
            else -> null
        }
    }
}
