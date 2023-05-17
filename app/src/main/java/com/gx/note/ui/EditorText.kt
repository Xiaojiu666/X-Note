package com.gx.note.ui

data class EditorText(
    val text: String,
    val startPosition: Int,
    val endPosition: Int,
    val spanStyleType: SpanStyleType,
)
