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
                goToChat = { navController.navigate(AppScreen.Chat.route) },
                goToCategory = { navController.navigate(AppScreen.Category.route) }
            )
        }

        composable(AppScreen.Chat.route) {
            ChatScreen(
                goBack = { navController.popBackStack() }
            )
        }

        composable(AppScreen.Conversation.route) {
            ConverScreen(
                goBack = { navController.popBackStack() }
            )
        }

        composable(AppScreen.Category.route) {
            CategoryScreen(
                goBack = { navController.popBackStack() },
                goToConver = { name ->
                    navController.navigate(
                        AppScreen.Conversation.route.replace("{name}", name)
                    )
                },
            )
        }
    }
}
