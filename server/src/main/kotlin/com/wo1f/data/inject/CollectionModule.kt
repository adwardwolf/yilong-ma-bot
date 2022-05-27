/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.data.inject

import com.wo1f.data.collections.CategoryCollection
import com.wo1f.data.collections.ConversationCollection
import org.koin.dsl.module

val collectionModule = module {
    single { ConversationCollection(get()) }
    single { CategoryCollection(get()) }
}
