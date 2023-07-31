package com.gx.note

import com.gx.note.diary.editor.DiaryEditorViewModelFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun diaryEditorViewModelFactory(): DiaryEditorViewModelFactory
}
