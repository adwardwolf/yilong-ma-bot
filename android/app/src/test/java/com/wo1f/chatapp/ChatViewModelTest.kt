package com.wo1f.chatapp

import androidx.lifecycle.SavedStateHandle
import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.ErrorMsg
import com.wo1f.chatapp.data.model.chat.ChatRes
import com.wo1f.chatapp.data.repo.ChatRepo
import com.wo1f.chatapp.ui.chat.ChatState
import com.wo1f.chatapp.ui.chat.ChatViewModel
import com.wo1f.chatapp.ui.state.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest : BaseTest() {

    private lateinit var repo: ChatRepo

    private lateinit var viewModel: ChatViewModel

    private val savedStateHandle = mockk<SavedStateHandle> {
        every { this@mockk.get<String>("room") } returns room
    }

    @BeforeEach
    fun setUp() {
        repo = mockk()
        viewModel = ChatViewModel(repo, savedStateHandle)
    }

    @Test
    fun `Test success state of baseState`() = runTest {
        When("[repo.getAll] is called, returns success") {
            coEvery { repo.getAll(room) } returns flowOf(DataResource.Success(chatList))
        }

        So("Load Chat") {
            viewModel.load()

            Then("[repo.getAll] should be called") {
                coVerify(exactly = 1) { repo.getAll(room) }

                And("[baseState] should be success") {
                    val actual = viewModel.baseState.value
                    actual shouldBe UiState.success(ChatState(chatList))
                }
            }
        }
    }

    @Test
    fun `Test success error of baseState`() = runTest {
        When("[repo.getAll] is called, returns error") {
            coEvery { repo.getAll(room) } returns flowOf(DataResource.Error(ErrorMsg.NOT_FOUND))
        }

        So("Load Chat") {
            viewModel.load()

            Then("[repo.getAll] should be called") {
                coVerify(exactly = 1) { repo.getAll(room) }

                And("[baseState] should be error") {
                    val actual = viewModel.baseState.value
                    actual shouldBe UiState.error(ErrorMsg.NOT_FOUND.value, null)
                }
            }
        }
    }

    @Test
    fun `Test sendChat method`() = runTest {
        val text = "Hi, How are you?"

        Given("[UiState.state] is null") {
            So("Send Chat") {
                viewModel.sendChat(text)

                Then("[baseState] should be the same") {
                    val actual = viewModel.baseState.value
                    actual shouldBe UiState()
                }
            }
        }

        Given("[UiState.state] is not null") {
            When("[repo.getAll] is called, return success") {
                coEvery { repo.getAll(room) } returns flowOf(DataResource.Success(chatList))
            }

            So("Get Chats") {
                viewModel.load()

                Then("Send Chat") {
                    viewModel.sendChat(text)

                    Then("[chatList] should not be null") {
                        val chatList = viewModel.baseState.value.state?.chatList
                        chatList shouldNotBe null

                        And("Last item's in [chatList] should be our text") {
                            chatList?.last()?.text shouldBe text
                            chatList?.last()?.roomName shouldBe room
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `Test receiveChat method`() = runTest {
        Given("[UiState.state] is null") {
            So("Receive Chat") {
                viewModel.receiveChat(chatRes)

                Then("[baseState] should be the same") {
                    val actual = viewModel.baseState.value
                    actual shouldBe UiState()
                }
            }
        }

        Given("[UiState.state] is not null") {
            When("[repo.getAll] is called, return success") {
                coEvery { repo.getAll(room) } returns flowOf(DataResource.Success(chatList))
            }

            So("Get Chats") {
                viewModel.load()

                Then("Receive Chat") {
                    viewModel.receiveChat(chatRes)

                    Then("[chatList] should not be null") {
                        val chatList = viewModel.baseState.value.state?.chatList
                        chatList shouldNotBe null

                        And("Last item in [chatList] should be our chat") {
                            chatList?.last() shouldBe chatRes
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val room = "1"
        private val chatList = listOf(
            ChatRes(id = "1", userName = "adwardwo1f", roomName = "1", text = "Hi", null),
            ChatRes(id = "2", userName = "Yilong Ma", roomName = "1", text = "Yes hi", null),
            ChatRes(id = "3", userName = "adwardwo1f", roomName = "1", text = "Hello", null)
        )
        private val chatRes = ChatRes(
            id = "4",
            userName = "adwardwo1f",
            roomName = "1",
            text = "How are you doing son?",
            null
        )
    }
}
