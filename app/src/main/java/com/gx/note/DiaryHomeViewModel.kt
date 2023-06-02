package com.gx.note

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DiaryHomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        initDiaryHomeUiState()
    )
    val uiState =
        _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initDiaryHomeUiState())

    private fun initDiaryHomeUiState() =
        DiaryHomeUiState(diaryList = listOf("日记", "账单", "任务", "随笔", "日记", "账单", "任务", "随笔"))

    data class DiaryHomeUiState(
        val diaryList: List<String>,
    ) : UiStateWrapper
}