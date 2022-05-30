/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wo1f.chatapp.R
import com.wo1f.chatapp.ui.item.HomeItem
import com.wo1f.chatapp.ui.utils.BgImageScaffold
import com.wo1f.chatapp.ui.utils.W600xh3Text
import com.wo1f.chatapp.utils.SocketIO

@Composable
fun HomeScreen(
    goToChat: (String) -> Unit,
    goToCategory: () -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()

    BgImageScaffold(
        topBar = {
            HomeTopBar()
        },
        content = {
            HomeContent(
                goToChat = { goToChat(SocketIO.room) },
                goToConver = goToCategory
            )
        }
    )
}

@Composable
private fun HomeTopBar() {
    TopAppBar(backgroundColor = Color.Transparent) {
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
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            HomeItem(
                text = stringResource(id = R.string.yilong_ma),
                painter = painterResource(id = R.drawable.home_yilong_ma),
                onClick = goToChat
            )
        }

        item {
            HomeItem(
                text = stringResource(R.string.conversations),
                painter = painterResource(id = R.drawable.home_conversations),
                onClick = goToConver
            )
        }
    }
}
