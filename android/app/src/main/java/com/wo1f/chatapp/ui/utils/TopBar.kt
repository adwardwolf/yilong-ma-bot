/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomTopAppBarIconStart(
    imageVector: ImageVector,
    onClick: () -> Unit,
    label: String,
    contentDescription: String? = null,
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.onBackground,
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
                .padding(start = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = onClick,
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = imageVector,
                    contentDescription = contentDescription
                )
            }
            W600xh3Text(
                text = label,
                maxLines = 1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun CustomTopAppBarIconStartAndEnd(
    label: String,
    imageVectorStart: ImageVector,
    imageVectorEnd: ImageVector?,
    iconEndEnabled: Boolean = true,
    iconDescriptionStart: String? = null,
    iconDescriptionEnd: String? = null,
    onClickIconStart: () -> Unit,
    onClickIconEnd: () -> Unit,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.wrapContentSize(Alignment.TopStart),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onClickIconStart,
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = imageVectorStart,
                        contentDescription = iconDescriptionStart
                    )
                }
                W600xh3Text(
                    text = label,
                    maxLines = 1,
                    color = MaterialTheme.colors.onSurface
                )
            }
            imageVectorEnd?.let { imageVectorEnd ->
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onClickIconEnd,
                    enabled = iconEndEnabled,
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = imageVectorEnd,
                        contentDescription = iconDescriptionEnd
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTopAppBarIconStartAndEnd(
    label: String,
    startContent: @Composable () -> Unit,
    endContent: @Composable () -> Unit
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.onBackground,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.wrapContentSize(Alignment.TopStart),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                startContent()
                W600xh3Text(
                    text = label,
                    maxLines = 1,
                    color = MaterialTheme.colors.onSurface
                )
            }
            endContent()
        }
    }
}
