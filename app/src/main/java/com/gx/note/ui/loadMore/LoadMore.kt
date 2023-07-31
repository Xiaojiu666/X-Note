package com.gx.note.ui.loadMore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.gx.note.LoadCompletePlaceholder
import com.gx.note.LoadMorePlaceholder

fun <T : Any> LazyListScope.LoadMoreView(lazyPagingItems: LazyPagingItems<T>){
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
