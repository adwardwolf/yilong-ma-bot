/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalConfiguration

/**
 * Custom button width for difference screen size
 */
fun Modifier.fillButtonWidth(): Modifier = composed {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    when {
        screenWidthDp < 600 -> this.fillMaxWidth(0.7f)
        screenWidthDp < 840 -> this.fillMaxWidth(0.6f)
        else -> this.fillMaxWidth(0.5f)
    }
}
