package com.gx.note.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import com.gx.note.R



sealed class SpanStyleType(val spanStyle: SpanStyle, val id: Int){
}

data class SpanStyleColor(val textColor: Color) :
    SpanStyleType(SpanStyle(color = textColor), 1)

object SpanStyleBold : SpanStyleType(SpanStyle(fontWeight = FontWeight.Bold), 0)

object SpanStyleNormal : SpanStyleType(SpanStyle(), -1)


