/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wo1f.chatapp.data.api.CategoryService
import com.wo1f.chatapp.data.api.ConversationService
import com.wo1f.chatapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RestModule {
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

    @Provides
    fun provideConversationService(retrofit: Retrofit): ConversationService {
        return retrofit.create(ConversationService::class.java)
    }

    @Provides
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }
}
