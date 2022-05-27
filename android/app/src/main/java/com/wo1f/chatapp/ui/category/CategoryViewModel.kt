package com.wo1f.chatapp.ui.category

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.model.category.CategoryRq
import com.wo1f.chatapp.data.repo.CategoryRepo
import com.wo1f.chatapp.ui.CategoryAction
import com.wo1f.chatapp.ui.base.BaseViewModel
import com.wo1f.chatapp.ui.model.OneTFDialogType
import com.wo1f.chatapp.ui.model.State
import com.wo1f.chatapp.ui.model.UiState
import com.wo1f.chatapp.ui.state.DialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repo: CategoryRepo
) : BaseViewModel<CategoryState, CategoryAction, List<CategoryRes>>() {

    private val _category = MutableStateFlow("")
    val category = _category.asStateFlow()
    private val _oneTFDialogState = MutableStateFlow(DialogState<OneTFDialogType>())
    val oneTFDialogState = _oneTFDialogState.asStateFlow()
    private val _listState = MutableStateFlow(LazyListState())
    val listState = _listState

    override suspend fun repoCall(): Flow<DataResource<List<CategoryRes>>> {
        return repo.getAll()
    }

    override suspend fun onLoadSuccess(data: List<CategoryRes>) {
        emitState { UiState.success(CategoryState(data)) }
    }

    override suspend fun onRefreshSuccess(data: List<CategoryRes>) {
        emitState { UiState.success(CategoryState(data)) }
    }

    private fun add(category: String) {
        viewModelScope.launch {
            repo.insert(CategoryRq(category)).collect { result ->
                result.handleActionResult(CategoryAction.Add)
            }
        }
    }

    internal fun onCategoryChange(value: String) {
        _category.value = value
    }

    internal fun clearText() {
        _category.value = ""
    }

    internal fun showOneTFDialog(type: OneTFDialogType) {
        _oneTFDialogState.value = DialogState.show(type)
    }

    internal fun hideOneTFDialog() {
        _oneTFDialogState.value = DialogState.hide()
    }

    internal fun onOneTFDialogButtonClick(text: String) {
        when (_oneTFDialogState.value.type) {
            is OneTFDialogType.AddCategory -> add(text)
        }
    }
}

data class CategoryState(
    val categoryList: List<CategoryRes>? = null
) : State
