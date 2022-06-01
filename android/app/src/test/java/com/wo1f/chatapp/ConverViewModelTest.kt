package com.wo1f.chatapp

import androidx.lifecycle.SavedStateHandle
import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.ErrorMsg
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.model.conversation.ConversationRes
import com.wo1f.chatapp.data.model.conversation.ConversationRq
import com.wo1f.chatapp.data.model.conversation.GetConversationRes
import com.wo1f.chatapp.data.repo.CategoryRepo
import com.wo1f.chatapp.data.repo.ConversationRepo
import com.wo1f.chatapp.ui.conversations.ConverState
import com.wo1f.chatapp.ui.conversations.ConverViewModel
import com.wo1f.chatapp.ui.model.ConverAction
import com.wo1f.chatapp.ui.model.OneTFDialogType
import com.wo1f.chatapp.ui.model.TwoActionDialogType
import com.wo1f.chatapp.ui.state.ActionState
import com.wo1f.chatapp.ui.state.DialogState
import com.wo1f.chatapp.ui.state.UiState
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

    private lateinit var converRepo: ConversationRepo

    private lateinit var categoryRepo: CategoryRepo

    private lateinit var viewModel: ConverViewModel

    private val savedStateHandle = mockk<SavedStateHandle> {
        every { this@mockk.get<String>("name") } returns name
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
            coEvery { converRepo.getAll(name) } returns flowOf(DataResource.Success(getCategoryRes))
        }

        So("Load Conversation") {
            viewModel.load()

            Then("[converRepo.getAll] should be called") {
                coVerify { converRepo.getAll(name) }

                And("[baseState] should be success") {
                    val actual = viewModel.baseState.value
                    actual shouldBe UiState.success(ConverState(getCategoryRes))
                }
            }
        }
    }

    @Test
    fun `Test error state of baseState`() = runTest {
        When("[converRepo.getAll] is called return error") {
            coEvery { converRepo.getAll(name) } returns flowOf(DataResource.Error(ErrorMsg.UNKNOWN))
        }

        So("Load Conversation") {
            viewModel.load()

            Then("[converRepo.getAll] should be called") {
                coVerify { converRepo.getAll(name) }

                And("[baseState] should be error") {
                    val actual = viewModel.baseState.value
                    actual shouldBe UiState.error(ErrorMsg.UNKNOWN.value, null)
                }
            }
        }
    }

    @Test
    fun `Test success state of actionState`() = runTest {
        val body = ConversationRq(question = "How are you?", "I'm fine", name)

        When("[converRepo.insert] is called return success") {
            coEvery { converRepo.insert(body) } returns flowOf(DataResource.Success(null))
        }

        Note("[converRepo.getAll] will be called after insert is successful") {
            coEvery { converRepo.getAll(name) } returns flowOf(DataResource.Success(getCategoryRes))
        }

        So("Add Conversation") {
            viewModel.add(body.question, body.answer)

            Then("[converRepo.insert] and [converRepo.getAll] should be called") {
                coVerify(exactly = 1) { converRepo.insert(body) }
                coVerify(exactly = 1) { converRepo.getAll(name) }

                Then("[actionState] should be success") {
                    val actual = viewModel.actionState.value
                    actual shouldBe ActionState.success(ConverAction.Add)
                }

                And("[getCategoryRes] should be matched") {
                    val actual = viewModel.baseState.value.state?.getConverRes
                    actual shouldBe getCategoryRes
                }
            }
        }
    }

    @Test
    fun `Test error state of actionState`() = runTest {
        val body = ConversationRq(question = "How are you?", "I'm fine", name)

        When("[converRepo.insert] is called return error") {
            coEvery { converRepo.insert(body) } returns flowOf(DataResource.Success(null))
        }

        Note("[converRepo.getAll] will be called after insert is successful") {
            coEvery { converRepo.getAll(name) } returns flowOf(DataResource.Success(null))
        }

        So("Add Conversation") {
            viewModel.add(body.question, body.answer)

            Then("[converRepo.insert] should be called and [converRepo.getAll] should not be called") {
                coVerify(exactly = 1) { converRepo.insert(body) }
                coVerify { converRepo.getAll(name) wasNot called }

                And("[actionState] should be error") {
                    val actual = viewModel.actionState.value
                    actual shouldBe ActionState.success(ConverAction.Add)
                }
            }
        }
    }

    @Test
    fun `Test update conversation method`() = runTest {
        val body = ConversationRq(question = "How are you?", "I'm fine", name)
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
            coEvery { converRepo.getAll(name) } returns flowOf(DataResource.Success(null))
        }

        Given("[clickedItem.id] is not null") {
            viewModel.onEditConverClick(conversationRes)

            So("Update Conversation") {
                viewModel.update(body.question, body.answer)

                Then("[converRepo.update] should be called") {
                    coVerify(exactly = 1) { converRepo.update(id, body) }
                    coVerify(exactly = 1) { converRepo.getAll(name) }

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
            coEvery { converRepo.getAll(name) } returns flowOf(DataResource.Success(null))
        }

        Given("[clickedItem.id] is not null") {
            viewModel.onDeleteConverClick(conversationRes)

            So("Delete Conversation") {
                // viewModel.delete() is a private method we cannot called it directly
                viewModel.showTwoActionDialog(TwoActionDialogType.Delete)
                viewModel.onTwoDialogActionClick()

                Then("[converRepo.delete] and [converRepo.getAll] should be called") {
                    coVerify(exactly = 1) { converRepo.delete(id) }
                    coVerify(exactly = 1) { converRepo.getAll(name) }

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
            viewModel.onEditConverClick(conversationRes)

            Then("These values should be matched") {
                viewModel.run {
                    clickedItem.value shouldBe conversationRes
                    question.value shouldBe conversationRes.question
                    answer.value shouldBe conversationRes.answer
                    showUpdateConverDialog.value shouldBe true
                }
            }
        }
    }

    @Test
    fun `Test onDeleteCategoryClick method`() = runTest {
        When("Deleted category clicked") {
            viewModel.onDeleteConverClick(conversationRes)

            Then("These values should be matched") {
                viewModel.run {
                    clickedItem.value shouldBe conversationRes
                    twoActionDialogState.value shouldBe DialogState.show(TwoActionDialogType.Delete)
                }
            }
        }
    }

    companion object {
        private const val name = "history"
        private val categoryRes = CategoryRes(
            id = "1",
            name = "greetings",
            createdAt = "March 13, 2022",
            count = 2
        )
        val conversationRes = ConversationRes(
            id = "1",
            answer = "How are you",
            question = "I'm good",
            category = "greetings",
            createdAt = "March 13, 2022"
        )
        val converList = listOf(
            ConversationRes(
                id = "1",
                answer = "How are you",
                question = "I'm good",
                category = "greetings",
                createdAt = "March 13, 2022"
            ),
            ConversationRes(
                id = "2",
                answer = "Hello",
                question = "Hello",
                category = "greetings",
                createdAt = "March 22, 2022"
            )
        )
        private val getCategoryRes = GetConversationRes(categoryRes, converList)
    }
}
