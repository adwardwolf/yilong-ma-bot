/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.conversations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.wo1f.chatapp.R
import com.wo1f.chatapp.data.model.conversation.ConversationRes
import com.wo1f.chatapp.ui.base.Content
import com.wo1f.chatapp.ui.base.HandleErrorDialog
import com.wo1f.chatapp.ui.base.HandleLifeCycle
import com.wo1f.chatapp.ui.base.HandleOneTFDialog
import com.wo1f.chatapp.ui.base.HandleTwoActionDialog
import com.wo1f.chatapp.ui.category.CATEGORY_ALL
import com.wo1f.chatapp.ui.item.CategoryChip
import com.wo1f.chatapp.ui.item.ConverItem
import com.wo1f.chatapp.ui.model.ConverAction
import com.wo1f.chatapp.ui.model.OneTFDialogType
import com.wo1f.chatapp.ui.state.UiState
import com.wo1f.chatapp.ui.utils.AddFAB
import com.wo1f.chatapp.ui.utils.BgImageScaffold
import com.wo1f.chatapp.ui.utils.BoxMaxSizeCenter
import com.wo1f.chatapp.ui.utils.BoxMaxWidthEnd
import com.wo1f.chatapp.ui.utils.CloseIcon
import com.wo1f.chatapp.ui.utils.CustomButton
import com.wo1f.chatapp.ui.utils.CustomOutlineTextField
import com.wo1f.chatapp.ui.utils.TopAppBarIconStartAndEnd
import com.wo1f.chatapp.ui.utils.W400xh5Text
import com.wo1f.chatapp.ui.utils.W500xh4Text
import com.wo1f.chatapp.ui.utils.W600xh3Text
import kotlinx.coroutines.delay

