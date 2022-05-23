package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalConfiguration

fun Modifier.fillComponentsWidth(): Modifier = composed {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    when {
        screenWidthDp < 600 -> this.fillMaxWidth(0.7f)
        screenWidthDp < 840 -> this.fillMaxWidth(0.6f)
        else -> this.fillMaxWidth(0.5f)
    }
}
