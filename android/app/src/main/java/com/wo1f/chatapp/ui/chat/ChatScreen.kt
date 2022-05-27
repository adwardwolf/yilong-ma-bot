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
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.wo1f.chatapp.R
import com.wo1f.chatapp.data.model.Chat
import com.wo1f.chatapp.ui.item.ChatItem
import com.wo1f.chatapp.ui.utils.ChatBottomBar
import com.wo1f.chatapp.ui.utils.CustomTopAppBarIconStart
import com.wo1f.chatapp.utils.SocketIO

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(goBack: () -> Unit) {

    val lifecycleOwner = LocalLifecycleOwner.current
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

    val onStart: () -> Unit = {
    }

    val onStop: () -> Unit = {
        socketIO.disconnect()
    }

    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
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
        label = stringResource(id = R.string.chat)
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
            Spacer(modifier = Modifier.height(8.dp))
        }

        itemsIndexed(chatList) { _: Int, chat: Chat ->
            ChatItem(chat)
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
