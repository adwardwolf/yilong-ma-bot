package com.wo1f.chatapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 20.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 18.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 16.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
    ),
    button = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        color = Color.White
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    )
)