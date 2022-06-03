/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.app

sealed class AppScreen(val route: String) {

    object Home : AppScreen("/home")

    object Chat : AppScreen("/chat/{room}")

    object Conversation : AppScreen("/category/{name}/conversation")

    object Category : AppScreen("/category")
}
