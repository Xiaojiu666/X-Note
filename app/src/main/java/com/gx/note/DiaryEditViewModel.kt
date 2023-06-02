package com.gx.note

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DiaryEditViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        initDiaryEditUiState()
    )
    val uiState =
        _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initDiaryEditUiState())

    private fun initDiaryEditUiState(): DiaryEditUiState {
        val richEditorUiState = RichEditorUiState(
            textFieldValue = TextFieldValue(),
            onTextFiledChange = {
                updateRichEditorUiState { richEditorUi ->
                    richEditorUi.copy(textFieldValue = it)
                }
            })
        return DiaryEditUiState(richEditorUiState)
    }

    fun updateRichEditorUiState(block: (RichEditorUiState) -> RichEditorUiState) {
        viewModelScope.launch {
            val diaryEditUiState = _uiState.value
            _uiState.emit(
                diaryEditUiState.copy(
                    richEditorUiState = block(diaryEditUiState.richEditorUiState!!)
                )
            )
        }
    }

    data class DiaryEditUiState(
        val richEditorUiState: RichEditorUiState?
    ) : UiStateWrapper


    data class RichEditorUiState(
        val textFieldValue: TextFieldValue,
        val onTextFiledChange: (TextFieldValue) -> Unit
    ) : UiStateWrapper
}