package com.wo1f.chatapp.ui.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun W800xh2Text(
    text: String,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h2,
        fontWeight = FontWeight.W800,
        textAlign = textAlign,
        color = MaterialTheme.colors.onSurface,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun W600xh3Text(
    text: String,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h3,
        fontWeight = FontWeight.W600,
        textAlign = textAlign,
        color = MaterialTheme.colors.onSurface,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun W500xh3Text(
    text: String,
    textAlign: TextAlign? = null,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h3,
        fontWeight = FontWeight.W500,
        textAlign = textAlign,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines
    )
}

@Composable
fun W500xh4Text(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign? = null,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.h4,
        fontWeight = FontWeight.W500,
        textAlign = textAlign,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines
    )
}

@Composable
fun W500xh5Text(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign? = null,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.W500,
        textAlign = textAlign,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines
    )
}

@Composable
fun W400xh6Text(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign? = null,
    color: Color = MaterialTheme.colors.onSurface,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.W400,
        textAlign = textAlign,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines
    )
}

@Composable
fun W400xh5Text(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.onSurface,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.W400,
        textAlign = textAlign,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines
    )
}

@Composable
fun W600xh6Text(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.W600,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines
    )
}

@Composable
fun W500xh6Text(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.W500,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines
    )
}

@Composable
fun W400xOverlineText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.overline,
        fontWeight = FontWeight.W400,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines
    )
}

@Composable
fun Normalxh6Text(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.overline,
        fontWeight = FontWeight.Normal,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines
    )
}