package com.gx.note.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.gx.note.entity.DiaryContent
import com.gx.note.entity.DiaryContentWithTextContent

@Dao
interface DiaryContentDao {
    @Query("SELECT * FROM diarycontent")
    fun getAll(): List<DiaryContent>

    @Query("SELECT * FROM diarycontent WHERE id == :id")
    fun loadAllByIds(id: Int): DiaryContent
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): DiaryContent

    @Insert
    fun insert(diaryContent: DiaryContent): Long

    @Insert
    fun insertAll(vararg users: DiaryContent)

    @Delete
    fun delete(user: DiaryContent)

    @Transaction
    @Query("SELECT * FROM diarycontent")
    fun getUsersWithPlaylists(): List<DiaryContentWithTextContent>

//    @Transaction
//    @Query("SELECT * FROM diarycontent WHERE uid IN (:userIds)")
//    fun getById(): DiaryContentWithTextContent
}