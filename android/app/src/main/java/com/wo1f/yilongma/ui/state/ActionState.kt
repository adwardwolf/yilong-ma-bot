/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.ui.state

import com.wo1f.yilongma.ui.model.Action

data class ActionState<T : Action>(
    var type: T? = null,
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var errorMsg: Int? = null,
    var errorDialogMsg: Int? = null,

) {

    companion object {
        fun <T : Action> success(type: T): ActionState<T> {
            return ActionState(isSuccessful = true, type = type)
        }

        fun <T : Action> error(
            msg: Int?,
            dialogMsg: Int?
        ) = ActionState<T>(errorMsg = msg, errorDialogMsg = dialogMsg)

        fun <T : Action> loading() = ActionState<T>(isLoading = true)
    }
}
