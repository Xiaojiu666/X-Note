package com.gx.note

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gx.note.ui.SpanStyleNormal
import com.gx.note.ui.SpanStyleType
import com.gx.note.ui.TextContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DiaryEditViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        initDiaryEditUiState()
    )
    val uiState =
        _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initDiaryEditUiState())

    private fun initDiaryEditUiState(): DiaryEditUiState {
        val richEditorContentUiState = RichEditorContentUiState(
            textFieldValue = TextFieldValue(),
            spanStyleType = SpanStyleNormal,
            onContentChange = {
                viewModelScope.launch {
                    updateRichEditorContentUiState { richEditorUi ->
                        richEditorUi.copy(textFieldValue = it)
                    }
                }
                val textContent = _uiState.value.richEditorContentUiState.textContent
                val currentEditorType = _uiState.value.richEditorContentUiState.spanStyleType
                if (textContent.isEmpty()) {
                    textContent.add(TextContent(it.text, 0, it.text.length, currentEditorType))
                    return@RichEditorContentUiState
                }

                val lastContent = textContent.last()
                if (lastContent.spanStyleType == currentEditorType) {
                    val text =
                        it.text.substring(lastContent.startPosition, it.selection.end)
                    val copy = textContent.last()
                        .copy(
                            text =text,
                            startPosition = lastContent.startPosition,
                            endPosition = it.selection.end
                        )
                    textContent.removeAt(textContent.lastIndex)
                    textContent.add(copy)
                } else {
                    val text =
                        it.text.substring(lastContent.endPosition, it.selection.end)
                    Log.e(
                        "editorTexts",
                        "text${text}"

                    )
                    textContent.add(
                        TextContent(
                            text,
                            lastContent.endPosition,
                            it.selection.end,
                            currentEditorType
                        )
                    )
                }

            }, onSpanTypeChange = {
                viewModelScope.launch {
                    updateRichEditorContentUiState { richEditorUi ->
                        richEditorUi.copy(spanStyleType = it)
                    }
                }
            }, textContent = mutableListOf()
        )
        val richEditorTitleUiState = RichEditorTitleUiState(
            titleValue = TextFieldValue(),
            onTitleChange = {
                viewModelScope.launch {
                    updateRichEditorTitleUiState { richEditorUi ->
                        richEditorUi.copy(titleValue = it)
                    }
                }
            })
        return DiaryEditUiState(richEditorContentUiState, richEditorTitleUiState)
    }


    private suspend fun updateRichEditorTitleUiState(block: (RichEditorTitleUiState) -> RichEditorTitleUiState) {
        val diaryEditUiState = _uiState.value
        _uiState.emit(
            diaryEditUiState.copy(
                richEditorTitleUiState = block(diaryEditUiState.richEditorTitleUiState)
            )
        )
    }

    private suspend fun updateRichEditorContentUiState(block: (RichEditorContentUiState) -> RichEditorContentUiState) {
        val diaryEditUiState = _uiState.value
        _uiState.emit(
            diaryEditUiState.copy(
                richEditorContentUiState = block(diaryEditUiState.richEditorContentUiState)
            )
        )
    }

    data class DiaryEditUiState(
        val richEditorContentUiState: RichEditorContentUiState,
        val richEditorTitleUiState: RichEditorTitleUiState
    ) : UiStateWrapper


    data class RichEditorTitleUiState(
        val titleValue: TextFieldValue,
        val onTitleChange: (TextFieldValue) -> Unit
    )

    data class RichEditorContentUiState(
        val textFieldValue: TextFieldValue,
        val spanStyleType: SpanStyleType,
        val textContent: MutableList<TextContent>,
        val onContentChange: (TextFieldValue) -> Unit,
        val onSpanTypeChange: (SpanStyleType) -> Unit
    ) : UiStateWrapper


}