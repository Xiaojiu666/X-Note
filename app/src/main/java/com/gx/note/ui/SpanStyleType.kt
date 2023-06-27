package com.gx.note.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight

sealed class SpanStyleType(val spanStyle: SpanStyle)


data class SpanStyleColor(val textColor: Color) : SpanStyleType(SpanStyle(color =textColor))

object SpanStyleBold : SpanStyleType(SpanStyle(fontWeight = FontWeight.Bold))

object SpanStyleNormal : SpanStyleType(SpanStyle())
