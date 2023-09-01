package com.gx.note.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gx.note.dao.ContentDao
import com.gx.note.dao.DiaryContentDao
import com.gx.note.dao.TodoEntityDao
import com.gx.note.entity.DiaryContent
import com.gx.note.entity.TextContent
import com.gx.note.entity.TodoEntity


@Database(entities = [DiaryContent::class, TextContent::class,TodoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryContentDao(): DiaryContentDao

    abstract fun textContentDao(): ContentDao

    abstract fun todoEntityDao(): TodoEntityDao
}