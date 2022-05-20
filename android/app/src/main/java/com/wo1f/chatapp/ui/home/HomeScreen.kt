package com.wo1f.chatapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wo1f.chatapp.ui.utils.UserTemplate
import com.wo1f.chatapp.ui.utils.W500xh4Text

@Composable
fun HomeScreen(goToChat: () -> Unit) {

    Scaffold(
        topBar = {
            HomeTopBar()
        },
        content = {
            HomeContent(goToChat = goToChat)
        }
    )
}

@Composable
private fun HomeTopBar() {
    TopAppBar {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            W500xh4Text(text = "Home")
        }
    }
}

@Composable
private fun HomeContent(goToChat: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            UserTemplate(name = "Yilong Ma", onClick = goToChat)
        }
    }
}