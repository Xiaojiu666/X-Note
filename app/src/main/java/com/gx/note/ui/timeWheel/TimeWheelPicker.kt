package com.gx.note.ui.timeWheel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.gx.note.ui.theme.colorSecondary
import com.gx.note.utils.createLocalDateTime
import com.gx.note.utils.formatString
import com.gx.note.utils.hour
import com.gx.note.utils.hours
import com.gx.note.utils.minute
import com.gx.note.utils.minutes
import com.gx.note.utils.monthDays
import com.gx.note.utils.nowMonthDay
import java.time.LocalTime

@Composable
fun TimeWheelPicker(
    modifier: Modifier = Modifier,
    startTime: LocalTime = LocalTime.now(),
    minTime: LocalTime = LocalTime.MIN,
    maxTime: LocalTime = LocalTime.MAX,
    size: DpSize = DpSize(128.dp, 128.dp),
    rowCount: Int = 3,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedTime: (snappedTime: LocalTime) -> Unit = {},
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (selectorProperties.enabled().value) {
            Surface(
                modifier = Modifier
                    .size(size.width, size.height / rowCount),
                shape = selectorProperties.shape().value,
                color = selectorProperties.color().value,
                border = selectorProperties.border().value
            ) {

            }
        }
        Row {
            WheelTextPicker(
                texts = monthDays.map { it.toString() },
                rowCount = 3,
                startIndex = monthDays.indexOfFirst { it == nowMonthDay },
                color = colorSecondary(),
                onScrollFinished = { snappedIndex ->
                    monthDays.get(snappedIndex)
                    return@WheelTextPicker snappedIndex
                }
            )

            WheelTextPicker(
                texts = hours.formatString(),
                rowCount = 3,
                startIndex = hours.indexOfFirst { it == hour },
                color = colorSecondary()
            )
            WheelTextPicker(
                texts = minutes.formatString(),
                rowCount = 3,
                startIndex = minutes.indexOfFirst { it == minute },
                color = colorSecondary()
            )
        }
    }
}
