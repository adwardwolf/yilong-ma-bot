/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.state

data class UiState<T : State>(
    val state: T? = null,
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var errorMsg: Int? = null,
    var errorDialogMsg: Int? = null
) {

    companion object {
        fun <T : State> success(state: T?): UiState<T> {
            return UiState(isSuccessful = true, state = state)
        }

        fun <T : State> error(
            msg: Int?,
            dialogMsg: Int?
        ) = UiState<T>(errorMsg = msg, errorDialogMsg = dialogMsg)

        fun <T : State> loading() = UiState<T>(isLoading = true)
    }
}
