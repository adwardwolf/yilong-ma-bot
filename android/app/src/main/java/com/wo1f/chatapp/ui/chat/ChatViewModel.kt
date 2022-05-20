package com.wo1f.chatapp.ui.chat

import androidx.lifecycle.ViewModel
import com.wo1f.chatapp.data.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(): ViewModel() {

    val name = "adwardwo1f"
    private val _chatList = MutableStateFlow(arrayListOf<Chat>())
    val chatList = _chatList.asStateFlow()
    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    fun onTextChange(value: String) {
        _text.value = value
    }

    fun sendChat(text: String) {
        val chat = Chat(
            name = name,
            text = text,
            date = LocalTime.now(),
            type = Chat.Type.SENDER
        )
        val newList = ArrayList(_chatList.value)
        newList.add(chat)
        _chatList.value = newList
    }

    fun receiveChat(chat: Chat) {
        val newList = ArrayList(_chatList.value)
        newList.add(chat)
        _chatList.value = newList
    }

    fun clearText() {
        _text.value = ""
    }
}