/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.ui.conversations

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.wo1f.chatapp.data.DataResource
import com.wo1f.chatapp.data.model.category.CategoryRes
import com.wo1f.chatapp.data.model.category.CategoryRq
import com.wo1f.chatapp.data.model.conversation.ConversationRes
import com.wo1f.chatapp.data.model.conversation.ConversationRq
import com.wo1f.chatapp.data.model.conversation.GetConversationRes
import com.wo1f.chatapp.data.repo.CategoryRepo
import com.wo1f.chatapp.data.repo.ConversationRepo
import com.wo1f.chatapp.ui.base.BaseViewModel
import com.wo1f.chatapp.ui.model.ConverAction
import com.wo1f.chatapp.ui.model.OneTFDialogType
import com.wo1f.chatapp.ui.model.TwoActionDialogType
import com.wo1f.chatapp.ui.state.DialogState
import com.wo1f.chatapp.ui.state.State
import com.wo1f.chatapp.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverViewModel @Inject constructor(
    private val converRepo: ConversationRepo,
    private val categoryRepo: CategoryRepo,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ConverState, ConverAction, GetConversationRes>() {

    private val _name = MutableStateFlow(
        savedStateHandle.get<String>("name")
            ?: throw IllegalStateException("Category can't be empty")
    )
    val name = _name.asStateFlow()
    private val _category = MutableStateFlow<CategoryRes?>(null)
    val category = _category.asStateFlow()
    private val _question = MutableStateFlow("")
    val question = _question.asStateFlow()
    private val _answer = MutableStateFlow("")
    val answer = _answer.asStateFlow()
    private val _showAddConverDialog = MutableStateFlow(false)
    val showAddConverDialog = _showAddConverDialog.asStateFlow()
    private val _showUpdateConverDialog = MutableStateFlow(false)
    val showUpdateConverDialog = _showUpdateConverDialog.asStateFlow()
    private val _clickedItem = MutableStateFlow<ConversationRes?>(null)
    val clickedItem = _clickedItem.asStateFlow()
    private val _twoActionDialogState = MutableStateFlow(DialogState<TwoActionDialogType>())
    val twoActionDialogState = _twoActionDialogState.asStateFlow()
    private val _oneTFDialogState = MutableStateFlow(DialogState<OneTFDialogType>())
    val oneTFDialogState = _oneTFDialogState.asStateFlow()
    private val _categoryName = MutableStateFlow("")
    val categoryName = _categoryName.asStateFlow()
    private val _listState = MutableStateFlow(LazyListState())
    val listState = _listState

    override suspend fun repoCall(): Flow<DataResource<GetConversationRes>> {
        return converRepo.getAll(_name.value)
    }

    override suspend fun onLoadSuccess(data: GetConversationRes) {
        emitState { UiState.success(ConverState(data)) }
        _category.emit(data.category)
    }

    override suspend fun onRefreshSuccess(data: GetConversationRes) {
        emitState { UiState.success(ConverState(data)) }
        _category.emit(data.category)
    }

    internal fun add(question: String, answer: String) {
        val request = ConversationRq(question, answer, _name.value)
        viewModelScope.launch {
            converRepo.add(request).collect { result ->
                result.handleAction(ConverAction.Add)
            }
        }
    }

    internal fun update(question: String, answer: String) {
        val request = ConversationRq(question, answer, _name.value)
        viewModelScope.launch {
            _clickedItem.value?.id?.let { id ->
                converRepo.update(id, request).collect { result ->
                    result.handleAction(ConverAction.Update)
                }
            }
        }
    }

    private fun delete() {
        _clickedItem.value?.id?.let { id ->
            viewModelScope.launch {
                converRepo.delete(id).collect { result ->
                    result.handleAction(ConverAction.Delete)
                }
            }
        }
    }

    private fun updateCategory(newName: String) {
        viewModelScope.launch {
            categoryRepo.update(_name.value, CategoryRq(newName)).collect { result ->
                result.handleAction(
                    type = ConverAction.UpdateCategory,
                    onSuccess = { _name.value = newName }
                )
            }
        }
    }

    private fun deleteCategory() {
        viewModelScope.launch {
            categoryRepo.delete(_name.value).collect { result ->
                result.handleAction(ConverAction.DeleteCategory)
            }
        }
    }

    internal fun onEditCategoryClick(item: ConversationRes) {
        setClickedItem(item)
        onQuestionChange(item.question)
        onAnswerChange(item.answer)
        setUpdateConverAD(true)
    }

    internal fun onDeleteCategoryClick(item: ConversationRes) {
        setClickedItem(item)
        showTwoActionDialog(TwoActionDialogType.Delete)
    }

    internal fun onQuestionChange(value: String) {
        _question.value = value
    }

    internal fun onAnswerChange(value: String) {
        _answer.value = value
    }

    internal fun onCategoryChange(value: String) {
        _categoryName.value = value
    }

    internal fun clearText() {
        _question.value = ""
        _answer.value = ""
        _categoryName.value = ""
    }

    internal fun setAddConverAD(value: Boolean) {
        _showAddConverDialog.value = value
    }

    internal fun setUpdateConverAD(value: Boolean) {
        _showUpdateConverDialog.value = value
    }

    private fun setClickedItem(item: ConversationRes) {
        _clickedItem.value = item
    }

    private fun showTwoActionDialog(type: TwoActionDialogType) {
        _twoActionDialogState.value = DialogState.show(type)
    }

    internal fun hideTwoActionDialog() {
        _twoActionDialogState.value = DialogState.hide()
    }

    internal fun onTwoDialogActionClick() {
        when (_twoActionDialogState.value.type) {
            is TwoActionDialogType.Delete -> delete()
        }
        hideTwoActionDialog()
    }

    internal fun showOneTFDialog(type: OneTFDialogType) {
        _oneTFDialogState.value = DialogState.show(type)
    }

    internal fun hideOneTFDialog() {
        _oneTFDialogState.value = DialogState.hide()
    }

    internal fun onOneTFDialogButtonClick(text: String) {
        when (_oneTFDialogState.value.type) {
            is OneTFDialogType.DeleteCategory -> deleteCategory()
            is OneTFDialogType.UpdateCategory -> updateCategory(text)
        }
    }
}

data class ConverState(
    val getConverRes: GetConversationRes? = null
) : State
