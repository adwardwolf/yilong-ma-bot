/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.model

import com.wo1f.chatapp.R
import com.wo1f.chatapp.ui.utils.OneTextFieldDialog
import com.wo1f.chatapp.ui.utils.TwoActionDialog

sealed interface Dialog

/**
 * A class that contains necessary data for [TwoActionDialog]
 * @property message Dialog's message
 * @property posText positive action's text
 */
sealed class TwoActionDialogType(
    val message: Int,
    val posText: Int
) : Dialog {
    object Delete : TwoActionDialogType(R.string.delete_item_message, R.string.delete)
}

/**
 * A class that contains necessary data for [OneTextFieldDialog]
 */
sealed class OneTFDialogType(
    val title: Int,
    val message: Any? = null,
    val textButton: Int,
    val buttonColor: Int,
    val textButtonColor: Int,
    val hint: Int
) : Dialog {
    class DeleteCategory(val name: String) : OneTFDialogType(
        title = R.string.delete_category,
        message = "Write \"$name\" to delete category.",
        textButton = R.string.delete,
        buttonColor = R.color.red_candy,
        textButtonColor = R.color.white,
        hint = R.string.category_name
    )

    object AddCategory : OneTFDialogType(
        title = R.string.add_new_category,
        message = null,
        textButton = R.string.add,
        buttonColor = R.color.light_yellow_green,
        textButtonColor = R.color.dark_blue,
        hint = R.string.category_name
    )

    object UpdateCategory : OneTFDialogType(
        title = R.string.update_category,
        message = null,
        textButton = R.string.update,
        buttonColor = R.color.light_yellow_green,
        textButtonColor = R.color.dark_blue,
        hint = R.string.category_name
    )
}
