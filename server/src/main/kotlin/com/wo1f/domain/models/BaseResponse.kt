/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val data: T? = null,
    @SerialName("msg_code")
    val msgCode: Int = 0,
)

/**
 * Contains all message codes that front-end can understand
 */
object MsgCode {
    const val CATEGORY_ALREADY_EXIST = 41
}
