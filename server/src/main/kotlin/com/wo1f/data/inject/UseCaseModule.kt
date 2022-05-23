package com.wo1f.data.inject

import com.wo1f.domain.usecases.conversation.DeleteConversation
import com.wo1f.domain.usecases.conversation.DeleteConversationImpl
import com.wo1f.domain.usecases.conversation.InsertConversation
import com.wo1f.domain.usecases.conversation.InsertConversationImpl
import com.wo1f.domain.usecases.conversation.UpdateConversation
import com.wo1f.domain.usecases.conversation.UpdateConversationImpl
import org.koin.dsl.module

val useCasesModule = module {
    factory<InsertConversation> { InsertConversationImpl(get()) }
    factory<UpdateConversation> { UpdateConversationImpl(get()) }
    factory<DeleteConversation> { DeleteConversationImpl(get()) }
}
