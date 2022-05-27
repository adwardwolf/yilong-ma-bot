/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.ui.Action
import com.wo1f.chatapp.ui.model.ActionState
import com.wo1f.chatapp.ui.model.State
import com.wo1f.chatapp.ui.model.UiState
import com.wo1f.chatapp.ui.state.ErrorDialogState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : State, AS : Action, Res> : ViewModel() {

    private val _baseState = MutableStateFlow(UiState<S>())
    val baseState = _baseState.asStateFlow()
    private val _actionState = MutableStateFlow(ActionState<AS>())
    val actionState = _actionState.asStateFlow()
    private val _errorDialogState = MutableStateFlow(ErrorDialogState())
    val errorDialogState = _errorDialogState.asStateFlow()

    internal abstract suspend fun repoCall(): Flow<DataResource<Res>>

    internal abstract suspend fun onLoadSuccess(data: Res)

    internal abstract suspend fun onRefreshSuccess(data: Res)

    internal open fun load() {
        viewModelScope.launch {
            repoCall().collect { result ->
                result.onSuccess { data ->
                    if (data != null) {
                        onLoadSuccess(data)
                    }
                }.onFailure { msg, dialogMsg ->
                    emitState { UiState.error(msg, dialogMsg) }
                }.onLoading {
                    emitState { UiState.loading() }
                }
            }
        }
    }

    internal open fun refresh() {
        viewModelScope.launch {
            repoCall().collect { result ->
                result.onSuccess { data ->
                    if (data != null) {
                        onRefreshSuccess(data)
                    }
                }.onFailure { msg, dialogMsg ->
                    emitState { UiState.error(msg, dialogMsg) }
                }.onLoading {}
            }
        }
    }

    internal fun emitState(
        update: (old: UiState<S>) -> UiState<S>
    ): UiState<S> {
        return _baseState.updateAndGet(update)
    }

    internal suspend fun <S> DataResource<S>.handleActionResult(
        type: AS,
        onSuccess: (() -> Unit)? = null
    ) {
        this.onSuccess {
            _actionState.emit(ActionState.success(type))
            if (onSuccess != null) {
                onSuccess()
            }
            refresh()
        }.onFailure { msg, dialogMsg ->
            _actionState.emit(ActionState.error(msg, dialogMsg))
            if (dialogMsg != null) {
                _errorDialogState.emit(ErrorDialogState.show(dialogMsg))
            }
        }.onLoading {
            _actionState.emit(ActionState.loading())
        }
    }

    internal fun resetActionState() {
        _actionState.value = ActionState()
    }

    internal fun hideErrorDialog() {
        _errorDialogState.value = ErrorDialogState.hide()
    }
}
