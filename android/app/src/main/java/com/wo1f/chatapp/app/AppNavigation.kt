package com.wo1f.chatapp.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wo1f.chatapp.ui.category.CategoryScreen
import com.wo1f.chatapp.ui.chat.ChatScreen
import com.wo1f.chatapp.ui.conversations.ConverScreen
import com.wo1f.chatapp.ui.home.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(navController, AppScreen.Home.route) {

        composable(AppScreen.Home.route) {
            HomeScreen(
                goToChat = navController::navigateToChat,
                goToCategory = navController::navigateToCategory
            )
        }

        composable(AppScreen.Chat.route) {
            ChatScreen(
                goBack = navController::popBackStack
            )
        }

        composable(AppScreen.Conversation.route) {
            ConverScreen(
                goBack = navController::popBackStack
            )
        }

        composable(AppScreen.Category.route) {
            CategoryScreen(
                goBack = navController::popBackStack,
                goToConver = navController::navigateToConversation,
            )
        }
    }
}

private fun NavHostController.navigateToConversation(name: String) {
    this.navigate(AppScreen.Conversation.route.replace("{name}", name))
}

private fun NavHostController.navigateToChat() {
    this.navigate(AppScreen.Chat.route)
}

private fun NavHostController.navigateToCategory() {
    this.navigate(AppScreen.Category.route)
}
