/**
 * @author Adwardwo1f
 * @created May 31, 2022
 */

package com.wo1f.yilongma

import com.wo1f.yilongma.data.DataResource
import com.wo1f.yilongma.data.ErrorMsg
import com.wo1f.yilongma.data.model.category.CategoryRq
import com.wo1f.yilongma.data.repo.CategoryRepoImpl
import com.wo1f.yilongma.ui.category.CategoryState
import com.wo1f.yilongma.ui.category.CategoryViewModel
import com.wo1f.yilongma.ui.model.CategoryAction
import com.wo1f.yilongma.ui.model.OneTFDialogType
import com.wo1f.yilongma.ui.state.ActionState
import com.wo1f.yilongma.ui.state.DialogState
import com.wo1f.yilongma.ui.state.UiState
import com.wo1f.yilongma.utils.MockData.mockCategory
import com.wo1f.yilongma.utils.MockData.mockCategoryList
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

    private lateinit var repo: CategoryRepoImpl

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
            coEvery { repo.getAll() } returns flowOf(DataResource.Success(mockCategoryList))
        }

        So("Load Category") {
            viewModel.load()

            Then("[baseState] should be success") {
                val actual = viewModel.baseState.value
                actual shouldBe UiState.success(CategoryState(mockCategoryList))
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
        val body = CategoryRq(name = mockCategory)
        When("[repo.insert] is called, return success") {
            coEvery { repo.insert(body) } returns flowOf(DataResource.Success(null))
        }

        Note("[repo.insert] will be called after insert is successful") {
            coEvery { repo.getAll() } returns flowOf(DataResource.Success(mockCategoryList))
        }

        So("Add Category") {
            viewModel.add(mockCategory)

            Then("[repo.insert] and [repo.getAll] should be called") {
                coVerify(exactly = 1) { repo.insert(body) }
                coVerify(exactly = 1) { repo.getAll() }

                Then("[actionState] should be success") {
                    val actual = viewModel.actionState.value
                    actual shouldBe ActionState.success(CategoryAction.Add)
                }

                And("[categoryList] should matched") {
                    val actual = viewModel.baseState.value.state?.categoryList
                    actual shouldBe mockCategoryList
                }
            }
        }
    }

    @Test
    fun `Test error state of actionState`() = runTest {
        val body = CategoryRq(name = mockCategory)
        When("[repo.insert] is called, return error") {
            coEvery { repo.insert(body) } returns flowOf(DataResource.Error(ErrorMsg.UNKNOWN))
        }

        Note("[repo.insert] will be called after insert is successful") {
            coEvery { repo.getAll() } returns flowOf(DataResource.Success(mockCategoryList))
        }

        So("Add Category") {
            viewModel.add(mockCategory)

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
}
