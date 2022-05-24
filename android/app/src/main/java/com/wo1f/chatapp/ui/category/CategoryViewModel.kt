package com.wo1f.chatapp.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.repo.CategoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repo: CategoryRepo
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryState())
    val state = _state.asStateFlow()

    init {
        getCategoryList()
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            repo.getCategories().collect { result ->
                result.onSuccess { data ->
                    if (data != null) {
                        _state.emit(CategoryState.success(data))
                    }
                }.onFailure { message ->
                    _state.emit(CategoryState.error(message))
                }.onLoading {
                    _state.emit(CategoryState.loading())
                }
            }
        }
    }
}

data class CategoryState(
    val categoryList: List<CategoryRes>? = null,
    var isLoading: Boolean = false,
    var isSuccessful: Boolean = false,
    var error: String? = null
) {

    companion object {
        fun success(categoryList: List<CategoryRes>?): CategoryState {
            return CategoryState(isSuccessful = true, categoryList = categoryList)
        }
        fun error(message: String) = CategoryState(error = message)
        fun loading() = CategoryState(isLoading = true)
    }
}
