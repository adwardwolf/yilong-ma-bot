package com.wo1f.chatapp.ui.conversations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.wo1f.chatapp.R
import com.wo1f.chatapp.data.model.conversation.ConversationRes
import com.wo1f.chatapp.ui.BaseUiState
import com.wo1f.chatapp.ui.item.CategoryChip
import com.wo1f.chatapp.ui.item.ConverItem
import com.wo1f.chatapp.ui.utils.AddFAB
import com.wo1f.chatapp.ui.utils.BoxMaxSizeCenter
import com.wo1f.chatapp.ui.utils.Content
import com.wo1f.chatapp.ui.utils.CustomButton
import com.wo1f.chatapp.ui.utils.CustomOutlineTextField
import com.wo1f.chatapp.ui.utils.CustomTopAppBarIconStart
import com.wo1f.chatapp.ui.utils.HandleErrorDialog
import com.wo1f.chatapp.ui.utils.HandleTwoActionDialog
import com.wo1f.chatapp.ui.utils.W400xh5Text
import com.wo1f.chatapp.ui.utils.W500xh4Text
import com.wo1f.chatapp.ui.utils.W600xh3Text

@Composable
fun ConverScreen(goBack: () -> Unit) {

    val viewModel = hiltViewModel<ConverViewModel>()
    val name = remember { viewModel.name }
    val baseState by remember { viewModel.baseState }.collectAsState()
    val question by remember { viewModel.question }.collectAsState()
    val answer by remember { viewModel.answer }.collectAsState()
    val actionState by remember { viewModel.actionState }.collectAsState()
    val showAddConverAD by remember { viewModel.showAddConverAD }.collectAsState()
    val showUpdateConverAD by remember { viewModel.showUpdateConverAD }.collectAsState()
    val errorDialogState by remember { viewModel.errorDialogState }.collectAsState()
    val twoActionDialogState by remember { viewModel.twoActionDialogState }.collectAsState()
    val clickedItem by remember { viewModel.clickedItem }.collectAsState()

    Scaffold(
        topBar = {
            ConverTopBar(
                name = name,
                goBack = goBack
            )
        },
        content = {
            ConverContent(
                baseState = baseState,
                onEditClick = viewModel::onEditClick,
                onDeleteClick = viewModel::onDeleteClick
            )
        },
        floatingActionButton = {
            if (name != "all") {
                AddFAB(onClick = { viewModel.setAddConverAD(true) })
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
            onDismissRequest = {
                viewModel.setAddConverAD(false)
                viewModel.clearText()
            },
            onClick = {
                viewModel.addConversation(question, answer)
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
            onDismissRequest = {
                viewModel.setUpdateConverAD(false)
                viewModel.clearText()
            },
            onClick = {
                viewModel.updateConversation(question, answer)
            }
        )
    }

    LaunchedEffect(actionState) {
        if (actionState.isSuccessful) {
            viewModel.setAddConverAD(false)
            viewModel.setUpdateConverAD(false)
            viewModel.hideTwoActionDialog()
            viewModel.clearText()
        }
    }

    HandleErrorDialog(
        dialogState = errorDialogState,
        hideDialog = viewModel::hideDialog
    )

    HandleTwoActionDialog(
        dialogState = twoActionDialogState,
        hideDialog = viewModel::hideTwoActionDialog,
        onActionClick = viewModel::onTwoDialogActionClick
    )
}

@Composable
private fun ConverTopBar(
    name: String,
    goBack: () -> Unit
) {
    CustomTopAppBarIconStart(
        imageVector = Icons.Default.ArrowBack,
        onClick = goBack,
        label = name
    )
}

@Composable
private fun ConverContent(
    baseState: BaseUiState<ConverState>,
    onEditClick: (ConversationRes) -> Unit,
    onDeleteClick: (ConversationRes) -> Unit
) {
    Content(
        baseState = baseState,
        onSuccess = { state ->
            state?.converList?.let { list ->
                if (list.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
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
                W600xh3Text(text = title)

                CustomOutlineTextField(
                    value = question,
                    label = stringResource(id = R.string.question),
                    showKeyboard = true,
                    onValueChange = onQuestionChange
                )
                CustomOutlineTextField(
                    value = answer,
                    label = stringResource(id = R.string.answer),
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
                    shouldBeEnabled = question.isNotBlank() && answer.isNotBlank(),
                    isLoading = isLoading,
                    onClick = onClick
                )
            }
        }
    }
}
