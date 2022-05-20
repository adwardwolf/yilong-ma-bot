package com.wo1f.chatapp.app

sealed class AppScreen(val route: String) {

    object Home : AppScreen("/home")

    object Chat : AppScreen("/chat")
}