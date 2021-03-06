/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Box that align the [content] to center
 */
@Composable
fun BoxMaxSizeCenter(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

/**
 * Box that align the [content] to start
 */
@Composable
fun BoxMaxWidthEnd(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        content()
    }
}
