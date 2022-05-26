package com.wo1f.data.inject

import com.wo1f.data.datasource.CategoryDataSource
import com.wo1f.data.datasource.ConversationDataSource
import org.koin.dsl.module

val datasourceModule = module {
    single { ConversationDataSource(get(), get()) }
    single { CategoryDataSource(get()) }
}
