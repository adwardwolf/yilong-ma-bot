package com.wo1f.chatapp.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.model.category.CategoryRq
import com.wo1f.chatapp.data.repo.CategoryRepo
import com.wo1f.chatapp.ui.BaseUiState
import com.wo1f.chatapp.ui.state.ErrorDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repo: CategoryRepo
) : ViewModel() {

    private val _baseState = MutableStateFlow<BaseUiState<CategoryState>>(BaseUiState())
    val baseState = _baseState.asStateFlow()
    private val _actionState = MutableStateFlow<BaseUiState<CategoryState>>(BaseUiState())
    val actionState = _actionState.asStateFlow()
    private val _errorDialogState = MutableStateFlow(ErrorDialogState())
    val errorDialogState = _errorDialogState.asStateFlow()
    private val _category = MutableStateFlow("")
    val category = _category.asStateFlow()
    private val _showAddCategoryAD = MutableStateFlow(false)
    val showAddCategoryAD = _showAddCategoryAD.asStateFlow()

    init {
        getCategoryList()
    }

    private fun getCategoryList() {
        viewModelScope.launch {
            repo.getCategories().collect { result ->
                result.onSuccess { data ->
                    if (data != null) {
                        _baseState.emit(BaseUiState.success(CategoryState(data)))
                    }
                }.onFailure { msg, dialogMsg ->
                    _baseState.emit(BaseUiState.error(msg, dialogMsg))
                }.onLoading {
                    _baseState.emit(BaseUiState.loading())
                }
            }
        }
    }

    fun addCategory(category: String) {
        viewModelScope.launch {
            repo.addCategory(CategoryRq(category)).collect { result ->
                result.onSuccess {
                    _actionState.emit(BaseUiState.success(null))
                    getCategoryList()
                }.onFailure { msg, dialogMsg ->
                    _actionState.emit(BaseUiState.error(msg, dialogMsg))
                    if (dialogMsg != null) {
                        _errorDialogState.emit(ErrorDialogState.show(dialogMsg))
                    }
                }.onLoading {
                    _actionState.emit(BaseUiState.loading())
                }
            }
        }
    }

    fun setAddCategoryAD(value: Boolean) {
        _showAddCategoryAD.value = value
    }

    fun onCategoryChange(value: String) {
        _category.value = value
    }

    fun clearText() {
        _category.value = ""
    }

    fun hideDialog() {
        _errorDialogState.value = ErrorDialogState.hide()
    }
}

data class CategoryState(
    val categoryList: List<CategoryRes>? = null
)
