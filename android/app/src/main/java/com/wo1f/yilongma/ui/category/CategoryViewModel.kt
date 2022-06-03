/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.yilongma.ui.category

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import com.wo1f.yilongma.data.DataResource
import com.wo1f.yilongma.data.model.category.CategoryRes
import com.wo1f.yilongma.data.model.category.CategoryRq
import com.wo1f.yilongma.domain.repo.CategoryRepo
import com.wo1f.yilongma.ui.base.BaseViewModel
import com.wo1f.yilongma.ui.model.CategoryAction
import com.wo1f.yilongma.ui.model.OneTFDialogType
import com.wo1f.yilongma.ui.state.DialogState
import com.wo1f.yilongma.ui.state.State
import com.wo1f.yilongma.ui.state.UiState
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
    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    override suspend fun repoCall(): Flow<DataResource<List<CategoryRes>>> {
        return repo.getAll()
    }

    override suspend fun onLoadSuccess(data: List<CategoryRes>?) {
        emitState { UiState.success(CategoryState(data)) }
    }

    override suspend fun onRefreshSuccess(data: List<CategoryRes>?) {
        emitState { UiState.success(CategoryState(data)) }
    }

    internal fun add(category: String) {
        viewModelScope.launch {
            repo.insert(CategoryRq(category)).collect { result ->
                result.handleAction(CategoryAction.Add)
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
            else -> {}
        }
    }

    internal fun onSearchTextChange(value: String) {
        _search.value = value
    }

    internal fun clearSearchText() {
        _search.value = ""
    }
}

data class CategoryState(
    val categoryList: List<CategoryRes>? = null
) : State
