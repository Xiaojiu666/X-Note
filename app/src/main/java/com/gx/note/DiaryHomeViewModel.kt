package com.gx.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DiaryHomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        DiaryHomeUiState(
            homeNoteList = null, addNoteEntity = {})
    )
    val uiState =
        _uiState.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            DiaryHomeUiState(homeNoteList = null, addNoteEntity = {})
        )

    init {
        initDiaryHomeUiState()
    }

    private fun initDiaryHomeUiState() {
        updateUiState { it ->
            it.copy(
                homeNoteList = listOf(
                    NoteEntity("笔记", 1)
                ),
                addNoteEntity = {
                    val noteEntity = _uiState.value.homeNoteList?.get(0)!!
                    val newValue = noteEntity.noteCount + 1
                    updateUiState {
                        it.copy(
                            homeNoteList = listOf(
                                noteEntity.copy(noteCount = newValue)
                            )
                        )
                    }
                }
            )
        }
    }


    private fun updateUiState(block: (DiaryHomeUiState) -> DiaryHomeUiState) {
        viewModelScope.launch {
            _uiState.emit(block(_uiState.value))
        }
    }

    data class DiaryHomeUiState(
        val homeNoteList: List<NoteEntity>?,
        val addNoteEntity: () -> Unit
    ) : UiStateWrapper

    data class NoteEntity(val noteName: String, var noteCount: Int = 0) {

    }

}