/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wo1f.chatapp.R
import com.wo1f.chatapp.ui.theme.ChatAppTheme

/**
 * A close icon with round background shape
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CloseIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.testTag(stringResource(id = R.string.close)),
        shape = CircleShape,
        color = MaterialTheme.colors.secondary,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
    }
}

@Preview
@Composable
private fun CloseIconPreview() {
    ChatAppTheme(darkTheme = true) {
        CloseIcon(onClick = {})
    }
}
