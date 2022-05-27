package com.wo1f.chatapp.ui.model

import com.wo1f.chatapp.ui.Action

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
