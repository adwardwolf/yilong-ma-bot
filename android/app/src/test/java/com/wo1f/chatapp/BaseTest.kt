/**
 * @author Adwardwo1f
 * @created May 31, 2022
 */

package com.wo1f.chatapp

import io.mockk.MockKAnnotations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTest {

    private lateinit var dispatcher: CoroutineDispatcher

    @BeforeAll
    fun setup() {
        MockKAnnotations.init(this)
        dispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(dispatcher)
    }

    @AfterAll
    fun finish() {
        Dispatchers.resetMain()
    }

    fun runTest(testBody: suspend TestScope.() -> Unit) = runTest(dispatcher) { testBody() }

    infix fun <T> T?.shouldBe(expected: T) {
        Assertions.assertEquals(expected, this)
    }

    infix fun <T> T?.shouldNotBe(expected: T) {
        Assertions.assertNotEquals(expected, this)
    }

    @Suppress("UNUSED_PARAMETER", "TestFunctionName")
    fun TestScope.Given(text: String, block: TestScope.() -> Unit) = this.block()

    @Suppress("UNUSED_PARAMETER", "TestFunctionName")
    fun TestScope.When(text: String, block: TestScope.() -> Unit) = this.block()

    @Suppress("UNUSED_PARAMETER", "TestFunctionName")
    fun TestScope.So(text: String, block: TestScope.() -> Unit) = this.block()

    @Suppress("UNUSED_PARAMETER", "TestFunctionName")
    fun TestScope.Then(text: String, block: TestScope.() -> Unit) = this.block()

    @Suppress("UNUSED_PARAMETER", "TestFunctionName")
    fun TestScope.And(text: String, block: TestScope.() -> Unit) = this.block()

    @Suppress("UNUSED_PARAMETER", "TestFunctionName")
    fun TestScope.Note(text: String, block: TestScope.() -> Unit) = this.block()
}
