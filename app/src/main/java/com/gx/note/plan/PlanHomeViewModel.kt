package com.gx.note.plan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gx.note.UiStateWrapper
import com.gx.note.entity.RecommendEntity
import com.gx.note.recommend.RecommendHomeViewModel
import com.gx.note.usecase.RecommendHomeUseCase
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.yearMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class PlanHomeViewModel @Inject constructor(private val recommendHomeUseCase: RecommendHomeUseCase) :
    ViewModel() {


    private val _uiState = MutableStateFlow(
        PlanHomeUiState(CalendarUiState(updateSelectionDate = { selectionDate ->
            viewModelScope.launch {
                updatePlanHomeUiState {
                    it.copy(selectionDate = selectionDate)
                }
            }
        }, updateCalendarMode = {
            viewModelScope.launch {
                updatePlanHomeUiState {
                    it.copy(isWeekMode = !it.isWeekMode)
                }
            }
        }))
    )
    val uiState = _uiState.asStateFlow()

    init {
        initDiaryHomeUiState()
    }

    fun initDiaryHomeUiState() {
    }

    private suspend fun updatePlanHomeUiState(block: (CalendarUiState) -> CalendarUiState) {
        val value = uiState.value
        val calendarUiState = block(value.calendarUiState)
        _uiState.emit(value.copy(calendarUiState = calendarUiState))
    }

    data class PlanHomeUiState(
        val calendarUiState: CalendarUiState,
    ) : UiStateWrapper


    data class CalendarUiState(
        val currentDate: LocalDate = LocalDate.now(),
        val currentMonth: YearMonth = currentDate.yearMonth,
        val startMonth: YearMonth = currentMonth.minusMonths(500),
        val endMonth: YearMonth = currentMonth.plusMonths(500),
        val selectionDate: LocalDate = currentDate,
        val updateSelectionDate: (LocalDate) -> Unit,
        val daysOfWeek: List<DayOfWeek> = daysOfWeek(),
        val isWeekMode: Boolean = true,
        val updateCalendarMode: () -> Unit,
    )

}