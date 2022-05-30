/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.wo1f.chatapp.ui.utils.BgImageScaffold
import com.wo1f.chatapp.ui.utils.CloseIcon
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
    val search by remember { viewModel.search }.collectAsState()
    val focusManager = LocalFocusManager.current

    BgImageScaffold(
        topBar = {
            CategoryTopBar(goBack)
        },
        content = {
            CategoryContent(
                baseState = baseState,
                listState = listState,
                search = search,
                onSearchTextChange = viewModel::onSearchTextChange,
                onItemClick = goToConver,
                onClearSearchClick = viewModel::clearSearchText
            )
        },
        floatingActionButton = {
            AddFAB(
                onClick = {
                    viewModel.clearText()
                    viewModel.showOneTFDialog(OneTFDialogType.AddCategory)
                    focusManager.clearFocus()
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
    search: String,
    onItemClick: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onClearSearchClick: () -> Unit
) {
    Content(
        baseState = baseState,
        onSuccess = { state ->
            state?.categoryList?.let { list ->
                Column {
                    SearchBar(
                        modifier = Modifier,
                        value = search,
                        onValueChange = onSearchTextChange,
                        onClearValueClick = onClearSearchClick
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = listState
                    ) {
                        itemsIndexed(
                            items = list.filter { it.name.contains(search) }
                        ) { _: Int, item: CategoryRes ->
                            CategoryItem(
                                item = item,
                                onClick = { onItemClick(item.name) },
                                painter = painterResource(id = R.drawable.ic_conver)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun SearchBar(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClearValueClick: () -> Unit
) {
    BasicTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        enabled = true,
        singleLine = true,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp
        ),
        cursorBrush = Brush.horizontalGradient(
            listOf(
                MaterialTheme.colors.secondary,
                MaterialTheme.colors.secondaryVariant
            )
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            if (value.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.search),
                    style = TextStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp
                    ),
                    color = Color.LightGray
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                it()

                if (value.isNotBlank()) {
                    CloseIcon(
                        modifier = Modifier.size(22.dp),
                        onClick = onClearValueClick
                    )
                }
            }
        }
    }
}
