/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.ui.model

sealed interface Action

sealed class CategoryAction : Action {

    object Add : CategoryAction()
}

sealed class ConverAction : Action {

    object Add : ConverAction()

    object Update : ConverAction()

    object Delete : ConverAction()

    object UpdateCategory : ConverAction()

    object DeleteCategory : ConverAction()
}

sealed class ChatAction : Action
