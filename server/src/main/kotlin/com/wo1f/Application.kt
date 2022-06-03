/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f

import com.wo1f.plugins.configureKoin
import com.wo1f.plugins.configureMonitoring
import com.wo1f.plugins.configureRouting
import com.wo1f.plugins.configureSerialization
import com.wo1f.plugins.configureStatusPage
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 3000, host = "0.0.0.0") {
        configureKoin()
        configureRouting()
        configureSerialization()
        configureMonitoring()
        configureStatusPage()
    }.start(wait = true)
}
