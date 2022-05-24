package com.wo1f.chatapp.ui.conversations

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.model.conversation.ConversationRes
import com.wo1f.chatapp.ui.item.CategoryChip
import com.wo1f.chatapp.ui.item.ConverItem
import com.wo1f.chatapp.ui.utils.CustomButton
import com.wo1f.chatapp.ui.utils.CustomOutlineTextField
import com.wo1f.chatapp.ui.utils.CustomTopAppBarIconStart
import com.wo1f.chatapp.ui.utils.W400xh5Text
import com.wo1f.chatapp.ui.utils.W500xh4Text

@Composable
fun ConverScreen(goBack: () -> Unit) {

    val viewModel = hiltViewModel<ConverViewModel>()
    val name = remember { viewModel.name }
    val state by remember { viewModel.state }.collectAsState()
    val question by remember { viewModel.question }.collectAsState()
    val answer by remember { viewModel.answer }.collectAsState()
    val addResponse by remember { viewModel.addResponse }.collectAsState()
    val showAddConverAD by remember { viewModel.showAddConverAD }.collectAsState()

    if (showAddConverAD) {
        AddConversationDialog(
            question = question,
            answer = answer,
            category = "greetings",
            isLoading = addResponse is DataResource.Loading,
            onQuestionChange = viewModel::onQuestionChange,
            onAnswerChange = viewModel::onAnswerChange,
            onDismissRequest = { viewModel.setAddConverDB(false) },
            onClick = { viewModel.add(question, answer) }
        )
    }

    LaunchedEffect(addResponse) {
        Log.d("addResponse", addResponse.toString())
        if (addResponse is DataResource.Success) {
            viewModel.clearText()
            viewModel.setAddConverDB(false)
        }
    }

    Scaffold(
        topBar = {
            ConverTopBar(
                name = name,
                goBack = goBack
            )
        },
        content = {
            ConverContent(
                state = state
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.setAddConverDB(true)
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            )
        }
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
        label = "Conversations"
    )
}

@Composable
private fun ConverContent(state: ConverState) {
    if (state.isSuccessful && state.converList != null) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(state.converList) { _: Int, item: ConversationRes ->
                ConverItem(item)
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

@Composable
fun AddConversationDialog(
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CustomOutlineTextField(
                    value = question,
                    label = "Question",
                    onValueChange = onQuestionChange
                )
                CustomOutlineTextField(
                    value = answer,
                    label = "Answer",
                    onValueChange = onAnswerChange
                )

                Spacer(Modifier.height(12.dp))

                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Add Conversation",
                    shouldBeEnabled = question.isNotBlank() && answer.isNotBlank(),
                    isLoading = isLoading,
                    onClick = onClick
                )

                Row {
                    W400xh5Text(text = "Category:", color = Color.LightGray)
                    CategoryChip(text = category)
                }
            }
        }
    }
}
