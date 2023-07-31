package com.gx.note.entity

data class RecommendEntity(val type: RecommendType, val size: Int)


enum class RecommendType(val title: String) {
    DIARY("日记"), ACCOUNT_BOOK("账本"), PLAN("任务清单"), REMEMBER("随心记")
}