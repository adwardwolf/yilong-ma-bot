package com.wo1f.chatapp.ui.utils

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wo1f.chatapp.R
import com.wo1f.chatapp.data.Chat
import com.wo1f.chatapp.utils.toAppTime

@Composable
fun ChatItem(chat: Chat) {

    if (chat.type == Chat.Type.SENDER) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Surface(
                color = Color.Red,
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    W400xh5Text(text = chat.text, color = Color.White)
                    W400xOverlineText(text = chat.date.toAppTime(), color = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = CircleShape
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.ic_human_profile),
                    contentDescription = null
                )
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(
                shape = CircleShape
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.ic_bot_profile),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                color = Color.Blue,
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(8.dp),) {
                    W400xh5Text(text = chat.text, color = Color.White)
                    W400xOverlineText(text = chat.date.toAppTime(), color = Color.LightGray)
                }
            }
        }
    }
}