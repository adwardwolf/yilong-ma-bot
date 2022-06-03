/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wo1f.yilongma.data.api.CategoryService
import com.wo1f.yilongma.data.api.ChatService
import com.wo1f.yilongma.data.api.ConversationService
import com.wo1f.yilongma.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL + Constants.SERVICE_PORT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideConversationService(retrofit: Retrofit): ConversationService {
        return retrofit.create(ConversationService::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    @Singleton
    @Provides
    fun provideChatService(retrofit: Retrofit): ChatService {
        return retrofit.create(ChatService::class.java)
    }
}
