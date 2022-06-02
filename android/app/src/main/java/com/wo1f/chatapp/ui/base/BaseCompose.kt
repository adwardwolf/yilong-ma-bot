/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.wo1f.chatapp.ui.model.OneTFDialogType
import com.wo1f.chatapp.ui.model.TwoActionDialogType
import com.wo1f.chatapp.ui.state.DialogState
import com.wo1f.chatapp.ui.state.ErrorDialogState
import com.wo1f.chatapp.ui.state.State
import com.wo1f.chatapp.ui.state.UiState
import com.wo1f.chatapp.ui.utils.BoxMaxSizeCenter
import com.wo1f.chatapp.ui.utils.CircleLoadingPI
import com.wo1f.chatapp.ui.utils.OkDialog
import com.wo1f.chatapp.ui.utils.OneTextFieldDialog
import com.wo1f.chatapp.ui.utils.TwoActionDialog
import com.wo1f.chatapp.ui.utils.W600xh3Text

/**
 * A generic function that handle success, loading, error state of [baseState]
 */
@Composable
fun <T : State> Content(
    baseState: UiState<T>,
    onSuccess: @Composable (T?) -> Unit
) {
    if (baseState.isSuccessful) {
        onSuccess(baseState.state)
    }

    if (baseState.isLoading) {
        BoxMaxSizeCenter { CircleLoadingPI() }
    }

    baseState.errorMsg?.let { message ->
        BoxMaxSizeCenter {
            W600xh3Text(text = stringResource(id = message))
        }
    }
}

/**
 * A function that handles [OkDialog] based on [dialogState]
 */
@Composable
fun HandleErrorDialog(dialogState: ErrorDialogState, hideDialog: () -> Unit) {
    if (dialogState.show) {
        dialogState.message?.let { message ->
            OkDialog(
                text = stringResource(id = message),
                onDismissRequest = hideDialog
            )
        }
    }
}

/**
 * A function that handles [TwoActionDialog] based on [dialogState]
 */
@Composable
fun HandleTwoActionDialog(
    dialogState: DialogState<TwoActionDialogType>,
    onPosClick: () -> Unit,
    hideDialog: () -> Unit
) {
    if (dialogState.show && dialogState.type != null) {
        val message = dialogState.type.message
        val actionText = dialogState.type.posText
        TwoActionDialog(
            text = stringResource(id = message),
            actionText = stringResource(id = actionText),
            onCancelClick = hideDialog,
            onPosClick = onPosClick
        )
    }
}

/**
 * A function that handles [OneTextFieldDialog] based on [dialogState]
 */
@Composable
fun HandleOneTFDialog(
    text: String,
    dialogState: DialogState<OneTFDialogType>,
    isLoading: Boolean,
    onButtonClick: (text: String) -> Unit,
    onCloseClick: () -> Unit,
    onTextChange: (String) -> Unit
) {
    if (dialogState.show && dialogState.type != null) {
        OneTextFieldDialog(
            text = text,
            isLoading = isLoading,
            type = dialogState.type,
            tfTestTag = stringResource(id = dialogState.type.hint),
            onTextChange = onTextChange,
            onCloseClick = onCloseClick,
            onButtonClick = onButtonClick
        )
    }
}

@Composable
fun HandleLifeCycle(onStart: () -> Unit, onStop: (() -> Unit)? = null) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop?.let { it() }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
