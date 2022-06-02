/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wo1f.chatapp.R
import com.wo1f.chatapp.data.model.chat.ChatRes
import com.wo1f.chatapp.data.model.chat.toChat
import com.wo1f.chatapp.ui.base.Content
import com.wo1f.chatapp.ui.base.HandleLifeCycle
import com.wo1f.chatapp.ui.item.ChatItem
import com.wo1f.chatapp.ui.state.UiState
import com.wo1f.chatapp.ui.utils.BgImageScaffold
import com.wo1f.chatapp.ui.utils.ChatBottomBar
import com.wo1f.chatapp.ui.utils.TopAppBarIconStart
import com.wo1f.chatapp.ui.utils.keyboardAsState
import com.wo1f.chatapp.utils.SocketIO
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    goBack: () -> Unit
) {

    val listState = rememberLazyListState()
    val socketIO = remember { SocketIO() }
    val text by remember { viewModel.text }.collectAsState()
    val baseState by remember { viewModel.baseState }.collectAsState()
    val socketListener = object : SocketIO.OnSocketListener {
        override fun setSocketListener(chat: ChatRes) {
            viewModel.receiveChat(chat)
        }
    }
    val isKeyboardOpen by keyboardAsState()

    HandleLifeCycle(
        onStart = { viewModel.load() },
        onStop = { socketIO.disconnect() }
    )

    LaunchedEffect(isKeyboardOpen) {
        delay(100)
        baseState.state?.chatList?.let { list ->
            if (list.isNotEmpty()) {
                listState.animateScrollToItem(list.size - 1)
            }
        }
    }

    LaunchedEffect(socketIO) {
        socketIO.onReceiving(socketListener)
    }

    LaunchedEffect(baseState.state?.chatList?.size) {
        baseState.state?.chatList?.let { list ->
            if (list.isNotEmpty()) {
                listState.scrollToItem(list.size - 1)
            }
        }
    }

    BgImageScaffold(
        topBar = {
            ChatTopBar(goBack)
        },
        content = { paddingValues ->
            ChatContent(
                paddingValues = paddingValues,
                baseState = baseState,
                listState = listState
            )
        },
        bottomBar = {
            ChatBottomBar(
                text = text,
                enabled = text.isNotBlank(),
                onTextChange = viewModel::onTextChange,
                onSendClick = {
                    viewModel.sendChat(it)
                    socketIO.sendMessage(it)
                    viewModel.clearText()
                }
            )
        }
    )
}

@Composable
private fun ChatTopBar(goBack: () -> Unit) {
    TopAppBarIconStart(
        imageVector = Icons.Default.ArrowBack,
        onClick = goBack,
        label = stringResource(id = R.string.chat),
        contentDescription = stringResource(id = R.string.go_back)
    )
}

@Composable
private fun ChatContent(
    paddingValues: PaddingValues,
    baseState: UiState<ChatState>,
    listState: LazyListState
) {
    Content(
        baseState = baseState,
        onSuccess = { state ->
            state?.chatList?.let { list ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Bottom,
                    state = listState
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    itemsIndexed(list) { _: Int, chat: ChatRes ->
                        ChatItem(chat.toChat(SocketIO.name))
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    )
}
