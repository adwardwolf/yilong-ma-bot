/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wo1f.chatapp.ui.model.OneTFDialogType

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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OneTextFieldDialog(
    text: String,
    isLoading: Boolean,
    type: OneTFDialogType,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onButtonClick: (String) -> Unit
) {
    val buttonEnabled = when (type) {
        is OneTFDialogType.AddCategory -> text.isNotBlank()
        is OneTFDialogType.UpdateCategory -> text.isNotBlank()
        is OneTFDialogType.DeleteCategory -> text == type.name
    }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {}
    ) {
        Box(modifier = Modifier.padding(horizontal = 30.dp)) {
            Box(modifier = Modifier.padding(8.dp)) {
                Surface(
                    color = MaterialTheme.colors.background,
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            W600xh3Text(text = stringResource(id = type.title))
                            if (type.message is String) {
                                W400xh6Text(text = type.message, color = Color.LightGray)
                            }
                        }

                        CustomOutlineTextField(
                            value = text,
                            label = stringResource(id = type.hint),
                            showKeyboard = true,
                            maxLines = 1,
                            onValueChange = onTextChange
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        CustomButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = type.textButton),
                            enabled = buttonEnabled,
                            isLoading = isLoading,
                            buttonColor = colorResource(id = type.buttonColor),
                            textColor = colorResource(id = type.textColor),
                            onClick = { onButtonClick(text) }
                        )
                    }
                }
            }

            BoxMaxWidthEnd {
                CloseIcon(
                    modifier = Modifier.size(26.dp),
                    onClick = onCloseClick
                )
            }
        }
    }
}
