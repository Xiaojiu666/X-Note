package com.gx.note.usecase

import com.gx.note.entity.DiaryContent
import com.gx.note.repo.DiaryRepository
import javax.inject.Inject

class DiaryUseCase @Inject constructor(
    private val repo: DiaryRepository
) {
    suspend fun getDiaryList(pageNum: Int, pageSize: Int): List<DiaryContent> {
        return repo.getDiaryList(pageNum, pageSize)
    }

    suspend fun getDiaryDetails(id: Int): DiaryContent {
        return repo.getDiaryDetails(id)
    }
}