package com.gx.note.dao

import androidx.room.Dao
import androidx.room.Insert
import com.gx.note.entity.TextContent
import com.gx.note.entity.TodoEntity

@Dao
interface TodoEntityDao {
    @Insert
    fun insert(todoEntity: TodoEntity): Long
}