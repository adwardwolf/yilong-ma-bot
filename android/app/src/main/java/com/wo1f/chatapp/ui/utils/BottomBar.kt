package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalComposeUiApi
@Composable
fun ChatBottomBar(
    text: String,
    shouldBeEnabled: Boolean,
    onTextChange: (String) -> Unit,
    onSendClick: (String) -> Unit
) {
    val focusRequester = FocusRequester()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .imePadding(),
        color = MaterialTheme.colors.secondary,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp, horizontal = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BasicTextField(
                    modifier = Modifier
                        .weight(6f)
                        .wrapContentHeight()
                        .focusRequester(focusRequester),
                    value = text,
                    enabled = true,
                    onValueChange = onTextChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Default,
                    ),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp
                    ),
                    cursorBrush = Brush.horizontalGradient(
                        listOf(MaterialTheme.colors.primary, MaterialTheme.colors.secondaryVariant)
                    ),
                ) {
                    Surface(
                        modifier = Modifier.wrapContentSize(),
                        shape = MaterialTheme.shapes.large,
                        color = MaterialTheme.colors.background
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                        ) {
                            if (text.isEmpty()) {
                                Text(
                                    text = "Write a message",
                                    style = MaterialTheme.typography.subtitle2,
                                    color = Color.LightGray
                                )
                            }
                            it()
                        }
                    }
                }
                TextButton(
                    modifier = Modifier
                        .height(28.dp)
                        .weight(1f),
                    shape = CircleShape,
                    enabled = shouldBeEnabled,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary,
                        disabledBackgroundColor = MaterialTheme.colors.secondaryVariant,
                        disabledContentColor = MaterialTheme.colors.onPrimary
                    ),
                    contentPadding = PaddingValues(vertical = 2.dp, horizontal = 0.dp),
                    onClick = { onSendClick(text) },
                ) {
                    Text(
                        text = "Send",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.W600,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}