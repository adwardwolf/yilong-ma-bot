package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.VisualTransformation
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomOutlineTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    shouldBeEnabled: Boolean = true,
    showKeyboard: Boolean? = false,
    onValueChange: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = value,
        textStyle = MaterialTheme.typography.body2,
        enabled = shouldBeEnabled,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(label) },
        maxLines = 1,
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        shape = MaterialTheme.shapes.large,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            focusedBorderColor = MaterialTheme.colors.secondary,
            textColor = MaterialTheme.colors.onBackground,
            focusedLabelColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary
        )
    )

    LaunchedEffect(focusRequester) {
        if (showKeyboard == true) {
            focusRequester.requestFocus()
            delay(100)
            keyboard?.show()
        }
    }
}
