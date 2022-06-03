/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.ui.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wo1f.yilongma.R
import com.wo1f.yilongma.data.model.conversation.ConversationRes
import com.wo1f.yilongma.ui.theme.ChatAppTheme
import com.wo1f.yilongma.ui.theme.Orange
import com.wo1f.yilongma.ui.utils.W400xOverlineText
import com.wo1f.yilongma.ui.utils.W400xh5Text
import com.wo1f.yilongma.ui.utils.W400xh6Text
import com.wo1f.yilongma.ui.utils.W500xh5Text
import java.time.LocalDate

@Composable
fun ConverItem(
    item: ConversationRes,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, Color.White),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            W500xh5Text(
                text = "$humanEmoji : ${item.question}",
                color = MaterialTheme.colors.secondaryVariant
            )
            W400xh5Text(
                text = "$robotEmoji : ${item.answer}",
                color = MaterialTheme.colors.onBackground
            )
            CategoryChip(text = item.category)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                W400xOverlineText(
                    text = "Created at: ${item.createdAt}",
                    color = Color.LightGray
                )
                Row {
                    IconButton(
                        modifier = Modifier.size(36.dp),
                        onClick = onDeleteClick,
                        content = {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = null
                            )
                        }
                    )
                    IconButton(
                        modifier = Modifier.size(36.dp),
                        onClick = onEditClick,
                        content = {
                            Image(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}

private val robotEmoji = getEmoji(0x1F916)
private val humanEmoji = getEmoji(0x1F914)

private fun getEmoji(unicode: Int) = String(Character.toChars(unicode))

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    text: String
) {
    Surface(
        modifier = modifier
            .wrapContentWidth()
            .height(22.dp),
        color = Orange,
        shape = CircleShape
    ) {
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            W400xh6Text(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp),
                text = text,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun ConverItemPreview() {
    ChatAppTheme(darkTheme = true) {
        ConverItem(item = conversationRes, onDeleteClick = {}, onEditClick = {})
    }
}

private val conversationRes = ConversationRes(
    id = "1",
    question = "How old are you?",
    answer = "I'm twenty",
    category = "greetings",
    createdAt = LocalDate.now().toString()
)
