/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.ui.model.Action
import com.wo1f.chatapp.ui.state.ActionState
import com.wo1f.chatapp.ui.state.ErrorDialogState
import com.wo1f.chatapp.ui.state.State
import com.wo1f.chatapp.ui.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

/**
 * [S] a main state of the ui
 * [A] actions of the ui
 * [Data] main data of the ui
 */
abstract class BaseViewModel<S : State, A : Action, Data> : ViewModel() {

    private val _baseState = MutableStateFlow(UiState<S>())
    val baseState = _baseState.asStateFlow()
    private val _actionState = MutableStateFlow(ActionState<A>())
    val actionState = _actionState.asStateFlow()
    private val _errorDialogState = MutableStateFlow(ErrorDialogState())
    val errorDialogState = _errorDialogState.asStateFlow()

    internal abstract suspend fun repoCall(): Flow<DataResource<Data>>

    internal abstract suspend fun onLoadSuccess(data: Data)

    internal abstract suspend fun onRefreshSuccess(data: Data)

    /**
     * Load main data by calling [repoCall] and update [baseState] based on the result
     */
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

    /**
     * Load main data by calling [repoCall] and update [baseState] based on the result,
     * loading state will be ignore
     */
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

    internal fun emitState(update: (old: UiState<S>) -> UiState<S>): UiState<S> {
        return _baseState.updateAndGet(update)
    }

    /**
     * Handles an action, and update [actionState] based on the result.
     * [errorDialogState] will be updated if there are any action specific errors
     * @param [type] action type
     * @param [onSuccess] callback that will be triggered if the action is successful
     */
    internal suspend fun <S> DataResource<S>.handleAction(
        type: A,
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

    /**
     * Reset the action state to default state.
     * Call this function after the UI has handled the action state
     */
    internal fun resetActionState() {
        _actionState.value = ActionState()
    }

    internal fun hideErrorDialog() {
        _errorDialogState.value = ErrorDialogState.hide()
    }
}
