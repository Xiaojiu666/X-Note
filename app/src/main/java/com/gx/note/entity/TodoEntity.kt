package com.gx.note.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TodoEntity(
    val todoContent: String,
    val startTime: Long= System.currentTimeMillis(),
    val endTime: Long= System.currentTimeMillis(),
    val repeatPeriod: Int = 0  ,
    val repeatSize: Int = 2 ,
    val reminderTime: Long = System.currentTimeMillis(),
    val reminderType: Int = 0,
    val todoDesc: String? = null,
    val level: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
