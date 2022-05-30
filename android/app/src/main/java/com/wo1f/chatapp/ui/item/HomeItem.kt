/**
 * @author Adwardwo1f
 * @created May 30, 2022
 */

package com.wo1f.chatapp.ui.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wo1f.chatapp.ui.utils.W500xh3Text

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeItem(
    text: String,
    painter: Painter,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = Color.Transparent,
        border = BorderStroke(1.dp, Color.White),
        onClick = onClick
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = Color.Transparent
        ) {
            Column {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    painter = painter,
                    contentDescription = null
                )
                W500xh3Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = text,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}