package com.wo1f.chatapp.ui.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.wo1f.chatapp.R
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.ui.item.BasicListItem
import com.wo1f.chatapp.ui.utils.CustomTopAppBarIconStart
import com.wo1f.chatapp.ui.utils.W500xh4Text

@Composable
fun CategoryScreen(
    goBack: () -> Unit,
    goToConver: (String) -> Unit
) {
    val viewModel: CategoryViewModel = hiltViewModel()
    val state by remember { viewModel.state }.collectAsState()

    Scaffold(
        topBar = {
            CategoryTopBar(goBack)
        },
        content = {
            CategoryContent(
                state = state,
                onItemClick = goToConver
            )
        }
    )
}

@Composable
private fun CategoryTopBar(goBack: () -> Unit) {
    CustomTopAppBarIconStart(
        imageVector = Icons.Default.ArrowBack,
        onClick = goBack,
        label = "Categories"
    )
}

@Composable
private fun CategoryContent(
    state: CategoryState,
    onItemClick: (String) -> Unit
) {
    if (state.isSuccessful && state.categoryList != null) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                BasicListItem(
                    name = "all",
                    onClick = { onItemClick("all") },
                    painter = painterResource(id = R.drawable.ic_teach_bot)
                )
            }

            itemsIndexed(state.categoryList) { index: Int, item: CategoryRes ->
                BasicListItem(
                    name = item.name,
                    onClick = { onItemClick(item.name) },
                    painter = painterResource(id = R.drawable.ic_teach_bot)
                )
            }
        }
    }

    state.error?.let { message ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            W500xh4Text(text = message)
        }
    }
}
