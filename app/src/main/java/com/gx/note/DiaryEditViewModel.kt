package com.gx.note

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gx.note.entity.DiaryContent
import com.gx.note.entity.TextContent
import com.gx.note.repo.DiaryRepo
import com.gx.note.ui.SpanStyleNormal
import com.gx.note.ui.SpanStyleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryEditViewModel @Inject constructor(
    val repo: DiaryRepo
) : ViewModel() {

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
                val textContent = _uiState.value.richEditorContentUiState.textContent
                val textFieldValue = _uiState.value.richEditorContentUiState.textFieldValue
                val currentEditorType = _uiState.value.richEditorContentUiState.spanStyleType
                val oldTextFieldValue = textFieldValue.text.length

                if (textContent.isEmpty()) {
                    textContent.add(
                        TextContent(
                            text = it.text,
                            startPosition = 0,
                            endPosition = it.text.length,
                            spanStyleType = currentEditorType.id
                        )
                    )
                    viewModelScope.launch {
                        updateRichEditorContentUiState { richEditorUi ->
                            richEditorUi.copy(textFieldValue = it)
                        }
                    }
                    return@RichEditorContentUiState
                }

                if (oldTextFieldValue > it.text.length) {
                    println("onContentChange remove ${oldTextFieldValue - it.text.length}")
                    val lastContent = textContent.last()
                    val text =
                        it.text.substring(lastContent.startPosition, it.selection.end)
                    val copy = textContent.last()
                        .copy(
                            text = text,
                            startPosition = lastContent.startPosition,
                            endPosition = it.selection.end
                        )
                    textContent.removeAt(textContent.lastIndex)
                    if (it.text.length > lastContent.startPosition) {
                        textContent.add(copy)
                    }
                } else if (oldTextFieldValue < it.text.length) {
                    println("onContentChange add ${it.text.length - oldTextFieldValue}")
                    val lastContent = textContent.last()
                    if (lastContent.spanStyleType == currentEditorType.id) {
                        val text =
                            it.text.substring(lastContent.startPosition, it.selection.end)
                        val copy = textContent.last()
                            .copy(
                                text = text,
                                startPosition = lastContent.startPosition,
                                endPosition = it.selection.end
                            )
                        textContent.removeAt(textContent.lastIndex)
                        textContent.add(copy)
                    } else {
                        val text =
                            it.text.substring(lastContent.endPosition, it.selection.end)
                        textContent.add(
                            TextContent(
                                text = text,
                                startPosition = lastContent.endPosition,
                                endPosition = it.selection.end,
                                spanStyleType = currentEditorType.id
                            )
                        )
                    }
                } else {
                    println("onContentChange 移动 ${it.toString()}")
                }

                viewModelScope.launch {
                    updateRichEditorContentUiState { richEditorUi ->
                        richEditorUi.copy(textFieldValue = it)
                    }
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
        return DiaryEditUiState(richEditorContentUiState, richEditorTitleUiState, ::saveDiary)
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

    private fun saveDiary() {
        viewModelScope.launch(Dispatchers.IO) {
            val titleValue = uiState.value.richEditorTitleUiState.titleValue.text
            val contents = uiState.value.richEditorContentUiState.textContent
            val users = DiaryContent(titleValue)
            val insertId = repo.appDatabase.diaryContentDao().insert(users)
            println("insertId $insertId")
            contents.forEach {
                it.diaryId = insertId.toInt()
                repo.appDatabase.textContentDao().insert(it)
            }

            val list = repo.appDatabase.diaryContentDao().getUsersWithPlaylists()
            println("getUsersWithPlaylists $list")
        }

    }


    data class DiaryEditUiState(
        val richEditorContentUiState: RichEditorContentUiState,
        val richEditorTitleUiState: RichEditorTitleUiState,
        val onSaveDiary: () -> Unit
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