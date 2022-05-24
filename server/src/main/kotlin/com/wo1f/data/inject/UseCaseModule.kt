package com.wo1f.data.inject

import com.wo1f.domain.usecases.category.GetCategories
import com.wo1f.domain.usecases.category.GetCategoriesImpl
import com.wo1f.domain.usecases.category.InsertCategory
import com.wo1f.domain.usecases.category.InsertCategoryImpl
import com.wo1f.domain.usecases.conversation.DeleteConversation
import com.wo1f.domain.usecases.conversation.DeleteConversationImpl
import com.wo1f.domain.usecases.conversation.GetAllConversations
import com.wo1f.domain.usecases.conversation.GetAllConversationsImpl
import com.wo1f.domain.usecases.conversation.GetConversationsByName
import com.wo1f.domain.usecases.conversation.GetConversationsByNameImpl
import com.wo1f.domain.usecases.conversation.InsertConversation
import com.wo1f.domain.usecases.conversation.InsertConversationImpl
import com.wo1f.domain.usecases.conversation.UpdateConversation
import com.wo1f.domain.usecases.conversation.UpdateConversationImpl
import org.koin.dsl.module

val useCasesModule = module {
    factory<InsertConversation> { InsertConversationImpl(get()) }
    factory<UpdateConversation> { UpdateConversationImpl(get()) }
    factory<DeleteConversation> { DeleteConversationImpl(get()) }
    factory<GetAllConversations> { GetAllConversationsImpl(get()) }
    factory<InsertCategory> { InsertCategoryImpl(get()) }
    factory<GetCategories> { GetCategoriesImpl(get()) }
    factory<GetConversationsByName> { GetConversationsByNameImpl(get()) }
}
