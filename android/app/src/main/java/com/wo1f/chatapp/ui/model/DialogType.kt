package com.wo1f.chatapp.ui.model

import com.wo1f.chatapp.R

sealed class TwoActionDialogType(
    val message: Int,
    val actionText: Int
) {
    object Delete : TwoActionDialogType(R.string.delete_item_message, R.string.delete)
}

sealed class OneTFDialogType(
    val title: Int,
    val message: Any? = null,
    val textButton: Int,
    val buttonColor: Int,
    val textColor: Int,
    val hint: Int
) {
    class DeleteCategory(val name: String) : OneTFDialogType(
        title = R.string.delete_category,
        message = "Write \"$name\" to delete category.",
        textButton = R.string.delete,
        buttonColor = R.color.red_candy,
        textColor = R.color.white,
        hint = R.string.category_name
    )

    object AddCategory : OneTFDialogType(
        title = R.string.add_new_category,
        message = null,
        textButton = R.string.add,
        buttonColor = R.color.light_yellow_green,
        textColor = R.color.dark_blue,
        hint = R.string.category_name
    )

    object UpdateCategory : OneTFDialogType(
        title = R.string.update_category,
        message = null,
        textButton = R.string.update,
        buttonColor = R.color.light_yellow_green,
        textColor = R.color.dark_blue,
        hint = R.string.category_name
    )
}
