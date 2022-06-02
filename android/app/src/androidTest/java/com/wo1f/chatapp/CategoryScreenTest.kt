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
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.wo1f.chatapp.domain.repo.CategoryRepo
import com.wo1f.chatapp.fake_repo.CategoryRepoFake
import com.wo1f.chatapp.ui.category.CategoryScreen
import com.wo1f.chatapp.ui.category.CategoryViewModel
import com.wo1f.chatapp.ui.main.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CategoryScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var fakeRepo: CategoryRepo
    private lateinit var viewModel: CategoryViewModel
    private lateinit var categories: String
    private lateinit var addCategories: String
    private lateinit var categoryName: String
    private lateinit var add: String
    private lateinit var goBack: String
    private lateinit var search: String
    private lateinit var close: String

    @Before
    fun setUp() {
        fakeRepo = CategoryRepoFake()
        viewModel = CategoryViewModel(fakeRepo)
        categories = composeTestRule.activity.getString(R.string.categories)
        addCategories = composeTestRule.activity.getString(R.string.add_new_category)
        categoryName = composeTestRule.activity.getString(R.string.category_name)
        add = composeTestRule.activity.getString(R.string.add)
        goBack = composeTestRule.activity.getString(R.string.go_back)
        search = composeTestRule.activity.getString(R.string.search)
        close = composeTestRule.activity.getString(R.string.close)

        composeTestRule.setContent {
            CategoryScreen(viewModel = viewModel, goBack = { }, goToConver = {})
        }
    }

    @Test
    fun testTopBar() {
        composeTestRule.run {
            val title = onNodeWithText(categories)
            val backButton = onNodeWithContentDescription(goBack)
            title.assertIsDisplayed()
            backButton.assertIsEnabled()
        }
    }

    @Test
    fun testSearchBar() {
        composeTestRule.run {
            val clearTextButton = onNodeWithTag(close)
            val searchTF = onNodeWithText(search)

            searchTF.assertIsDisplayed()
            searchTF.assertIsNotFocused()

            searchTF.performClick()
            searchTF.performTextInput("nkkk")

            clearTextButton.assertIsDisplayed()
            clearTextButton.performClick()

            searchTF.assertTextEquals("nkkk")
        }
    }

    @Test
    fun testAddCategory() {
        composeTestRule.run {
            val addCategoryFAB = onNodeWithTag(addCategories)
            val categoryNameTF = onNodeWithText(categoryName)
            val addButton = onNodeWithText(add)

            addCategoryFAB.assertIsEnabled()
            addCategoryFAB.performClick()

            categoryNameTF.assertIsFocused()
            addButton.assertIsNotEnabled()

            categoryNameTF.performTextInput("testAddItem")
            addButton.assertIsEnabled()
            addButton.performClick()
            onNodeWithText("@testAddItem").assertIsDisplayed()
        }
    }
}
