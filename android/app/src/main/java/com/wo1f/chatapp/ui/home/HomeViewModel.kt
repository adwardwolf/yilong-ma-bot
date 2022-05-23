package com.wo1f.chatapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wo1f.chatapp.data.model.ConversationRq
import com.wo1f.chatapp.data.repo.ConversationRepo
import com.wo1f.chatapp.data.repo.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: ConversationRepo) : ViewModel() {

    private val _question = MutableStateFlow("")
    val question = _question.asStateFlow()
    private val _answer = MutableStateFlow("")
    val answer = _answer.asStateFlow()

    private val _showAddConverAD = MutableStateFlow(false)
    val showAddConverAD = _showAddConverAD.asStateFlow()

    val addResponse = MutableStateFlow<DataResource<*>>(DataResource.Empty)

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
