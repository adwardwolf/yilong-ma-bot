package com.wo1f.chatapp.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wo1f.chatapp.data.model.conversation.ConversationRes
import com.wo1f.chatapp.ui.theme.Orange
import com.wo1f.chatapp.ui.utils.W400xOverlineText
import com.wo1f.chatapp.ui.utils.W400xh5Text
import com.wo1f.chatapp.ui.utils.W400xh6Text
import com.wo1f.chatapp.ui.utils.W500xh4Text

@Composable
fun ConverItem(item: ConversationRes) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            W500xh4Text(
                text = item.question,
                color = MaterialTheme.colors.secondaryVariant
            )
            W400xh5Text(
                text = item.answer,
                color = MaterialTheme.colors.onBackground
            )
            CategoryChip(text = item.category)
            W400xOverlineText(
                text = "Created at: ${item.createdAt}",
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    text: String
) {
    Surface(
        modifier = modifier
            .wrapContentWidth()
            .height(20.dp),
        color = Orange,
        shape = CircleShape
    ) {
        W400xh6Text(
            modifier = Modifier.padding(horizontal = 6.dp),
            text = text,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
    }
}
