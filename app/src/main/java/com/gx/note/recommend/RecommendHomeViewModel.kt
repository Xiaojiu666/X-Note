package com.gx.note.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gx.note.UiStateWrapper
import com.gx.note.entity.RecommendEntity
import com.gx.note.usecase.RecommendHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendHomeViewModel @Inject constructor(private val recommendHomeUseCase: RecommendHomeUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(
        DiaryHomeUiState(
            recommendList = null, addNoteEntity = {})
    )
    val uiState =_uiState.asStateFlow()
//    uiState.e
//        _uiState.stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(),
//            DiaryHomeUiState(recommendList = null, addNoteEntity = {})
//        )

    init {
        initDiaryHomeUiState()
    }

    fun initDiaryHomeUiState() {
        viewModelScope.launch(IO) {
            val recommendList = recommendHomeUseCase.getRecommendList()
            println("recommendList ${recommendList.size}")
            updateUiState { it ->
                it.copy(
                    recommendList = recommendList,
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
        val recommendList: List<RecommendEntity>?,
        val addNoteEntity: () -> Unit
    ) : UiStateWrapper

}