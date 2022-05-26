package com.wo1f.chatapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.wo1f.chatapp.R
import com.wo1f.chatapp.ui.item.BasicListItem
import com.wo1f.chatapp.ui.utils.W600xh3Text

@Composable
fun HomeScreen(
    goToChat: () -> Unit,
    goToCategory: () -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            HomeTopBar()
        },
        content = {
            HomeContent(
                goToChat = goToChat,
                goToConver = goToCategory
            )
        }
    )
}

@Composable
private fun HomeTopBar() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            W600xh3Text(text = stringResource(id = R.string.home))
        }
    }
}

@Composable
private fun HomeContent(
    goToChat: () -> Unit,
    goToConver: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        BasicListItem(
            name = stringResource(id = R.string.yilong_ma),
            painter = painterResource(id = R.drawable.ic_bot_profile),
            onClick = goToChat
        )
        BasicListItem(
            name = stringResource(id = R.string.conversations),
            painter = painterResource(id = R.drawable.ic_teach_bot),
            onClick = goToConver
        )
    }
}
