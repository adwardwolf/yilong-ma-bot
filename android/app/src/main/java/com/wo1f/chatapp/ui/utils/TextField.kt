/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomOutlineTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    maxLines: Int,
    shouldBeEnabled: Boolean = true,
    showKeyboard: Boolean? = false,
    onValueChange: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = textFieldValue,
        textStyle = MaterialTheme.typography.body2,
        enabled = shouldBeEnabled,
        onValueChange = {
            textFieldValue = it
            onValueChange(it.text)
        },
        label = { Text(label) },
        maxLines = maxLines,
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

            // Move cursor to the end of text
            textFieldValue = textFieldValue.copy(selection = TextRange(value.length))
        }
    }
}
