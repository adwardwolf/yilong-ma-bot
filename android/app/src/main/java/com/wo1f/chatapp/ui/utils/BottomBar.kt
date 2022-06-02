/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wo1f.chatapp.R
import com.wo1f.chatapp.ui.theme.ChatAppTheme

@Composable
fun ChatBottomBar(
    text: String,
    enabled: Boolean,
    onTextChange: (String) -> Unit,
    onSendClick: (String) -> Unit
) {
    val focusRequester = FocusRequester()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .imePadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    start = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 8.dp
                ),
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
                        .focusRequester(focusRequester)
                        .testTag(stringResource(id = R.string.write_message)),
                    value = text,
                    enabled = true,
                    maxLines = 3,
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
                        listOf(
                            MaterialTheme.colors.secondary,
                            MaterialTheme.colors.secondaryVariant
                        )
                    ),
                ) {
                    Surface(
                        modifier = Modifier.wrapContentSize(),
                        shape = MaterialTheme.shapes.large,
                        color = MaterialTheme.colors.surface
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                        ) {
                            if (text.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.write_message),
                                    style = MaterialTheme.typography.subtitle2,
                                    color = Color.LightGray
                                )
                            }
                            it()
                        }
                    }
                }
                IconButton(
                    onClick = { onSendClick(text) },
                    enabled = enabled,
                ) {
                    Icon(
                        modifier = Modifier.size(22.dp),
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = stringResource(id = R.string.send_chat),
                        tint = if (enabled) {
                            MaterialTheme.colors.secondary
                        } else {
                            Color.LightGray
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChatBottomBarPreview() {
    ChatAppTheme(darkTheme = true) {
        ChatBottomBar(
            text = "",
            enabled = true,
            onTextChange = {},
            onSendClick = {}
        )
    }
}
