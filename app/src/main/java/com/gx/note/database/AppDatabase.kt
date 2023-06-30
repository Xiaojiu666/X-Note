package com.gx.note.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gx.note.dao.ContentDao
import com.gx.note.dao.DiaryContentDao
import com.gx.note.entity.DiaryContent
import com.gx.note.entity.TextContent


@Database(entities = [DiaryContent::class, TextContent::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryContentDao(): DiaryContentDao

    abstract fun textContentDao(): ContentDao
}