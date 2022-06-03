/**
 * @author Adwardwo1f
 * @created May 31, 2022
 */

package com.wo1f.yilongma

import androidx.lifecycle.SavedStateHandle
import com.wo1f.yilongma.data.DataResource
import com.wo1f.yilongma.data.ErrorMsg
import com.wo1f.yilongma.data.repo.ChatRepoImpl
import com.wo1f.yilongma.ui.chat.ChatState
import com.wo1f.yilongma.ui.chat.ChatViewModel
import com.wo1f.yilongma.ui.state.UiState
import com.wo1f.yilongma.utils.MockData.mockChatList
import com.wo1f.yilongma.utils.MockData.mockChatRes
import com.wo1f.yilongma.utils.MockData.mockRoom
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

    private lateinit var repo: ChatRepoImpl

    private lateinit var viewModel: ChatViewModel

    private val savedStateHandle = mockk<SavedStateHandle> {
        every { this@mockk.get<String>("room") } returns mockRoom
    }

    @BeforeEach
    fun setUp() {
        repo = mockk()
        viewModel = ChatViewModel(repo, savedStateHandle)
    }

    @Test
    fun `Test success state of baseState`() = runTest {
        When("[repo.getAll] is called, returns success") {
            coEvery { repo.getAll(mockRoom) } returns flowOf(DataResource.Success(mockChatList))
        }

        So("Load Chat") {
            viewModel.load()

            Then("[repo.getAll] should be called") {
                coVerify(exactly = 1) { repo.getAll(mockRoom) }

                And("[baseState] should be success") {
                    val actual = viewModel.baseState.value
                    actual shouldBe UiState.success(ChatState(mockChatList))
                }
            }
        }
    }

    @Test
    fun `Test success error of baseState`() = runTest {
        When("[repo.getAll] is called, returns error") {
            coEvery { repo.getAll(mockRoom) } returns flowOf(DataResource.Error(ErrorMsg.NOT_FOUND))
        }

        So("Load Chat") {
            viewModel.load()

            Then("[repo.getAll] should be called") {
                coVerify(exactly = 1) { repo.getAll(mockRoom) }

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
                coEvery { repo.getAll(mockRoom) } returns flowOf(DataResource.Success(mockChatList))
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
                            chatList?.last()?.roomName shouldBe mockRoom
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
                viewModel.receiveChat(mockChatRes)

                Then("[baseState] should be the same") {
                    val actual = viewModel.baseState.value
                    actual shouldBe UiState()
                }
            }
        }

        Given("[UiState.state] is not null") {
            When("[repo.getAll] is called, return success") {
                coEvery { repo.getAll(mockRoom) } returns flowOf(DataResource.Success(mockChatList))
            }

            So("Get Chats") {
                viewModel.load()

                Then("Receive Chat") {
                    viewModel.receiveChat(mockChatRes)

                    Then("[chatList] should not be null") {
                        val chatList = viewModel.baseState.value.state?.chatList
                        chatList shouldNotBe null

                        And("Last item in [chatList] should be our chat") {
                            chatList?.last() shouldBe mockChatRes
                        }
                    }
                }
            }
        }
    }
}
