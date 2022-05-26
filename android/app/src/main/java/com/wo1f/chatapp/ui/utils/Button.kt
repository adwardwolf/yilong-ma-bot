package com.wo1f.chatapp.ui.utils

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.wo1f.chatapp.R

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
            text = if (isLoading) {
                stringResource(id = R.string.please_wait)
            } else text,
            style = MaterialTheme.typography.h3
        )
    }
}

@Composable
fun AddFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        content = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
    )
}
