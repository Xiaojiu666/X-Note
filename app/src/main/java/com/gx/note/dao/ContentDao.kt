package com.gx.note.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.gx.note.entity.DiaryContent
import com.gx.note.entity.DiaryContentWithTextContent
import com.gx.note.entity.TABLE_NAME_TEXT_CONTENT
import com.gx.note.entity.TextContent
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    @Query("SELECT * FROM $TABLE_NAME_TEXT_CONTENT")
    fun getAll(): List<TextContent>

    @Query("SELECT * FROM $TABLE_NAME_TEXT_CONTENT WHERE diaryId = :diaryId")
    fun getDiaryTextForId(diaryId: Int): Flow<List<TextContent>>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): com.gx.note.entity.DiaryContent

    @Insert
    fun insert(textContent: TextContent): Long

//    @Insert
//    fun insertAll(vararg users: com.gx.note.entity.DiaryContent)
//
//
//    @Delete
//    fun delete(user: com.gx.note.entity.DiaryContent)

//    @Transaction
//    @Query("SELECT * FROM $TABLE_NAME_TEXT_CONTENT")
//    fun getUsersWithPlaylists(): List<DiaryContentWithTextContent>
}