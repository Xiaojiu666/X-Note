package com.gx.note.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
const val TABLE_NAME_TEXT_CONTENT = "TextContent"

@Entity
data class TextContent( @PrimaryKey(autoGenerate = true)
                        val id: Int = 0,
                        var diaryId: Int = -1,
                        val text: String,
                        val startPosition: Int,
                        val endPosition: Int,
                        val spanStyleType: Int,)
