package com.gx.note

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DiaryEditViewModel : ViewModel() {

    private val _uiStates = MutableStateFlow(
        DiaryEditUiState(null)
    )

    init {
        initDiaryEditUiState()

    }

    private fun initDiaryEditUiState() {
        viewModelScope.launch {
            val richEditorUiState = RichEditorUiState(
                textFieldValue = TextFieldValue(),
                onTextFiledChange = {
                    updateRichEditorUiState { richEditorUi ->
                        richEditorUi.copy ( textFieldValue = it )
                    }
                })
            val diaryEditUiState = DiaryEditUiState(richEditorUiState)
            _uiStates.emit(diaryEditUiState)
        }
    }

    fun updateRichEditorUiState(block: (RichEditorUiState) -> RichEditorUiState) {
        viewModelScope.launch {
            val diaryEditUiState = _uiStates.value
            _uiStates.emit(
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