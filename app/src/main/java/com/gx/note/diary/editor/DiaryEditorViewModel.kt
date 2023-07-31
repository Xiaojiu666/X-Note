package com.gx.note.diary.editor

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gx.note.UiStateWrapper
import com.gx.note.entity.DiaryContent
import com.gx.note.entity.TextContent
import com.gx.note.repo.DiaryRepo
import com.gx.note.ui.SpanStyleNormal
import com.gx.note.ui.SpanStyleType
import com.gx.note.usecase.DiaryUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DiaryEditorViewModel @AssistedInject constructor(
    private val repo: DiaryRepo,
    private val diaryUseCase: DiaryUseCase,
    @Assisted("diaryId") val diaryId: Int,
) : ViewModel() {
    companion object {
        fun provideFactory(
            assistedFactory: DiaryEditorViewModelFactory,
            diaryId: Int,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(diaryId) as T
            }
        }
    }

    private val _uiState = MutableStateFlow(
        initDiaryEditUiState()
    )
    val uiState =
        _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initDiaryEditUiState())

    private fun initDiaryEditUiState(): DiaryEditUiState {
        val richEditorContentUiState = RichEditorContentUiState(
            spanStyleType = SpanStyleNormal,
            onContentChange = {
                val textContent =
                    _uiState.value.richEditorContentUiState.textContent ?: mutableListOf()
                val textFieldValue = _uiState.value.richEditorContentUiState.textFieldValue
                val currentEditorType = _uiState.value.richEditorContentUiState.spanStyleType
                val oldTextFieldValue = textFieldValue?.text?.length ?: 0

                if (textContent.isEmpty()) {
                    println("textContent isEmpty ")
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
                    val lastContent = textContent.last()
                    val text = it.text.substring(lastContent.startPosition, it.selection.end)
                    val copy = textContent.last().copy(
                        text = text,
                        startPosition = lastContent.startPosition,
                        endPosition = it.selection.end
                    )
                    textContent.removeAt(textContent.lastIndex)
                    if (it.text.length > lastContent.startPosition) {
                        textContent.add(copy)
                    }
                } else if (oldTextFieldValue < it.text.length) {
                    val lastContent = textContent.last()
                    if (lastContent.spanStyleType == currentEditorType.id) {
                        val text = it.text.substring(lastContent.startPosition, it.selection.end)
                        val copy = textContent.last().copy(
                            text = text,
                            startPosition = lastContent.startPosition,
                            endPosition = it.selection.end
                        )
                        textContent.removeAt(textContent.lastIndex)
                        textContent.add(copy)
                    } else {
                        val text = it.text.substring(lastContent.endPosition, it.selection.end)
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
                }

                viewModelScope.launch {
                    updateRichEditorContentUiState { richEditorUi ->
                        richEditorUi.copy(textFieldValue = it)
                    }
                }
            },
            onSpanTypeChange = {
                viewModelScope.launch {
                    updateRichEditorContentUiState { richEditorUi ->
                        richEditorUi.copy(spanStyleType = it)
                    }
                }
            },
            textContent = mutableListOf()
        )
        val richEditorTitleUiState =
            RichEditorTitleUiState(titleValue = TextFieldValue(), onTitleChange = {
                viewModelScope.launch {
                    updateRichEditorTitleUiState { richEditorUi ->
                        richEditorUi.copy(titleValue = it)
                    }
                }
            })
        return DiaryEditUiState(richEditorContentUiState, richEditorTitleUiState, ::saveDiary)
    }

    init {
        initDiary()
    }

    private fun initDiary() {
        viewModelScope.launch(Dispatchers.IO) {
            if (diaryId >= 0) {
                val diaryDetail = diaryUseCase.getDiaryDetails(diaryId)
                updateRichEditorTitleUiState {
                    it.copy(
                        titleValue = TextFieldValue(
                            diaryDetail.name,
                            TextRange(diaryDetail.name.length)
                        )
                    )
                }
                updateRichEditorContentUiState { richEditorUi ->
                    richEditorUi.copy(
                        textFieldValue = TextFieldValue(
                            diaryDetail.textContent.orEmpty(),
                            TextRange(diaryDetail.textContent?.length ?: 0)
                        ), textContent = diaryDetail.textContents?.toMutableList()
                    )
                }
            }
        }
    }


    private suspend fun updateRichEditorTitleUiState(block: (RichEditorTitleUiState) -> RichEditorTitleUiState) {
        val diaryEditUiState = _uiState.value
        _uiState.emit(
            diaryEditUiState.copy(
                richEditorTitleUiState = block(diaryEditUiState.richEditorTitleUiState)
            )
        )
    }

    private suspend fun updateDiaryEditUiState(block: (DiaryEditUiState) -> DiaryEditUiState) {
        val diaryEditUiState = _uiState.value
        _uiState.emit(
            block(diaryEditUiState)
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
            contents?.forEach {
                it.diaryId = insertId.toInt()
                repo.appDatabase.textContentDao().insert(it)
            }
            updateDiaryEditUiState {
                it.copy(saveState = SaveState.SUCCESS)
            }
        }
    }


    data class DiaryEditUiState(
        val richEditorContentUiState: RichEditorContentUiState,
        val richEditorTitleUiState: RichEditorTitleUiState,
        val onSaveDiary: () -> Unit,
        val saveState: SaveState = SaveState.NOTHING,
    ) : UiStateWrapper


    data class RichEditorTitleUiState(
        val titleValue: TextFieldValue,
        val onTitleChange: (TextFieldValue) -> Unit
    )

    data class RichEditorContentUiState(
        val textFieldValue: TextFieldValue? = null,
        val spanStyleType: SpanStyleType,
        val textContent: MutableList<TextContent>?,
        val onContentChange: (TextFieldValue) -> Unit,
        val onSpanTypeChange: (SpanStyleType) -> Unit
    ) : UiStateWrapper

    enum class SaveState {
        SUCCESS, ERROR, LOADING, NOTHING, EMPTY, TOO_LONG,
    }

}