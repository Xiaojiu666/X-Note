package com.gx.note.repo

import com.gx.note.database.AppDatabase
import com.gx.note.entity.DiaryContent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class DiaryRepository @Inject constructor(val appDatabase: AppDatabase) {

    suspend fun getDiaryList(): List<DiaryContent> {
        return appDatabase.diaryContentDao().getDiaryList()
    }

    suspend fun getDiaryList(pageNum: Int, pageSize: Int): List<DiaryContent> {
        return appDatabase.diaryContentDao().getDiaryList(pageNum, pageSize)
    }

    suspend fun getDiaryDetails(id: Int): DiaryContent {
        return appDatabase.diaryContentDao().getDiaryDetailsForId(id)
            .zip(appDatabase.textContentDao().getDiaryTextForId(id)) { diaryContent, textContents ->

                diaryContent.textContent = textContents.joinToString("") {
                    it.text
                }
                diaryContent.textContents = textContents
                diaryContent
            }.first()
    }

}