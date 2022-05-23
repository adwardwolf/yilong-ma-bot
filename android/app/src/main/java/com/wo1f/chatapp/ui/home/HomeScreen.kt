package com.wo1f.chatapp.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.wo1f.chatapp.data.repo.DataResource
import com.wo1f.chatapp.ui.utils.CustomButton
import com.wo1f.chatapp.ui.utils.CustomOutlineTextField
import com.wo1f.chatapp.ui.utils.UserTemplate
import com.wo1f.chatapp.ui.utils.W600xh3Text

@Composable
fun HomeScreen(goToChat: () -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val question by remember { viewModel.question }.collectAsState()
    val answer by remember { viewModel.answer }.collectAsState()
    val addResponse by remember { viewModel.addResponse }.collectAsState()
    val showAddConverAD by remember { viewModel.showAddConverAD }.collectAsState()

    if (showAddConverAD) {
        AddConversationDialog(
            question = question,
            answer = answer,
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
            HomeTopBar()
        },
        content = {
            HomeContent(goToChat = goToChat)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.setAddConverDB(true)
                },
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
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
            W600xh3Text(text = "Home")
        }
    }
}

@Composable
private fun HomeContent(goToChat: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        UserTemplate(name = "Yilong Ma", onClick = goToChat)
    }
}

@Composable
fun AddConversationDialog(
    question: String,
    answer: String,
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
            }
        }
    }
}
