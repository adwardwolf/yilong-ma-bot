package com.wo1f.chatapp.ui.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.wo1f.chatapp.R
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.ui.BaseUiState
import com.wo1f.chatapp.ui.item.CategoryItem
import com.wo1f.chatapp.ui.utils.AddFAB
import com.wo1f.chatapp.ui.utils.Content
import com.wo1f.chatapp.ui.utils.CustomButton
import com.wo1f.chatapp.ui.utils.CustomOutlineTextField
import com.wo1f.chatapp.ui.utils.CustomTopAppBarIconStart
import com.wo1f.chatapp.ui.utils.HandleErrorDialog
import com.wo1f.chatapp.ui.utils.W600xh3Text

@Composable
fun CategoryScreen(
    goBack: () -> Unit,
    goToConver: (String) -> Unit
) {
    val viewModel: CategoryViewModel = hiltViewModel()
    val baseState by remember { viewModel.baseState }.collectAsState()
    val addCategoryState by remember { viewModel.actionState }.collectAsState()
    val showAddCategoryAD by remember { viewModel.showAddCategoryAD }.collectAsState()
    val category by remember { viewModel.category }.collectAsState()
    val errorDialogState by remember { viewModel.errorDialogState }.collectAsState()

    Scaffold(
        topBar = {
            CategoryTopBar(goBack)
        },
        content = {
            CategoryContent(
                baseState = baseState,
                onItemClick = goToConver
            )
        },
        floatingActionButton = {
            AddFAB(onClick = { viewModel.setAddCategoryAD(true) })
        }
    )

    if (addCategoryState.isSuccessful) {
        viewModel.clearText()
        viewModel.setAddCategoryAD(false)
    }

    if (showAddCategoryAD) {
        AddCategoryDialog(
            category = category,
            isLoading = addCategoryState.isLoading,
            onCategoryChange = viewModel::onCategoryChange,
            onDismissRequest = { viewModel.setAddCategoryAD(false) },
            onClick = { viewModel.addCategory(category) }
        )
    }

    HandleErrorDialog(
        dialogState = errorDialogState,
        hideDialog = viewModel::hideDialog
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
    baseState: BaseUiState<CategoryState>,
    onItemClick: (String) -> Unit
) {
    Content(
        baseState = baseState,
        onSuccess = { state ->
            state?.categoryList?.let { list ->
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
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

@Composable
fun AddCategoryDialog(
    category: String,
    isLoading: Boolean,
    onCategoryChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            color = MaterialTheme.colors.background,
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                W600xh3Text(text = stringResource(id = R.string.add_new_category))

                CustomOutlineTextField(
                    value = category,
                    label = stringResource(id = R.string.category),
                    showKeyboard = true,
                    onValueChange = onCategoryChange
                )

                Spacer(Modifier.height(12.dp))

                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Add",
                    shouldBeEnabled = category.isNotBlank(),
                    isLoading = isLoading,
                    onClick = onClick
                )
            }
        }
    }
}
