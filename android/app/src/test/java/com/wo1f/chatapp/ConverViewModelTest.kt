/**
 * @author Adwardwo1f
 * @created May 31, 2022
 */

package com.wo1f.chatapp

import androidx.lifecycle.SavedStateHandle
import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.ErrorMsg
import com.wo1f.chatapp.data.model.conversation.ConversationRq
import com.wo1f.chatapp.data.repo.CategoryRepoImpl
import com.wo1f.chatapp.data.repo.ConversationRepoImpl
import com.wo1f.chatapp.ui.conversations.ConverState
import com.wo1f.chatapp.ui.conversations.ConverViewModel
import com.wo1f.chatapp.ui.model.ConverAction
import com.wo1f.chatapp.ui.model.OneTFDialogType
import com.wo1f.chatapp.ui.model.TwoActionDialogType
import com.wo1f.chatapp.ui.state.ActionState
import com.wo1f.chatapp.ui.state.DialogState
import com.wo1f.chatapp.ui.state.UiState
import com.wo1f.chatapp.utils.MockData.mockConversationRes
import com.wo1f.chatapp.utils.MockData.mockGetCategoryRes
import com.wo1f.chatapp.utils.MockData.mockName
import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ConverViewModelTest : BaseTest() {

    private lateinit var converRepo: ConversationRepoImpl

    private lateinit var categoryRepo: CategoryRepoImpl

    private lateinit var viewModel: ConverViewModel

    private val savedStateHandle = mockk<SavedStateHandle> {
        every { this@mockk.get<String>("name") } returns mockName
    }

    @BeforeEach
    fun setUp() {
        converRepo = mockk()
        categoryRepo = mockk()
        viewModel = ConverViewModel(converRepo, categoryRepo, savedStateHandle)
    }

    @Test
    fun `Test default state`() = runTest {
        assertEquals(UiState<ConverState>(), viewModel.baseState.value)
        assertEquals(ActionState<ConverAction>(), viewModel.actionState.value)
    }

    @Test
    fun `Test success state of baseState`() = runTest {
        When("[converRepo.getAll] is called return success") {
            coEvery { converRepo.getAll(mockName) } returns flowOf(DataResource.Success(mockGetCategoryRes))
        }

        So("Load Conversation") {
            viewModel.load()

            Then("[converRepo.getAll] should be called") {
                coVerify { converRepo.getAll(mockName) }

                And("[baseState] should be success") {
                    val actual = viewModel.baseState.value
                    actual shouldBe UiState.success(ConverState(mockGetCategoryRes))
                }
            }
        }
    }

    @Test
    fun `Test error state of baseState`() = runTest {
        When("[converRepo.getAll] is called return error") {
            coEvery { converRepo.getAll(mockName) } returns flowOf(DataResource.Error(ErrorMsg.UNKNOWN))
        }

        So("Load Conversation") {
            viewModel.load()

            Then("[converRepo.getAll] should be called") {
                coVerify { converRepo.getAll(mockName) }

                And("[baseState] should be error") {
                    val actual = viewModel.baseState.value
                    actual shouldBe UiState.error(ErrorMsg.UNKNOWN.value, null)
                }
            }
        }
    }

    @Test
    fun `Test success state of actionState`() = runTest {
        val body = ConversationRq(question = "How are you?", "I'm fine", mockName)

        When("[converRepo.insert] is called return success") {
            coEvery { converRepo.insert(body) } returns flowOf(DataResource.Success(null))
        }

        Note("[converRepo.getAll] will be called after insert is successful") {
            coEvery { converRepo.getAll(mockName) } returns flowOf(DataResource.Success(mockGetCategoryRes))
        }

        So("Add Conversation") {
            viewModel.add(body.question, body.answer)

            Then("[converRepo.insert] and [converRepo.getAll] should be called") {
                coVerify(exactly = 1) { converRepo.insert(body) }
                coVerify(exactly = 1) { converRepo.getAll(mockName) }

                Then("[actionState] should be success") {
                    val actual = viewModel.actionState.value
                    actual shouldBe ActionState.success(ConverAction.Add)
                }

                And("[getCategoryRes] should be matched") {
                    val actual = viewModel.baseState.value.state?.getConverRes
                    actual shouldBe mockGetCategoryRes
                }
            }
        }
    }

    @Test
    fun `Test error state of actionState`() = runTest {
        val body = ConversationRq(question = "How are you?", "I'm fine", mockName)

        When("[converRepo.insert] is called return error") {
            coEvery { converRepo.insert(body) } returns flowOf(DataResource.Success(null))
        }

        Note("[converRepo.getAll] will be called after insert is successful") {
            coEvery { converRepo.getAll(mockName) } returns flowOf(DataResource.Success(null))
        }

        So("Add Conversation") {
            viewModel.add(body.question, body.answer)

            Then("[converRepo.insert] should be called and [converRepo.getAll] should not be called") {
                coVerify(exactly = 1) { converRepo.insert(body) }
                coVerify { converRepo.getAll(mockName) wasNot called }

                And("[actionState] should be error") {
                    val actual = viewModel.actionState.value
                    actual shouldBe ActionState.success(ConverAction.Add)
                }
            }
        }
    }

    @Test
    fun `Test update conversation method`() = runTest {
        val body = ConversationRq(question = "How are you?", "I'm fine", mockName)
        val id = "1"

        When("[converRepo.update] is called return success") {
            coEvery { converRepo.update(id, body) } returns flowOf(DataResource.Success(null))
        }

        Given("[clickedItem.id] is still null") {
            So("Update Conversation") {
                viewModel.update(body.question, body.answer)

                Then("[converRepo.update] should not be called") {
                    coVerify { converRepo.update(id, body) wasNot called }

                    And("[actionState] should be default") {
                        val actual = viewModel.actionState.value
                        actual shouldBe ActionState()
                    }
                }
            }
        }

        Note("[converRepo.getAll] will be called after update is successful") {
            coEvery { converRepo.getAll(mockName) } returns flowOf(DataResource.Success(null))
        }

        Given("[clickedItem.id] is not null") {
            viewModel.onEditConverClick(mockConversationRes)

            So("Update Conversation") {
                viewModel.update(body.question, body.answer)

                Then("[converRepo.update] should be called") {
                    coVerify(exactly = 1) { converRepo.update(id, body) }
                    coVerify(exactly = 1) { converRepo.getAll(mockName) }

                    And("[actionState] should be success") {
                        val actual = viewModel.actionState.value
                        actual shouldBe ActionState.success(ConverAction.Update)
                    }
                }
            }
        }
    }

    @Test
    fun `Test delete conversation method`() = runTest {
        val id = "1"

        When("[converRepo.delete] is called return success") {
            coEvery { converRepo.delete(id) } returns flowOf(DataResource.Success(null))
        }

        Given("[clickedItem.id] is still null") {
            So("Delete Conversation") {
                // viewModel.delete() is a private method we cannot called it directly
                viewModel.showTwoActionDialog(TwoActionDialogType.Delete)
                viewModel.onTwoDialogActionClick()

                Then("[converRepo.delete] should not be called") {
                    coVerify { converRepo.delete(id) wasNot called }

                    And("[actionState] should be default") {
                        val actual = viewModel.actionState.value
                        actual shouldBe ActionState()
                    }
                }
            }
        }

        Note("[converRepo.getAll] will be called after delete is successful") {
            coEvery { converRepo.getAll(mockName) } returns flowOf(DataResource.Success(null))
        }

        Given("[clickedItem.id] is not null") {
            viewModel.onDeleteConverClick(mockConversationRes)

            So("Delete Conversation") {
                // viewModel.delete() is a private method we cannot called it directly
                viewModel.showTwoActionDialog(TwoActionDialogType.Delete)
                viewModel.onTwoDialogActionClick()

                Then("[converRepo.delete] and [converRepo.getAll] should be called") {
                    coVerify(exactly = 1) { converRepo.delete(id) }
                    coVerify(exactly = 1) { converRepo.getAll(mockName) }

                    And("[actionState] should be success") {
                        val actual = viewModel.actionState.value
                        actual shouldBe ActionState.success(ConverAction.Delete)
                    }
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
    fun `Test twoActionDialogState`() = runTest {
        When("Show dialog") {
            viewModel.showTwoActionDialog(TwoActionDialogType.Delete)

            Then("[dialogState] should be showing") {
                val actual = viewModel.twoActionDialogState.value
                actual shouldBe DialogState.show(TwoActionDialogType.Delete)
            }
        }

        When("Hide dialog") {
            viewModel.hideTwoActionDialog()

            Then("[dialogState] should be hidden") {
                val actual = viewModel.twoActionDialogState.value
                actual shouldBe DialogState.hide()
            }
        }
    }

    @Test
    fun `Test clearText method`() = runTest {
        When("Set value") {
            viewModel.onCategoryChange("greetings")
            viewModel.onQuestionChange("Hello")
            viewModel.onAnswerChange("Hi")

            Then("All values should be matched") {
                viewModel.categoryName.value shouldBe "greetings"
                viewModel.question.value shouldBe "Hello"
                viewModel.answer.value shouldBe "Hi"
            }
        }

        So("Clear text") {
            viewModel.clearText()

            Then("All values should be cleared") {
                viewModel.categoryName.value shouldBe ""
                viewModel.question.value shouldBe ""
                viewModel.answer.value shouldBe ""
            }
        }
    }

    @Test
    fun `Test onEditCategoryClick method`() = runTest {
        When("Edit category clicked") {
            viewModel.onEditConverClick(mockConversationRes)

            Then("These values should be matched") {
                viewModel.run {
                    clickedItem.value shouldBe mockConversationRes
                    question.value shouldBe mockConversationRes.question
                    answer.value shouldBe mockConversationRes.answer
                    showUpdateConverDialog.value shouldBe true
                }
            }
        }
    }

    @Test
    fun `Test onDeleteCategoryClick method`() = runTest {
        When("Deleted category clicked") {
            viewModel.onDeleteConverClick(mockConversationRes)

            Then("These values should be matched") {
                viewModel.run {
                    clickedItem.value shouldBe mockConversationRes
                    twoActionDialogState.value shouldBe DialogState.show(TwoActionDialogType.Delete)
                }
            }
        }
    }
}
