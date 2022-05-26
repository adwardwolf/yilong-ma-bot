package com.wo1f.chatapp.ui.state

import com.wo1f.chatapp.ui.model.TwoActionDialogType

data class ErrorDialogState(
    val show: Boolean = false,
    val message: Int? = null
) {
    companion object {
        fun show(message: Int) = ErrorDialogState(true, message)
        fun hide() = ErrorDialogState()
    }
}

data class TwoActionDialogState(
    val show: Boolean = false,
    val type: TwoActionDialogType? = null
) {
    companion object {
        fun show(type: TwoActionDialogType) = TwoActionDialogState(true, type)
        fun hide() = TwoActionDialogState()
    }
}
