package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun OkDialog(
    text: String,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Surface(
            color = MaterialTheme.colors.background,
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                W500xh4Text(
                    text = text,
                    color = MaterialTheme.colors.onSurface
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
                        onClick = onDismissRequest,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.background
                        ),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        Text(
                            text = "Ok",
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TwoActionDialog(
    text: String,
    actionText: String,
    onCancelClick: () -> Unit,
    onActionClick: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Surface(
            color = MaterialTheme.colors.background,
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                W500xh4Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    text = text,
                    color = MaterialTheme.colors.onSurface
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Button(
                            onClick = onCancelClick,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.background
                            ),
                            elevation = ButtonDefaults.elevation(0.dp)
                        ) {
                            Text(
                                text = "Cancel",
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        Button(
                            onClick = onActionClick,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.background
                            ),
                            elevation = ButtonDefaults.elevation(0.dp)
                        ) {
                            Text(
                                text = actionText,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}
