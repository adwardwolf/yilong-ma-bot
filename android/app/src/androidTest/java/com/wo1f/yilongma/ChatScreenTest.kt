/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.yilongma

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.SavedStateHandle
import com.wo1f.yilongma.domain.repo.ChatRepo
import com.wo1f.yilongma.fake_repo.ChatRepoFake
import com.wo1f.yilongma.ui.chat.ChatScreen
import com.wo1f.yilongma.ui.chat.ChatViewModel
import com.wo1f.yilongma.ui.main.MainActivity
import com.wo1f.yilongma.ui.theme.ChatAppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var fakeRepo: ChatRepo
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: ChatViewModel
    private lateinit var chat: String
    private lateinit var writeMessage: String
    private lateinit var sendChat: String
    private lateinit var yilongMa: String
    private lateinit var goBack: String

    @Before
    fun setUp() {
        fakeRepo = ChatRepoFake()
        savedStateHandle = SavedStateHandle()
        savedStateHandle.set("room", "1")
        viewModel = ChatViewModel(fakeRepo, savedStateHandle)

        chat = composeTestRule.activity.getString(R.string.chat)
        writeMessage = composeTestRule.activity.getString(R.string.write_message)
        sendChat = composeTestRule.activity.getString(R.string.send_chat)
        yilongMa = composeTestRule.activity.getString(R.string.yilong_ma)
        goBack = composeTestRule.activity.getString(R.string.go_back)

        composeTestRule.setContent {
            ChatAppTheme {
                ChatScreen(viewModel = viewModel, goBack = {})
            }
        }
    }

    @Test
    fun testTopBar() {
        composeTestRule.run {
            val backButton = onNodeWithContentDescription(goBack)
            val title = onNodeWithText(chat)

            title.assertIsDisplayed()
            backButton.assertIsEnabled()
        }
    }

    @Test
    fun testSendMessageTF() {
        composeTestRule.run {
            val writeMessageTF = onNodeWithTag(writeMessage)
            val sendMessageButton = onNodeWithContentDescription(sendChat)

            writeMessageTF.assertIsNotFocused()
            writeMessageTF.assert(hasText(""))
            sendMessageButton.assertIsNotEnabled()

            writeMessageTF.performTextInput("testSendMessageTF")
            sendMessageButton.assertIsEnabled()
            sendMessageButton.performClick()

            onNodeWithText("testSendMessageTF").assertIsDisplayed()
            writeMessageTF.assert(hasText(""))
        }
    }
}
