package com.gx.note.ui

import android.graphics.Color

sealed class SpanStyleType


data class SpanStyleColor(val textColor: Color) : SpanStyleType()

object SpanStyleBold : SpanStyleType()

object SpanStyleNormal : SpanStyleType()
