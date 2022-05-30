/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.wo1f.chatapp.R

@Composable
fun CircleLoadingPI(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
    color: Color = MaterialTheme.colors.secondary
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        strokeWidth = strokeWidth
    )
}

/**
 * A Scaffold with custom background image
 */
@Composable
fun BgImageScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    backgroundColor: Color = Color.Transparent,
    content: @Composable (PaddingValues) -> Unit
) {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.chat_bg_star),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            backgroundColor = backgroundColor,
            content = content
        )
    }
}
