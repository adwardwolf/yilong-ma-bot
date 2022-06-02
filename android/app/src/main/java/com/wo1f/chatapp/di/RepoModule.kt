/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.chatapp.di

import com.wo1f.chatapp.data.repo.CategoryRepoImpl
import com.wo1f.chatapp.data.repo.ChatRepoImpl
import com.wo1f.chatapp.data.repo.ConversationRepoImpl
import com.wo1f.chatapp.domain.repo.CategoryRepo
import com.wo1f.chatapp.domain.repo.ChatRepo
import com.wo1f.chatapp.domain.repo.ConversationRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindCategoryRepo(impl: CategoryRepoImpl): CategoryRepo

    @Binds
    abstract fun bindChatRepo(impl: ChatRepoImpl): ChatRepo

    @Binds
    abstract fun bindConverRepo(impl: ConversationRepoImpl): ConversationRepo
}
