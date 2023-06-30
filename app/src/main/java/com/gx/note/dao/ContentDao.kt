package com.gx.note.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.gx.note.entity.DiaryContent
import com.gx.note.entity.DiaryContentWithTextContent
import com.gx.note.entity.TextContent

@Dao
interface ContentDao {
    @Query("SELECT * FROM textcontent")
    fun getAll(): List<TextContent>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<DiaryContent>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): DiaryContent

    @Insert
    fun insert(textContent: TextContent): Long

//    @Insert
//    fun insertAll(vararg users: DiaryContent)
//
//
//    @Delete
//    fun delete(user: DiaryContent)

    @Transaction
    @Query("SELECT * FROM diarycontent")
    fun getUsersWithPlaylists(): List<DiaryContentWithTextContent>
}