@Composable
fun ConverScreen(goBack: () -> Unit) {

    val viewModel = hiltViewModel<ConverViewModel>()
    val name by remember { viewModel.name }.collectAsState()
    val category by remember { viewModel.category }.collectAsState()
    val baseState by remember { viewModel.baseState }.collectAsState()
    val question by remember { viewModel.question }.collectAsState()
    val answer by remember { viewModel.answer }.collectAsState()
    val actionState by remember { viewModel.actionState }.collectAsState()
    val showAddConverAD by remember { viewModel.showAddConverDialog }.collectAsState()
    val showUpdateConverAD by remember { viewModel.showUpdateConverDialog }.collectAsState()
    val errorDialogState by remember { viewModel.errorDialogState }.collectAsState()
    val twoActionDialogState by remember { viewModel.twoActionDialogState }.collectAsState()
    val clickedItem by remember { viewModel.clickedItem }.collectAsState()
    val categoryName by remember { viewModel.categoryName }.collectAsState()
    val oneTFDialogState by remember { viewModel.oneTFDialogState }.collectAsState()
    val listState by remember { viewModel.listState }.collectAsState()

    BgImageScaffold(
        topBar = {
            ConverTopBar(
                name = name,
                contentEndEnable = category != null,
                goBack = goBack,
                onEditClick = {
                    category?.let {
                        viewModel.showOneTFDialog(OneTFDialogType.UpdateCategory)
                        viewModel.onCategoryChange(it.name)
                    }
                },
                onDeleteClick = {
                    viewModel.showOneTFDialog(OneTFDialogType.DeleteCategory(name))
                }
            )
        },
        content = {
            ConverContent(
                baseState = baseState,
                listState = listState,
                onEditClick = viewModel::onEditCategoryClick,
                onDeleteClick = viewModel::onDeleteCategoryClick
            )
        },
        floatingActionButton = {
            if (name != "all") {
                AddFAB(onClick = {
                    viewModel.clearText()
                    viewModel.setAddConverAD(true)
                })
            }
        }
    )

    if (showAddConverAD) {
        AddConversationDialog(
            title = stringResource(id = R.string.add_new_conversation),
            buttonText = stringResource(id = R.string.add),
            question = question,
            answer = answer,
            category = name,
            isLoading = actionState.isLoading,
            onQuestionChange = viewModel::onQuestionChange,
            onAnswerChange = viewModel::onAnswerChange,
            onCloseClick = {
                viewModel.setAddConverAD(false)
                viewModel.clearText()
            },
            onButtonClick = {
                viewModel.add(question, answer)
            }
        )
    }

    if (showUpdateConverAD) {
        AddConversationDialog(
            title = stringResource(id = R.string.update_conversation),
            buttonText = stringResource(id = R.string.update),
            question = question,
            answer = answer,
            category = clickedItem!!.category,
            isLoading = actionState.isLoading,
            onQuestionChange = viewModel::onQuestionChange,
            onAnswerChange = viewModel::onAnswerChange,
            onCloseClick = {
                viewModel.setUpdateConverAD(false)
                viewModel.clearText()
            },
            onButtonClick = {
                viewModel.update(question, answer)
            }
        )
    }

    LaunchedEffect(actionState) {
        if (actionState.isSuccessful && actionState.type != null) {
            viewModel.setAddConverAD(false)
            viewModel.setUpdateConverAD(false)
            viewModel.hideTwoActionDialog()
            viewModel.hideOneTFDialog()

            if (actionState.type is ConverAction.DeleteCategory) {
                delay(300)
                goBack()
            }
        }
    }

    HandleLifeCycle(onStart = { viewModel.load() })

    HandleOneTFDialog(
        text = categoryName,
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

    HandleTwoActionDialog(
        dialogState = twoActionDialogState,
        hideDialog = viewModel::hideTwoActionDialog,
        onPosClick = viewModel::onTwoDialogActionClick
    )
}

@Composable
private fun ConverTopBar(
    name: String,
    contentEndEnable: Boolean,
    goBack: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    TopAppBarIconStartAndEnd(
        label = name,
        startContent = {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = goBack,
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        endContent = {
            if (name != CATEGORY_ALL) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        modifier = Modifier.size(36.dp),
                        enabled = contentEndEnable,
                        onClick = onDeleteClick,
                        content = {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = null
                            )
                        }
                    )
                    IconButton(
                        modifier = Modifier.size(36.dp),
                        enabled = contentEndEnable,
                        onClick = onEditClick,
                        content = {
                            Image(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun ConverContent(
    baseState: UiState<ConverState>,
    listState: LazyListState,
    onEditClick: (ConversationRes) -> Unit,
    onDeleteClick: (ConversationRes) -> Unit
) {
    Content(
        baseState = baseState,
        onSuccess = { state ->
            state?.getConverRes?.conversations?.let { list ->
                if (list.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        state = listState,
                    ) {
                        itemsIndexed(list) { _: Int, item: ConversationRes ->
                            ConverItem(
                                item = item,
                                onDeleteClick = { onDeleteClick(item) },
                                onEditClick = { onEditClick(item) }
                            )
                        }
                    }
                } else {
                    BoxMaxSizeCenter {
                        W500xh4Text(text = stringResource(id = R.string.no_conversation))
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AddConversationDialog(
    title: String,
    buttonText: String,
    question: String,
    answer: String,
    category: String,
    isLoading: Boolean,
    onQuestionChange: (String) -> Unit,
    onAnswerChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onButtonClick: () -> Unit
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {}
    ) {
        Box(modifier = Modifier.padding(horizontal = 30.dp)) {
            Box(modifier = Modifier.padding(8.dp)) {
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
                        W600xh3Text(text = title)

                        CustomOutlineTextField(
                            value = question,
                            label = stringResource(id = R.string.question),
                            showKeyboard = true,
                            maxLines = 1,
                            onValueChange = onQuestionChange
                        )
                        CustomOutlineTextField(
                            value = answer,
                            label = stringResource(id = R.string.answer),
                            maxLines = 5,
                            onValueChange = onAnswerChange
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            W400xh5Text(
                                text = stringResource(id = R.string.category_colon),
                                color = Color.LightGray
                            )
                            CategoryChip(text = category)
                        }

                        CustomButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = buttonText,
                            enabled = question.isNotBlank() && answer.isNotBlank(),
                            isLoading = isLoading,
                            onClick = onButtonClick
                        )
                    }
                }
            }

            BoxMaxWidthEnd {
                CloseIcon(
                    modifier = Modifier.size(26.dp),
                    onClick = onCloseClick
                )
            }
        }
    }
}
