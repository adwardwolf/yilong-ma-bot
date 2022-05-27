package com.wo1f.chatapp.ui.model

data class UiState<T>(
    val state: T? = null,
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var errorMsg: Int? = null,
    var errorDialogMsg: Int? = null
) {

    companion object {
        fun <T> success(state: T?): UiState<T> {
            return UiState(isSuccessful = true, state = state)
        }

        fun <T> error(
            msg: Int?,
            dialogMsg: Int?
        ) = UiState<T>(errorMsg = msg, errorDialogMsg = dialogMsg)

        fun <T> loading() = UiState<T>(isLoading = true)
    }
}
