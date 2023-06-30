package com.gx.note.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.gx.note.ui.SpanStyleType


@Entity
data class DiaryContent(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    )

data class DiaryContentWithTextContent(
    @Embedded val diary: DiaryContent,
    @Relation(
        parentColumn = "id",
        entityColumn = "diaryId"
    )
    val textContents: List<TextContent>
)

@Entity
data class TextContent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var diaryId: Int = -1,
    val text: String,
    val startPosition: Int,
    val endPosition: Int,
    val spanStyleType: Int,
)


