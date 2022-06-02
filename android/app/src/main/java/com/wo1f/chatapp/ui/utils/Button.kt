/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wo1f.chatapp.ui.theme.ChatAppTheme

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    isLoading: Boolean,
    buttonColor: Color = MaterialTheme.colors.secondary,
    textColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillButtonWidth()
            .height(50.dp),
        enabled = enabled,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            contentColor = textColor
        )
    ) {
        if (isLoading) {
            CircleLoadingPI(
                modifier = Modifier.size(22.dp),
                strokeWidth = 3.dp,
                color = textColor
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Preview
@Composable
private fun CustomButtonPreview() {
    ChatAppTheme(darkTheme = true) {
        CustomButton(
            text = "Send",
            enabled = true,
            isLoading = false,
            onClick = {}
        )
    }
}

/**
 * A [FloatingActionButton] with an add icon
 */
@Composable
fun AddFAB(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
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

@Preview
@Composable
private fun AddFABPreview() {
    ChatAppTheme(darkTheme = true) {
        AddFAB(onClick = {})
    }
}
