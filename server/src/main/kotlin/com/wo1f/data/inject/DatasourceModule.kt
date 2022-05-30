/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.data.inject

import com.wo1f.data.datasource.CategoryDataSource
import com.wo1f.data.datasource.ChatDataSource
import com.wo1f.data.datasource.ConversationDataSource
import org.koin.dsl.module

/**
 * Koin module of all data sources in [com.wo1f.data.datasource]
 */
val datasourceModule = module {
    single { ConversationDataSource(get(), get()) }
    single { CategoryDataSource(get(), get()) }
    single { ChatDataSource(get()) }
}
