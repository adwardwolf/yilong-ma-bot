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
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wo1f.chatapp.data.Chat
import com.wo1f.chatapp.ui.utils.ChatItem
import com.wo1f.chatapp.ui.utils.ChatBottomBar
import com.wo1f.chatapp.ui.utils.CustomTopAppBarIconStart
import com.wo1f.chatapp.utils.SocketIO

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(goBack: () -> Unit) {

    val listState = rememberLazyListState()
    val socketIO = remember { SocketIO() }
    val viewModel: ChatViewModel = hiltViewModel()
    val text by remember { viewModel.text }.collectAsState()
    val chatList by remember { viewModel.chatList }.collectAsState()
    val socketListener = object : SocketIO.OnSocketListener {
        override fun setSocketListener(chat: Chat) {
            viewModel.receiveChat(chat)
        }
    }

    LaunchedEffect(socketIO) {
        socketIO.onReceiving(socketListener)
    }

    LaunchedEffect(chatList.size) {
        if (chatList.isNotEmpty()) {
            listState.scrollToItem(chatList.size - 1)
        }
    }

    Scaffold(
        topBar = {
            ChatTopBar(goBack)
        },
        content = { paddingValues ->
            ChatContent(
                paddingValues = paddingValues,
                chatList = chatList,
                listState = listState
            )
        },
        bottomBar = {
            ChatBottomBar(
                text = text,
                shouldBeEnabled = true,
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
    CustomTopAppBarIconStart(
        imageVector = Icons.Default.ArrowBack,
        onClick = goBack,
        label = "Chat"
    )
}

@Composable
private fun ChatContent(
    paddingValues: PaddingValues,
    chatList: List<Chat>,
    listState: LazyListState
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Bottom,
        state = listState
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        itemsIndexed(chatList) { _: Int, chat: Chat ->
            ChatItem(chat)
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}