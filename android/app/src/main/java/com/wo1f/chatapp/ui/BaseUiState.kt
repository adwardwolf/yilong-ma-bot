package com.wo1f.chatapp.ui

data class BaseUiState<T>(
    val state: T? = null,
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var errorMsg: Int? = null,
    var errorDialogMsg: Int? = null
) {

    companion object {
        fun <T> success(state: T?): BaseUiState<T> {
            return BaseUiState(isSuccessful = true, state = state)
        }

        fun <T> error(
            msg: Int?,
            dialogMsg: Int?
        ) = BaseUiState<T>(errorMsg = msg, errorDialogMsg = dialogMsg)

        fun <T> loading() = BaseUiState<T>(isLoading = true)
    }
}
