package com.gx.note.usecase

import androidx.paging.PagingSource
import com.gx.note.entity.DiaryContent
import com.gx.note.repo.DiaryRepo
import kotlinx.coroutines.delay
import javax.inject.Inject

class DiaryUseCase @Inject constructor(
    private val repo: DiaryRepo
) {

    suspend fun getDiaryList(): List<DiaryContent> {
        return repo.getDiaryList()
    }


    suspend fun getDiaryList(pageNum: Int, pageSize: Int): List<DiaryContent>{
        delay(3000)
        return repo.getDiaryList(pageNum,pageSize)
    }

    suspend fun saveDiary() {

    }
}