package com.gx.note.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val todoContent: String,
    val level: Int,
    val startTime: Long,
    val endTime: Long,
    val repeatPeriod: Int,
    val repeatSize: Int,
    val reminderTime: Long,
    val reminderType: Int,
    val todoDesc: String? = null
)
