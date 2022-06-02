/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.chatapp

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.wo1f.chatapp.app.AppNavigation
import com.wo1f.chatapp.ui.main.MainActivity
import com.wo1f.chatapp.ui.theme.ChatAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var home: String
    private lateinit var yilongMa: String
    private lateinit var conversations: String
    private lateinit var goBack: String

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ChatAppTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }

        home = composeTestRule.activity.getString(R.string.home)
        yilongMa = composeTestRule.activity.getString(R.string.yilong_ma)
        conversations = composeTestRule.activity.getString(R.string.conversations)
        goBack = composeTestRule.activity.getString(R.string.go_back)
    }

    @Test
    fun testDisplay() {
        composeTestRule.run {
            onNodeWithText(home).assertIsDisplayed()
            onNodeWithText(yilongMa).assertIsDisplayed()
            onNodeWithText(conversations).assertIsDisplayed()
        }
    }

    @Test
    fun testClickable() {
        composeTestRule.run {
            onNodeWithText(yilongMa).assertHasClickAction()
            onNodeWithText(conversations).assertHasClickAction()
        }
    }

    @Test
    fun testOnClick() {
        composeTestRule.run {
            onNodeWithText(yilongMa).performClick()
            onNodeWithContentDescription(goBack).performClick()
            onNodeWithText(conversations).performClick()
            onNodeWithContentDescription(goBack).performClick()
            onNodeWithText(home).assertIsDisplayed()
        }
    }
}
