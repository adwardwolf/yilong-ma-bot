/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wo1f.yilongma.R
import com.wo1f.yilongma.ui.theme.ChatAppTheme
import com.wo1f.yilongma.ui.utils.W600xh3Text

/**
 * An item which has a text and an leading image
 */
@Composable
fun BasicListItem(
    text: String,
    painter: Painter,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colors.onPrimary
                ) {
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(6.dp),
                        painter = painter,
                        contentDescription = null
                    )
                }
            }
            W600xh3Text(text = "@$text", maxLines = 1)
        }
    }
}

@Preview
@Composable
private fun BasicListItemPreview() {
    ChatAppTheme(darkTheme = true) {
        BasicListItem(
            text = "Bot",
            painter = painterResource(id = R.drawable.ic_bot_profile),
            onClick = {}
        )
    }
}
