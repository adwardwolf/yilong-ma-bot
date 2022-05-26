package com.wo1f.chatapp.ui.conversations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wo1f.chatapp.data.model.conversation.ConversationRes
import com.wo1f.chatapp.data.model.conversation.ConversationRq
import com.wo1f.chatapp.data.repo.ConversationRepo
import com.wo1f.chatapp.ui.BaseUiState
import com.wo1f.chatapp.ui.model.TwoActionDialogType
import com.wo1f.chatapp.ui.state.ErrorDialogState
import com.wo1f.chatapp.ui.state.TwoActionDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverViewModel @Inject constructor(
    private val repo: ConversationRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val name: String = savedStateHandle.get<String>("name")
        ?: throw IllegalStateException("Category can't be empty")
    private val _baseState = MutableStateFlow<BaseUiState<ConverState>>(BaseUiState())
    val baseState = _baseState.asStateFlow()
    private val _actionState = MutableStateFlow<BaseUiState<ConverState>>(BaseUiState())
    val actionState = _actionState.asStateFlow()
    private val _errorErrorDialogState = MutableStateFlow(ErrorDialogState())
    val errorDialogState = _errorErrorDialogState.asStateFlow()
    private val _question = MutableStateFlow("")
    val question = _question.asStateFlow()
    private val _answer = MutableStateFlow("")
    val answer = _answer.asStateFlow()
    private val _showAddConverAD = MutableStateFlow(false)
    val showAddConverAD = _showAddConverAD.asStateFlow()
    private val _showUpdateConverAD = MutableStateFlow(false)
    val showUpdateConverAD = _showUpdateConverAD.asStateFlow()
    private val _clickedItem = MutableStateFlow<ConversationRes?>(null)
    val clickedItem = _clickedItem.asStateFlow()
    private val _twoActionDialogState = MutableStateFlow(TwoActionDialogState())
    val twoActionDialogState = _twoActionDialogState.asStateFlow()

    init {
        getConverList()
    }

    private fun getConverList() {
        viewModelScope.launch {
            repo.getConversations(name).collect { result ->
                result.onSuccess { data ->
                    if (data != null) {
                        _baseState.emit(BaseUiState.success(ConverState(data)))
                    }
                }.onFailure { msg, dialogMsg ->
                    _baseState.emit(BaseUiState.error(msg, dialogMsg))
                }.onLoading {
                    _baseState.emit(BaseUiState.loading())
                }
            }
        }
    }

    fun addConversation(question: String, answer: String) {
        val request = ConversationRq(question, answer, name)
        viewModelScope.launch {
            repo.addConversation(request).collect { result ->
                result.onSuccess {
                    _actionState.emit(BaseUiState.success(ConverState(null)))
                    getConverList()
                }.onFailure { msg, dialogMsg ->
                    _actionState.emit(BaseUiState.error(msg, dialogMsg))
                    if (dialogMsg != null) {
                        _errorErrorDialogState.emit(ErrorDialogState.show(dialogMsg))
                    }
                }.onLoading {
                    _actionState.emit(BaseUiState.loading())
                }
            }
        }
    }

    fun updateConversation(question: String, answer: String) {
        val request = ConversationRq(question, answer, name)
        viewModelScope.launch {
            _clickedItem.value?.id?.let { id ->
                repo.updateConversation(id, request).collect { result ->
                    result.onSuccess {
                        _actionState.emit(BaseUiState.success(ConverState(null)))
                        getConverList()
                    }.onFailure { msg, dialogMsg ->
                        _actionState.emit(BaseUiState.error(msg, dialogMsg))
                        if (dialogMsg != null) {
                            _errorErrorDialogState.emit(ErrorDialogState.show(dialogMsg))
                        }
                    }.onLoading {
                        _actionState.emit(BaseUiState.loading())
                    }
                }
            }
        }
    }

    private fun deleteConversation() {
        _clickedItem.value?.id?.let { id ->
            viewModelScope.launch {
                repo.deleteConversation(id).collect { result ->
                    result.onSuccess {
                        _actionState.emit(BaseUiState.success(ConverState(null)))
                        getConverList()
                    }.onFailure { msg, dialogMsg ->
                        _actionState.emit(BaseUiState.error(msg, dialogMsg))
                        if (dialogMsg != null) {
                            _errorErrorDialogState.emit(ErrorDialogState.show(dialogMsg))
                        }
                    }.onLoading {
                        _actionState.emit(BaseUiState.loading())
                    }
                }
            }
        }
    }

    fun onEditClick(item: ConversationRes) {
        setClickedItem(item)
        onQuestionChange(item.question)
        onAnswerChange(item.answer)
        setUpdateConverAD(true)
    }

    fun onDeleteClick(item: ConversationRes) {
        setClickedItem(item)
        showTwoActionDialog(TwoActionDialogType.Delete)
    }

    fun setAddConverAD(value: Boolean) {
        _showAddConverAD.value = value
    }

    fun setUpdateConverAD(value: Boolean) {
        _showUpdateConverAD.value = value
    }

    fun onQuestionChange(value: String) {
        _question.value = value
    }

    fun onAnswerChange(value: String) {
        _answer.value = value
    }

    fun clearText() {
        _question.value = ""
        _answer.value = ""
    }

    fun hideDialog() {
        _errorErrorDialogState.value = ErrorDialogState.hide()
    }

    private fun setClickedItem(item: ConversationRes) {
        _clickedItem.value = item
    }

    private fun showTwoActionDialog(type: TwoActionDialogType) {
        _twoActionDialogState.value = TwoActionDialogState.show(type)
    }

    fun hideTwoActionDialog() {
        _twoActionDialogState.value = TwoActionDialogState.hide()
    }

    fun onTwoDialogActionClick() {
        when (_twoActionDialogState.value.type) {
            TwoActionDialogType.Delete -> deleteConversation()
        }
    }
}

data class ConverState(
    val converList: List<ConversationRes>? = null
)
