package com.wo1f.chatapp

import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.ErrorMsg
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.model.category.CategoryRq
import com.wo1f.chatapp.data.repo.CategoryRepo
import com.wo1f.chatapp.ui.category.CategoryState
import com.wo1f.chatapp.ui.category.CategoryViewModel
import com.wo1f.chatapp.ui.model.CategoryAction
import com.wo1f.chatapp.ui.model.OneTFDialogType
import com.wo1f.chatapp.ui.state.ActionState
import com.wo1f.chatapp.ui.state.DialogState
import com.wo1f.chatapp.ui.state.UiState
import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryViewModelTest : BaseTest() {

    private lateinit var repo: CategoryRepo

    private lateinit var viewModel: CategoryViewModel

    @BeforeEach
    fun setUp() {
        repo = mockk()
        viewModel = CategoryViewModel(repo)
    }

    @Test
    fun `Test default state`() = runTest {
        assertEquals(UiState<CategoryState>(), viewModel.baseState.value)
        assertEquals(ActionState<CategoryAction>(), viewModel.actionState.value)
    }

    @Test
    fun `Test success state of baseState`() = runTest {
        When("[repo.getAll] is called, return success") {
            coEvery { repo.getAll() } returns flowOf(DataResource.Success(categoryList))
        }

        So("Load Category") {
            viewModel.load()

            Then("[baseState] should be success") {
                val actual = viewModel.baseState.value
                actual shouldBe UiState.success(CategoryState(categoryList))
            }
        }
    }

    @Test
    fun `Test error state of baseState`() = runTest {
        When("[repo.getAll] is called, return error") {
            coEvery { repo.getAll() } returns flowOf(DataResource.Error(ErrorMsg.UNKNOWN))
        }

        So("Load Category") {
            viewModel.load()

            Then("[baseState] should be error") {
                val actual = viewModel.baseState.value
                actual shouldBe UiState.error(ErrorMsg.UNKNOWN.value, null)
            }
        }
    }

    @Test
    fun `Test success state of actionState`() = runTest {
        val body = CategoryRq(name = category)
        When("[repo.insert] is called, return success") {
            coEvery { repo.insert(body) } returns flowOf(DataResource.Success(null))
        }

        Note("[repo.insert] will be called after insert is successful") {
            coEvery { repo.getAll() } returns flowOf(DataResource.Success(categoryList))
        }

        So("Add Category") {
            viewModel.add(category)

            Then("[repo.insert] and [repo.getAll] should be called") {
                coVerify(exactly = 1) { repo.insert(body) }
                coVerify(exactly = 1) { repo.getAll() }

                Then("[actionState] should be success") {
                    val actual = viewModel.actionState.value
                    actual shouldBe ActionState.success(CategoryAction.Add)
                }

                And("[categoryList] should matched") {
                    val actual = viewModel.baseState.value.state?.categoryList
                    actual shouldBe categoryList
                }
            }
        }
    }

    @Test
    fun `Test error state of actionState`() = runTest {
        val body = CategoryRq(name = category)
        When("[repo.insert] is called, return error") {
            coEvery { repo.insert(body) } returns flowOf(DataResource.Error(ErrorMsg.UNKNOWN))
        }

        Note("[repo.insert] will be called after insert is successful") {
            coEvery { repo.getAll() } returns flowOf(DataResource.Success(categoryList))
        }

        So("Add Category") {
            viewModel.add(category)

            Then("[repo.insert] should be called and [repo.getAll] should no be called") {
                coVerify(exactly = 1) { repo.insert(body) }
                coVerify { repo.getAll() wasNot called }

                And("[actionState] should be error") {
                    val actual = viewModel.actionState.value
                    actual shouldBe ActionState.error(ErrorMsg.UNKNOWN.value, null)
                }
            }
        }
    }

    @Test
    fun `Test oneTfDialogState`() = runTest {
        When("Show dialog") {
            viewModel.showOneTFDialog(OneTFDialogType.AddCategory)

            Then("[dialogState] should be showing") {
                val actual = viewModel.oneTFDialogState.value
                actual shouldBe DialogState.show(OneTFDialogType.AddCategory)
            }
        }

        When("Hide dialog") {
            viewModel.hideOneTFDialog()

            Then("[dialogState] should be hidden") {
                val actual = viewModel.oneTFDialogState.value
                actual shouldBe DialogState.hide()
            }
        }
    }

    @Test
    fun `Test clearText method`() = runTest {
        When("Set value") {
            viewModel.onCategoryChange("greetings")

            Then("Value should be matched") {
                viewModel.category.value shouldBe "greetings"
            }
        }

        So("Clear text") {
            viewModel.clearText()

            Then("Value should be cleared") {
                viewModel.category.value shouldBe ""
            }
        }
    }

    @Test
    fun `Test clearSearchText method`() = runTest {
        When("Set value") {
            viewModel.onSearchTextChange("history")

            Then("Value should be matched") {
                viewModel.search.value shouldBe "history"
            }
        }

        So("Clear search text") {
            viewModel.clearSearchText()

            Then("Value should be cleared") {
                viewModel.search.value shouldBe ""
            }
        }
    }

    companion object {
        private const val category = "english"
        private val categoryList = listOf(
            CategoryRes(id = "1", "english", count = 12, createdAt = "March 13, 2022"),
            CategoryRes(id = "2", "history", count = 24, createdAt = "June 13, 2022"),
            CategoryRes(id = "3", "physics", count = 42, createdAt = "August 13, 2022")
        )
    }
}
