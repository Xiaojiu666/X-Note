package com.gx.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gx.note.usecase.DiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class DiaryHomeViewModel @Inject constructor(private val diaryUseCase: DiaryUseCase) : ViewModel() {

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
        viewModelScope.launch(IO) {
            val diaryList = diaryUseCase.getDiaryList()
            updateUiState { it ->
                it.copy(
                    homeNoteList = listOf(
                        NoteEntity("日记", diaryList.size),
                        NoteEntity("账本", 0),
                    ),
                    addNoteEntity = ::addNote
                )
            }
        }
    }

    fun addNote() {
    }


    private suspend fun updateUiState(block: (DiaryHomeUiState) -> DiaryHomeUiState) {
        _uiState.emit(block(_uiState.value))
        _uiState.collect()
    }

    data class DiaryHomeUiState(
        val homeNoteList: List<NoteEntity>?,
        val addNoteEntity: () -> Unit
    ) : UiStateWrapper

    data class NoteEntity(val noteName: String, var noteCount: Int = 0) {

    }


}