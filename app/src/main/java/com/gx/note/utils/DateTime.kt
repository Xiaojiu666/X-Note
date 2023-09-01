package com.gx.note.utils

import com.gx.note.plan.now
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

val nowData: LocalDate = LocalDate.now()
val year = nowData.year // 当前年份
val month = nowData.monthValue // 月份(1-12)
val day = nowData.dayOfMonth // 日期号(1-31)
val dayOfWeek = getDayOfWeekText(nowData.dayOfWeek)  // 星期几

val nowMonthDay = WheelMonthDay(
    month, day, dayOfWeek
)

val nowTime: LocalTime = LocalTime.now()
val hour = nowTime.hour
val minute = nowTime.minute


val hours = (0..23).toList()

val minutes = (0..59).toList()

/**
 * 当前年的天数
 */
val monthDays = (1..12).map { month -> // 遍历12个月
    (1..now.withMonth(month).lengthOfMonth()) // 遍历每个月份的天数
        .map { day ->
            val date = LocalDate.of(year, month, day)
            WheelMonthDay(month, day, getDayOfWeekText(date.dayOfWeek))
        }
}.flatten()


data class WheelMonthDay(var month: Int, var day: Int, var week: String) {

    override fun toString(): String {
        return "${month}月${day}日 $week"
    }

    override fun equals(other: Any?): Boolean {
        return other.toString() == this.toString()
    }

    override fun hashCode(): Int {
        var result = month
        result = 31 * result + day
        result = 31 * result + week.hashCode()
        return result
    }
}


fun getDayOfWeekText(dayOfWeek: DayOfWeek) = when (dayOfWeek) {
    DayOfWeek.MONDAY -> "周一"
    DayOfWeek.TUESDAY -> "周二"
    DayOfWeek.WEDNESDAY -> "周三"
    DayOfWeek.THURSDAY -> "周四"
    DayOfWeek.FRIDAY -> "周五"
    DayOfWeek.SATURDAY -> "周六"
    DayOfWeek.SUNDAY -> "周日"
}

fun createLocalDateTime(month: Int, day: Int, hour: Int, minute: Int): LocalDateTime {
    val data = LocalDate.of(year, month, day)
    val time = LocalTime.of(hour, minute)
    return LocalDateTime.of(data, time)
}

fun List<Int>.formatString(): List<String> {
    return map {
        if (it < 10) {
            "0$it"
        } else {
            "$it"
        }
    }
}