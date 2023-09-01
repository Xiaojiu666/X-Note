package com.gx.note.plan

import com.gx.note.utils.WheelMonthDay
import com.gx.note.utils.getDayOfWeekText
import java.time.LocalDate

val now: LocalDate = LocalDate.now()
val year = now.year // 当前年份

val dates = (1..12).map { month -> // 遍历12个月
    (1..now.withMonth(month).lengthOfMonth()) // 遍历每个月份的天数
        .map { day ->
            val date = LocalDate.of(year, month, day)
            "${month}月${date.dayOfMonth}日 ${getDayOfWeekText(date.dayOfWeek)}"
        }
}.flatten()

fun main() {
    // 获取当前日期
    val month = now.monthValue // 月份(1-12)
    val day = now.dayOfMonth // 日期号(1-31)
    val dayOfWeek =getDayOfWeekText(now.dayOfWeek)  // 星期几
   println(WheelMonthDay(month,day,dayOfWeek))
    // 在列表中查找索引
//    val index = dates.indexOfFirst {
//        it ==
//    }


}
