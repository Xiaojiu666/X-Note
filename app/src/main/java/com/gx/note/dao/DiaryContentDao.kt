package com.gx.note.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.gx.note.entity.DiaryContent
import com.gx.note.entity.DiaryContentWithTextContent
import com.gx.note.entity.TABLE_NAME_DIARY
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryContentDao {
    @Query("SELECT * FROM $TABLE_NAME_DIARY")
    fun getDiaryList(): List<DiaryContent>

    @Query("SELECT * FROM $TABLE_NAME_DIARY LIMIT :pageNum OFFSET :pageSize")
    fun getDiaryList(pageNum: Int, pageSize: Int): List<DiaryContent>

    @Query("SELECT * FROM $TABLE_NAME_DIARY WHERE id == :id")
    fun getDiaryDetailsForId(id: Int): Flow<DiaryContent>

    @Query("SELECT * FROM $TABLE_NAME_DIARY")
    fun getUsers(): PagingSource<Int, DiaryContent>

    @Query("SELECT * FROM diarycontent WHERE id == :id")
    fun loadAllByIds(id: Int): DiaryContent
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): com.gx.note.entity.DiaryContent

    @Insert
    fun insert(diaryContent: DiaryContent): Long

    @Insert
    fun insertAll(vararg users: DiaryContent)

    @Delete
    fun delete(user: DiaryContent)

//    @Transaction
//    @Query("SELECT * FROM diarycontent")
//    fun getUsersWithPlaylists(): List<DiaryContentWithTextContent>

//    @Transaction
//    @Query("SELECT * FROM diarycontent WHERE uid IN (:userIds)")
//    fun getById(): com.gx.note.entity.DiaryContentWithTextContent
}