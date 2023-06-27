package com.gx.note.ui



data class TextContent(
    val text: String,
    val startPosition: Int,
    val endPosition: Int,
    val spanStyleType: SpanStyleType,
)
