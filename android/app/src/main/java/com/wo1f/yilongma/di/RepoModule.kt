/**
 * @author Adwardwo1f
 * @created June 1, 2022
 */

package com.wo1f.yilongma.di

import com.wo1f.yilongma.data.repo.CategoryRepoImpl
import com.wo1f.yilongma.data.repo.ChatRepoImpl
import com.wo1f.yilongma.data.repo.ConversationRepoImpl
import com.wo1f.yilongma.domain.repo.CategoryRepo
import com.wo1f.yilongma.domain.repo.ChatRepo
import com.wo1f.yilongma.domain.repo.ConversationRepo
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
