/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.chat

import androidx.lifecycle.SavedStateHandle
import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.model.chat.ChatRes
import com.wo1f.chatapp.data.repo.ChatRepo
import com.wo1f.chatapp.ui.base.BaseViewModel
import com.wo1f.chatapp.ui.model.ChatAction
import com.wo1f.chatapp.ui.state.State
import com.wo1f.chatapp.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.bson.types.ObjectId
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo: ChatRepo,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ChatState, ChatAction, List<ChatRes>>() {

    private val roomName = savedStateHandle.get<String>("room")
        ?: throw IllegalStateException("Room can't be empty")
    val name = "adwardwo1f"
    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    override suspend fun repoCall(): Flow<DataResource<List<ChatRes>>> {
        return repo.getAll(roomName)
    }

    override suspend fun onLoadSuccess(data: List<ChatRes>?) {
        emitState { UiState.success(ChatState(data)) }
    }

    override suspend fun onRefreshSuccess(data: List<ChatRes>?) {
        emitState { UiState.success(ChatState(data)) }
    }

    internal fun onTextChange(value: String) {
        _text.value = value
    }

    internal fun sendChat(text: String) {
        val id = ObjectId.get()
        val chat = ChatRes(
            id = id.toString(),
            userName = name,
            roomName = roomName,
            text = text,
            date = LocalDate.now().toString(),
        )
        emitState { old ->
            old.state?.chatList?.let { list ->
                val newList = ArrayList(list)
                newList.add(chat)
                old.copy(old.state.copy(chatList = newList))
            } ?: old.copy()
        }
    }

    internal fun receiveChat(chat: ChatRes) {
        emitState { old ->
            old.state?.chatList?.let { list ->
                val newList = ArrayList(list)
                newList.add(chat)
                old.copy(old.state.copy(chatList = newList))
            } ?: old.copy()
        }
    }

    internal fun clearText() {
        _text.value = ""
    }
}

data class ChatState(
    val chatList: List<ChatRes>? = null
) : State
