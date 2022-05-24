package com.wo1f.chatapp.ui.conversations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.model.conversation.ConversationRes
import com.wo1f.chatapp.data.model.conversation.ConversationRq
import com.wo1f.chatapp.data.repo.ConversationRepo
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

    val name = savedStateHandle.get<String>("name") ?: ""
    private val _state = MutableStateFlow(ConverState())
    val state = _state.asStateFlow()
    private val _question = MutableStateFlow("")
    val question = _question.asStateFlow()
    private val _answer = MutableStateFlow("")
    val answer = _answer.asStateFlow()
    private val _showAddConverAD = MutableStateFlow(false)
    val showAddConverAD = _showAddConverAD.asStateFlow()
    val addResponse = MutableStateFlow<DataResource<*>>(DataResource.Empty)

    init {
        getConverList()
    }

    private fun getConverList() {
        viewModelScope.launch {
            repo.getConversations(name).collect { result ->
                result.onSuccess { data ->
                    if (data != null) {
                        _state.emit(ConverState.success(data))
                    }
                }.onFailure { _state.emit(ConverState.error(it)) }
                    .onLoading { _state.emit(ConverState.loading()) }
            }
        }
    }

    fun add(question: String, answer: String) {
        val request = ConversationRq(question, answer, GREETINGS)
        viewModelScope.launch {
            repo.addConversation(request).collect {
                addResponse.emit(it)
            }
        }
    }

    fun setAddConverDB(value: Boolean) {
        _showAddConverAD.value = value
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

    companion object {
        const val GREETINGS = "greetings"
    }
}

data class ConverState(
    val converList: List<ConversationRes>? = null,
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var error: String? = null
) {

    companion object {
        fun success(converList: List<ConversationRes>?): ConverState {
            return ConverState(isSuccessful = true, converList = converList)
        }
        fun error(message: String) = ConverState(error = message)
        fun loading() = ConverState(isLoading = true)
    }
}
