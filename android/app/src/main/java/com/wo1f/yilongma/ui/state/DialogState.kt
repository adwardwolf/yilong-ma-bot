/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.ui.state

import com.wo1f.yilongma.ui.model.Dialog

data class ErrorDialogState(
    val show: Boolean = false,
    val message: Int? = null
) {
    companion object {
        fun show(message: Int) = ErrorDialogState(true, message)
        fun hide() = ErrorDialogState()
    }
}

/**
 * [T] Dialog type
 */
data class DialogState<T : Dialog>(
    val show: Boolean = false,
    val type: T? = null
) {
    companion object {
        fun <T : Dialog> show(type: T) = DialogState(true, type)
        fun <T : Dialog> hide() = DialogState<T>()
    }
}
