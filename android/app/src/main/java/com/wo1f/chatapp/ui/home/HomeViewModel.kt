package com.wo1f.chatapp.ui.home

import androidx.lifecycle.ViewModel
import com.wo1f.chatapp.data.repo.ConversationRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: ConversationRepo) : ViewModel()
