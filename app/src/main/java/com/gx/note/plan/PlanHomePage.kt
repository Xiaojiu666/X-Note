package com.gx.note.plan

import android.annotation.SuppressLint
import android.icu.util.Calendar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.gx.note.BaseBackToolbar
import com.gx.note.LoadableLayout
import com.gx.note.R
import com.gx.note.diary.list.DiaryListEmptyLayout
import com.gx.note.diary.list.DiaryListPage
import com.gx.note.diary.list.DiaryListViewModel
import com.gx.note.plan.view.TodoCheckBox
import com.gx.note.ui.LocalGlobalNavController
import com.gx.note.ui.RouteConfig
import com.gx.note.ui.theme.colorPrimary
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.WeekDayPosition
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.yearMonth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun PlanHomeRoute(viewModel: PlanHomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val calendarUiState = uiState.calendarUiState
    val navController = LocalGlobalNavController.current
    PlanHomePage(
        calendarUiState = calendarUiState,
        onBackClick = { navController?.popBackStack() }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanHomePage(
    calendarUiState: PlanHomeViewModel.CalendarUiState,
    onBackClick: () -> Unit,
) {


    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(colorPrimary()),
        topBar = {
            BaseBackToolbar(
                title = "Plan List",
                onLeftIconClick = onBackClick,
                rightIconId = R.drawable.ic_calendar_night,
                onRightIconClick = {
                    calendarUiState.updateCalendarMode()
                }
            )
        }) {
        Column(modifier = Modifier.padding(it)) {
            val monthState = rememberCalendarState(
                startMonth = calendarUiState.startMonth,
                endMonth = calendarUiState.endMonth,
                firstVisibleMonth = calendarUiState.currentMonth,
                firstDayOfWeek = calendarUiState.daysOfWeek.first(),
            )
            val weekState = rememberWeekCalendarState(
                startDate = calendarUiState.startMonth.atStartOfMonth(),
                endDate = calendarUiState.endMonth.atEndOfMonth(),
                firstVisibleWeekDate = calendarUiState.currentDate,
                firstDayOfWeek = calendarUiState.daysOfWeek.first(),
            )

            DaysOfWeekTitle(daysOfWeek = calendarUiState.daysOfWeek) // Use the title here
            AnimatedVisibility(visible = !calendarUiState.isWeekMode) {
                HorizontalCalendar(
                    state = monthState,
                    dayContent = { day ->
                        val isSelectable = day.position == DayPosition.MonthDate
                        Day(
                            day.date,
                            isSelected = calendarUiState.selectionDate == day.date,
                            isSelectable = isSelectable,
                        ) { clicked ->
                            calendarUiState.updateSelectionDate(clicked)
                        }
                    },
                )
            }
            AnimatedVisibility(visible = calendarUiState.isWeekMode) {
                WeekCalendar(
                    state = weekState,
                    dayContent = { day ->
                        val isSelectable = day.position == WeekDayPosition.RangeDate
                        Day(
                            day.date,
                            isSelected = isSelectable && calendarUiState.selectionDate == day.date,
                            isSelectable = isSelectable,
                        ) { clicked ->
                            calendarUiState.updateSelectionDate(clicked)
//                            selection = clicked
                        }
                    },
                )
            }

            TodoCheckBox("哑铃飞鸟1组", false)
            TodoCheckBox("哑铃飞鸟1组", false)
            TodoCheckBox("哑铃飞鸟1组", false)


        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}

@Composable
fun Day(
    day: LocalDate,
    isSelected: Boolean,
    isSelectable: Boolean,
    onClick: (LocalDate) -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) Color.Red else Color.Transparent)
            .clickable(
                enabled = isSelectable,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column() {
            val textColor = when {
                isSelected -> Color.White
                isSelectable -> Color.Unspecified
                else -> Color.Gray
            }
            Text(
                text = day.dayOfMonth.toString(),
                color = textColor,
                fontSize = 14.sp,
            )
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .align(CenterHorizontally)
                    .background(Color.White)
            )
        }
    }
}


@Preview
@Composable
fun PreDay() {
    Day(LocalDate.now(), true, true) {

    }
}