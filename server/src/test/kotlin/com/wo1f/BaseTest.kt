/**
 * @author Adwardwo1f
 * @created June 2, 2022
 */

package com.wo1f

import com.wo1f.plugins.configureKoin
import com.wo1f.plugins.configureMonitoring
import com.wo1f.plugins.configureSerialization
import com.wo1f.plugins.configureStatusPage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTest {

    val ApplicationTestBuilder.baseClient: HttpClient
        get() = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

    @BeforeAll
    fun setup() = testApplication {
        application {
            configureKoin()
        }
    }

    fun baseTestApplication(block: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
        application {
            configureSerialization()
            configureMonitoring()
            configureStatusPage()
        }
        block()
    }

    infix fun <T> T.shouldBe(expected: T) {
        assertEquals(this, expected)
    }
}
