package com.wo1f.chatapp.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wo1f.chatapp.ui.chat.ChatScreen
import com.wo1f.chatapp.ui.home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(navController, AppScreen.Home.route) {

        composable(AppScreen.Home.route) {
            HomeScreen(
                goToChat = { navController.navigate(AppScreen.Chat.route) }
            )
        }

        composable(AppScreen.Chat.route) {
            ChatScreen(
                goBack = { navController.popBackStack() }
            )
        }
    }
}