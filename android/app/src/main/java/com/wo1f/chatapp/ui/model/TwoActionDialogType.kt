package com.wo1f.chatapp.ui.model

import com.wo1f.chatapp.R

sealed class TwoActionDialogType(
    val message: Int,
    val actionText: Int
) {
    object Delete : TwoActionDialogType(R.string.delete_item_message, R.string.delete)
}
