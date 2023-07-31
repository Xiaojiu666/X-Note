package com.gx.note.usecase

import com.gx.note.entity.DiaryContent
import com.gx.note.entity.RecommendEntity
import com.gx.note.entity.RecommendType
import com.gx.note.repo.DiaryRepo
import javax.inject.Inject

class RecommendHomeUseCase @Inject constructor(
    private val repo: DiaryRepo
) {
    suspend fun getRecommendList(): List<RecommendEntity> {
        val diaryList = repo.getDiaryList()
        val diary = RecommendEntity(RecommendType.DIARY, diaryList.size)
        val account = RecommendEntity(RecommendType.ACCOUNT_BOOK, 30)
        val plan = RecommendEntity(RecommendType.PLAN, 30)
        val remember = RecommendEntity(RecommendType.REMEMBER, 30)
        return listOf(diary, account, plan, remember)
    }
}