/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wo1f.chatapp.R
import com.wo1f.chatapp.data.model.chat.Chat
import com.wo1f.chatapp.ui.utils.W400xOverlineText
import com.wo1f.chatapp.ui.utils.W400xh5Text
import com.wo1f.chatapp.ui.utils.W600xh5Text

@Composable
fun ChatItem(chat: Chat) {
    when (chat.type) {
        Chat.Type.SENDER -> {
            ChatSenderItem(chat)
        }
        Chat.Type.RECEIVER -> {
            ChatReceiverItem(chat)
        }
    }
}

@Composable
private fun ChatSenderItem(chat: Chat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            color = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                W600xh5Text(
                    text = chat.name,
                    color = MaterialTheme.colors.background,
                    maxLines = 1
                )
                W400xh5Text(
                    text = chat.text!!,
                    color = MaterialTheme.colors.background
                )
                W400xOverlineText(
                    text = chat.date,
                    color = MaterialTheme.colors.primaryVariant,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colors.onPrimary
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_human_profile),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ChatReceiverItem(chat: Chat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colors.onPrimary
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_bot_profile),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
            color = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                W600xh5Text(
                    text = chat.name,
                    color = MaterialTheme.colors.background,
                    maxLines = 1
                )
                W400xh5Text(
                    text = chat.text!!,
                    color = MaterialTheme.colors.background
                )
                W400xOverlineText(
                    text = chat.date,
                    color = MaterialTheme.colors.primaryVariant,
                    maxLines = 1
                )
            }
        }
    }
}

// @Composable
// private fun ChatJoinedItem(chat: Chat) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 8.dp, vertical = 4.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Surface(
//            modifier = Modifier.wrapContentWidth(),
//            color = MaterialTheme.colors.secondary,
//            shape = CircleShape
//        ) {
//            Box(
//                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
//            ) {
//                W400xh6Text(
//                    text = "${chat.name} joined chat",
//                    color = MaterialTheme.colors.primary
//                )
//            }
//        }
//    }
// }
