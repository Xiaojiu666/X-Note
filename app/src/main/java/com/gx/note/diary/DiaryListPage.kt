package com.gx.note.diary

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gx.note.BaseBackToolbar
import com.gx.note.LoadCompletePlaceholder
import com.gx.note.LoadMorePlaceholder
import com.gx.note.LoadableLayout
import com.gx.note.LoadableState
import com.gx.note.R
import com.gx.note.entity.DiaryContent
import com.gx.note.ui.LocalGlobalNavController
import com.gx.note.ui.RouteConfig.ROUTE_DIARY_HOME

@Composable
fun DiaryListRoute(diaryListViewModel: DiaryListViewModel) {
    val uiState by diaryListViewModel.uiState.collectAsStateWithLifecycle()
    val navController = LocalGlobalNavController.current
    val lazyPagingItems = diaryListViewModel.diaryListPagingSource.collectAsLazyPagingItems()
    DiaryListPage(
        lazyPagingItems = lazyPagingItems,
        uiState = uiState,
        onBackClick = { navController?.popBackStack() },
        onItemClick = { navController?.navigate(ROUTE_DIARY_HOME) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryListPage(
    uiState: LoadableState<DiaryListViewModel.DiaryListUiState>,
    lazyPagingItems: LazyPagingItems<DiaryContent>,
    onBackClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black),
        topBar = { BaseBackToolbar(title = "日记列表", onLeftIconClick = onBackClick) }) {
        LoadableLayout(
            modifier = Modifier.padding(it),
            loadableState = uiState,
            onRetryClick = {}) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                items(lazyPagingItems) {
                    it?.let {
                        DiaryItemView(it) {
                            onItemClick()
                        }
                    }
                }

                when {
                    lazyPagingItems.loadState.append is LoadState.Loading -> {
                        item {
                            LoadMorePlaceholder()
                        }
                    }

                    lazyPagingItems.loadState.append is LoadState.Error -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp)
                                    .background(Color.Red)
                            )
                        }
                    }

                    lazyPagingItems.loadState.refresh is LoadState.NotLoading -> {
                        item {
                            LoadCompletePlaceholder()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DiaryItemView(diary: DiaryContent, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp)
            .background(
                Color(0xff1c1e1f), RoundedCornerShape(10.dp)
            )
            .clickable {
                onItemClick()
            }
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = diary.name,
                color = Color(0xffa4a5a6),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterStart)
            )
            Image(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = ""
            )
        }
    }
}