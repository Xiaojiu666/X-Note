package com.gx.note.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation

const val TABLE_NAME_DIARY = "DiaryContent"

@Entity(tableName = TABLE_NAME_DIARY)
data class DiaryContent(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val createTime: Long = System.currentTimeMillis(),
){
    @Transient
    var textContents: List<TextContent>? = null
        set(value) {
            field = value
        }
    @Transient
    var textContent: String? = ""
        set(value) {
            field = value.orEmpty()
        }
}

data class DiaryContentWithTextContent(
    @Embedded val diary: DiaryContent,
    @Relation(
        parentColumn = "id",
        entityColumn = "diaryId"
    )
    val textContents: List<TextContent>
)


