package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.wo1f.chatapp.ui.BaseUiState
import com.wo1f.chatapp.ui.state.ErrorDialogState
import com.wo1f.chatapp.ui.state.TwoActionDialogState

@Composable
fun CircleLoadingPI() {
    CircularProgressIndicator(color = MaterialTheme.colors.secondary)
}

@Composable
fun <T> Content(
    baseState: BaseUiState<T>,
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

@Composable
fun HandleTwoActionDialog(
    dialogState: TwoActionDialogState,
    onActionClick: () -> Unit,
    hideDialog: () -> Unit
) {
    if (dialogState.show && dialogState.type != null) {
        val message = dialogState.type.message
        val actionText = dialogState.type.actionText
        TwoActionDialog(
            text = stringResource(id = message),
            actionText = stringResource(id = actionText),
            onCancelClick = hideDialog,
            onActionClick = onActionClick
        )
    }
}

@Composable
fun BoxMaxSizeCenter(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
