/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.plugins

import com.wo1f.data.inject.Koin
import com.wo1f.data.inject.collectionModule
import com.wo1f.data.inject.datasourceModule
import com.wo1f.data.inject.useCasesModule
import com.wo1f.data.inject.utilsModule
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureKoin() {
    install(Koin) {
        modules = arrayListOf(
            utilsModule,
            collectionModule,
            datasourceModule,
            useCasesModule
        )
    }
}
