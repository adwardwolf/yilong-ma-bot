/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.state

data class ErrorDialogState(
    val show: Boolean = false,
    val message: Int? = null
) {
    companion object {
        fun show(message: Int) = ErrorDialogState(true, message)
        fun hide() = ErrorDialogState()
    }
}

data class DialogState<T>(
    val show: Boolean = false,
    val type: T? = null
) {
    companion object {
        fun <T> show(type: T) = DialogState(true, type)
        fun <T> hide() = DialogState<T>()
    }
}
