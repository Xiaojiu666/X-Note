package com.gx.note.diary.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.gx.note.ui.loadMore.LoadMoreView
import com.gx.note.ui.theme.colorPrimary
import com.gx.note.ui.theme.colorSecondary

@Composable
fun DiaryListRoute(diaryListViewModel: DiaryListViewModel) {
    val uiState by diaryListViewModel.uiState.collectAsStateWithLifecycle()
    val navController = LocalGlobalNavController.current
    val lazyPagingItems = diaryListViewModel.diaryListPagingSource.collectAsLazyPagingItems()
    DiaryListPage(
        lazyPagingItems = lazyPagingItems,
        uiState = uiState,
        onBackClick = { navController?.popBackStack() },
        onItemClick = { navController?.navigate("$ROUTE_DIARY_HOME?diaryId=$it") },
        onEmptyLayoutClick = { navController?.navigate("$ROUTE_DIARY_HOME")}
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryListPage(
    uiState: LoadableState<DiaryListViewModel.DiaryListUiState>,
    lazyPagingItems: LazyPagingItems<DiaryContent>,
    onBackClick: () -> Unit,
    onEmptyLayoutClick: () -> Unit,
    onItemClick: (diaryId: Int) -> Unit
) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(colorPrimary()),
        topBar = { BaseBackToolbar(title = "日记列表", onLeftIconClick = onBackClick) }) {
        LoadableLayout(
            modifier = Modifier.padding(it),
            loadableState = uiState,
            onRetryClick = {

            },
            emptyLayout = {
                DiaryListEmptyLayout(onEmptyLayoutClick)
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorPrimary())
            ) {
                items(lazyPagingItems) {
                    it?.let {
                        DiaryItemView(it) {
                            onItemClick(it.id)
                        }
                    }
                }
                LoadMoreView(lazyPagingItems)
            }
        }
    }
}
@Composable
fun BoxScope.DiaryListEmptyLayout(onEmptyLayoutClick:()->Unit){
    Box(modifier = Modifier.align(Alignment.Center).clickable { onEmptyLayoutClick()  }){
        Text(text = stringResource(R.string.empty_no_data), color = colorSecondary())
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