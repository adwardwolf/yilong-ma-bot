package com.wo1f.data.inject

import com.wo1f.data.collections.CategoryCollection
import com.wo1f.data.collections.ConversationCollection
import org.koin.dsl.module

val collectionModule = module {
    single { ConversationCollection() }
    single { CategoryCollection() }
}
