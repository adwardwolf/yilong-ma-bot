package com.wo1f.chatapp.ui.utils

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    shouldBeEnabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillComponentsWidth(),
        enabled = shouldBeEnabled,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.primary
        )
    ) {
        Text(
            text = if (isLoading) "Please Wait..." else text,
            style = MaterialTheme.typography.h3
        )
    }
}
