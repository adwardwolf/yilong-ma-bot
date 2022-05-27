package com.wo1f.chatapp.ui.category

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.wo1f.chatapp.R
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.ui.base.Content
import com.wo1f.chatapp.ui.base.HandleErrorDialog
import com.wo1f.chatapp.ui.base.HandleLifeCycle
import com.wo1f.chatapp.ui.base.HandleOneTFDialog
import com.wo1f.chatapp.ui.item.CategoryItem
import com.wo1f.chatapp.ui.model.OneTFDialogType
import com.wo1f.chatapp.ui.model.UiState
import com.wo1f.chatapp.ui.utils.AddFAB
import com.wo1f.chatapp.ui.utils.CustomTopAppBarIconStart

internal const val CATEGORY_ALL = "all"

@Composable
fun CategoryScreen(
    goBack: () -> Unit,
    goToConver: (String) -> Unit
) {
    val viewModel: CategoryViewModel = hiltViewModel()
    val baseState by remember { viewModel.baseState }.collectAsState()
    val actionState by remember { viewModel.actionState }.collectAsState()
    val oneTFDialogState by remember { viewModel.oneTFDialogState }.collectAsState()
    val category by remember { viewModel.category }.collectAsState()
    val errorDialogState by remember { viewModel.errorDialogState }.collectAsState()
    val listState by remember { viewModel.listState }.collectAsState()

    Scaffold(
        topBar = {
            CategoryTopBar(goBack)
        },
        content = {
            CategoryContent(
                baseState = baseState,
                listState = listState,
                onItemClick = goToConver
            )
        },
        floatingActionButton = {
            AddFAB(
                onClick = {
                    viewModel.clearText()
                    viewModel.showOneTFDialog(OneTFDialogType.AddCategory)
                }
            )
        }
    )

    LaunchedEffect(actionState) {
        if (actionState.isSuccessful) {
            viewModel.hideOneTFDialog()
        }
    }

    HandleLifeCycle(onStart = { viewModel.load() })

    HandleOneTFDialog(
        text = category,
        dialogState = oneTFDialogState,
        isLoading = actionState.isLoading,
        onButtonClick = { text ->
            viewModel.onOneTFDialogButtonClick(text)
        },
        onCloseClick = {
            viewModel.hideOneTFDialog()
            viewModel.clearText()
        },
        onTextChange = viewModel::onCategoryChange
    )

    HandleErrorDialog(
        dialogState = errorDialogState,
        hideDialog = viewModel::hideErrorDialog
    )
}

@Composable
private fun CategoryTopBar(goBack: () -> Unit) {
    CustomTopAppBarIconStart(
        imageVector = Icons.Default.ArrowBack,
        onClick = goBack,
        label = stringResource(id = R.string.categories)
    )
}

@Composable
private fun CategoryContent(
    baseState: UiState<CategoryState>,
    listState: LazyListState,
    onItemClick: (String) -> Unit
) {
    Content(
        baseState = baseState,
        onSuccess = { state ->
            state?.categoryList?.let { list ->
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    state = listState,
                ) {
                    itemsIndexed(list) { _: Int, item: CategoryRes ->
                        CategoryItem(
                            item = item,
                            onClick = { onItemClick(item.name) },
                            painter = painterResource(id = R.drawable.ic_teach_bot)
                        )
                    }
                }
            }
        }
    )
}
