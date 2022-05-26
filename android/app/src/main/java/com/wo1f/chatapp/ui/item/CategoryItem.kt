package com.wo1f.chatapp.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.ui.utils.W400xh5Text
import com.wo1f.chatapp.ui.utils.W600xh3Text

@Composable
fun CategoryItem(
    item: CategoryRes,
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
            Column {
                W600xh3Text(text = "@${item.name}", maxLines = 1)
                W400xh5Text(
                    text = "${item.count} Conversations",
                    color = Color.LightGray
                )
            }
        }
    }
}
