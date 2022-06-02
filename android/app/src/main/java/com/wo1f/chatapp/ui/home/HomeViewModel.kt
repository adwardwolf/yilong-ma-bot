/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.home

import androidx.lifecycle.ViewModel
import com.wo1f.chatapp.data.repo.ConversationRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: ConversationRepoImpl) : ViewModel()
