package com.gx.note.diary

import android.content.ClipData
import android.util.Log
import androidx.paging.PagedList
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gx.note.entity.DiaryContent
import com.gx.note.usecase.DiaryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DiaryListPagingSource(
    private val diaryUseCase: DiaryUseCase,
    private val onPageStart: () -> Unit,
    private val onPageEnd: () -> Unit,
    private val onPageError: (throwable: Throwable) -> Unit
) : PagingSource<Int, DiaryContent>() {
    override fun getRefreshKey(state: PagingState<Int, DiaryContent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiaryContent> {
        val page = params.key ?: 0

        return withContext(Dispatchers.IO) {
            try {
                if (page == 0) {
                    onPageStart()
                }
                Log.e(
                    "getDiaryList",
                    "page ${page} , params.loadSize ${params.loadSize} "
                )
                val response = diaryUseCase.getDiaryList(
                    params.loadSize, params.loadSize * page
                )
                if (page == 0) {
                    onPageEnd()
                }
                LoadResult.Page(
                    data = response,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response.isEmpty()) null else page + 1
                )
            } catch (e: Exception) {
                Log.e("getDiaryList", "${e.message}")
                onPageError(e)
                LoadResult.Error(e)
            }
        }
    }
}