package com.gx.note.diary.editor

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gx.note.ViewModelFactoryProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.android.EntryPointAccessors

@AssistedFactory
interface DiaryEditorViewModelFactory {
    fun create(
        @Assisted("diaryId") diaryId: Int,
    ): DiaryEditorViewModel
}

@Composable
fun diaryEditorViewModel(
    diaryId: Int,
    viewModelStoreOwner: ViewModelStoreOwner
): DiaryEditorViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity, ViewModelFactoryProvider::class.java
    ).diaryEditorViewModelFactory()
    return viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = DiaryEditorViewModel.provideFactory(factory, diaryId)
    )
}
