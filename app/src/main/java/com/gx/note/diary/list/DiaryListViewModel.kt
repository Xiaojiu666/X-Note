package com.gx.note.diary.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gx.note.LoadableState
import com.gx.note.usecase.DiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryListViewModel @Inject constructor(private val diaryUseCase: DiaryUseCase) : ViewModel() {

    private val _uiState =
        MutableStateFlow<LoadableState<DiaryListUiState>>(LoadableState.Loading())
    val uiState = _uiState.asStateFlow()

    val diaryListPagingSource =
        Pager(PagingConfig(pageSize = 20, enablePlaceholders = false, initialLoadSize = 20)) {
            DiaryListPagingSource(
                diaryUseCase,
                onPageStart = {
                    viewModelScope.launch(IO) {
                        _uiState.emit(LoadableState.Loading())
                    }
                },
                onPageEnd = {
                    initDiaryHomeUiState()
                }, onPageError = {
                }
            )
        }.flow.cachedIn(viewModelScope)

    private fun initDiaryHomeUiState() {
        viewModelScope.launch(IO) {
            _uiState.emit(LoadableState.Success(DiaryListUiState(100)))
        }
    }

    data class DiaryListUiState(
        val listSize: Int,
    )

}