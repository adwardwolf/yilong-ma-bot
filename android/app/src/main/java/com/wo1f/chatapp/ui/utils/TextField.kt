package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomOutlineTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    shouldBeEnabled: Boolean = true,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
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
}
