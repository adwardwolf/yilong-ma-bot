/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.chatapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.SavedStateHandle
import com.wo1f.chatapp.fake_repo.CategoryRepoFake
import com.wo1f.chatapp.fake_repo.ConversationRepoFake
import com.wo1f.chatapp.ui.conversations.ConverScreen
import com.wo1f.chatapp.ui.conversations.ConverViewModel
import com.wo1f.chatapp.ui.main.MainActivity
import com.wo1f.chatapp.ui.theme.ChatAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConverScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var fakeConverRepo: ConversationRepoFake
    private lateinit var fakeCategoryRepo: CategoryRepoFake
    private val savedStateHandle = SavedStateHandle().apply {
        this.set("name", "greetings")
    }
    private lateinit var viewModel: ConverViewModel
    private lateinit var add: String
    private lateinit var goBack: String
    private lateinit var search: String
    private lateinit var close: String
    private lateinit var updateCategory: String
    private lateinit var deleteCategory: String
    private lateinit var update: String
    private lateinit var delete: String
    private lateinit var categoryName: String
    private lateinit var addNewConver: String
    private lateinit var question: String
    private lateinit var answer: String

    @Before
    fun setUp() {
        fakeConverRepo = ConversationRepoFake()
        fakeCategoryRepo = CategoryRepoFake()
        viewModel = ConverViewModel(fakeConverRepo, fakeCategoryRepo, savedStateHandle)
        add = composeTestRule.activity.getString(R.string.add)
        goBack = composeTestRule.activity.getString(R.string.go_back)
        search = composeTestRule.activity.getString(R.string.search)
        close = composeTestRule.activity.getString(R.string.close)
        update = composeTestRule.activity.getString(R.string.update)
        updateCategory = composeTestRule.activity.getString(R.string.update_category)
        deleteCategory = composeTestRule.activity.getString(R.string.delete_category)
        categoryName = composeTestRule.activity.getString(R.string.category_name)
        delete = composeTestRule.activity.getString(R.string.delete)
        addNewConver = composeTestRule.activity.getString(R.string.add_new_conversation)
        question = composeTestRule.activity.getString(R.string.question)
        answer = composeTestRule.activity.getString(R.string.answer)

        composeTestRule.setContent {
            ChatAppTheme {
                ConverScreen(viewModel = viewModel, goBack = {})
            }
        }
    }

    @Test
    fun testTopBar_Display() {
        composeTestRule.run {
            val backButton = onNodeWithContentDescription(goBack)
            val updateButton = onNodeWithTag(updateCategory)
            val deleteButton = onNodeWithTag(deleteCategory)

            backButton.assertIsDisplayed()
            updateButton.assertIsDisplayed()
            deleteButton.assertIsDisplayed()
        }
    }

    @Test
    fun testAddConver() {
        composeTestRule.run {
            val addConverFAB = onNodeWithTag(addNewConver)
            val addConverDialog = onNodeWithText(addNewConver)
            val questionTF = onNodeWithText(question)
            val answerTF = onNodeWithText(answer)
            val addButton = onNodeWithText(add)

            addConverFAB.performClick()

            addConverDialog.assertIsDisplayed()
            questionTF.assertIsFocused()
            answerTF.assertIsNotFocused()
            addButton.assertIsNotEnabled()

            questionTF.performTextInput("Are you testing?")
            addButton.assertIsNotEnabled()
            answerTF.performClick()
            answerTF.performTextInput("Yes, I am")

            addButton.assertIsEnabled()
            addButton.performClick()

            onNodeWithText("Are you testing?", substring = true).assertIsDisplayed()
            onNodeWithText("Yes, I am", substring = true).assertIsDisplayed()
        }
    }

    @Test
    fun testTopBar_UpdateCategory() {
        composeTestRule.run {
            val updateIcon = onNodeWithTag(updateCategory)
            val updateDialog = onNodeWithText(updateCategory)
            val updateButton = onNodeWithText(update)
            val categoryNameTF = onNodeWithTag(categoryName)

            updateIcon.assertIsEnabled()
            updateIcon.performClick()

            updateDialog.assertIsDisplayed()
            updateButton.assertIsEnabled()
            categoryNameTF.assertIsFocused()

            categoryNameTF.performTextClearance()
            categoryNameTF.performTextInput("test")
            updateButton.performClick()

            onNodeWithText("test").assertIsDisplayed()
            updateDialog.assertDoesNotExist()
        }
    }

    @Test
    fun testTopBar_DeleteCategory() {
        composeTestRule.run {
            val deleteIcon = onNodeWithTag(deleteCategory)
            val deleteDialog = onNodeWithText(deleteCategory)
            val deleteButton = onNodeWithText(delete)
            val categoryNameTF = onNodeWithTag(categoryName)

            deleteIcon.assertIsEnabled()
            deleteIcon.performClick()

            deleteDialog.assertIsDisplayed()
            deleteButton.assertIsNotEnabled()
            categoryNameTF.assertIsFocused()
            categoryNameTF.performTextInput("greetings")
            deleteButton.assertIsEnabled()
            deleteButton.performClick()
        }
    }
}
