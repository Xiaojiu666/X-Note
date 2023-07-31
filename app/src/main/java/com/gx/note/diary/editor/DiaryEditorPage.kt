package com.gx.note.diary.editor

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gx.note.BaseBackToolbar
import com.gx.note.R
import com.gx.note.ui.RichEditor
import com.gx.note.ui.withStyle

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiaryEditorPage(diaryEditorViewModel: DiaryEditorViewModel, onBackClick: () -> Unit) {
    val uiState by diaryEditorViewModel.uiState.collectAsStateWithLifecycle()
    val richEditorUiState = uiState.richEditorContentUiState
    val richEditorTitleUiState = uiState.richEditorTitleUiState
    LaunchedEffect(uiState.saveState) {
        when (uiState.saveState) {
            DiaryEditorViewModel.SaveState.SUCCESS ->
                onBackClick()
            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        snackbarHost = {

        },
        topBar = {
            BaseBackToolbar(
                title = "Diary",
                rightIconId = if (isSystemInDarkTheme()) {
                    R.drawable.ic_save_night
                } else {
                    R.drawable.ic_save_light
                },
                onLeftIconClick = onBackClick,
                onRightIconClick = uiState.onSaveDiary,
            )
        }
    ) {

        RichEditor(
            modifier = Modifier.padding(it),
            value = richEditorUiState.textFieldValue?.withStyle(
                richEditorUiState.textContent ?: emptyList()
            ) ?: TextFieldValue(),
            titleValue = richEditorTitleUiState.titleValue,
            onTextFieldValueChange = richEditorUiState.onContentChange,
            onTitleValueChange = richEditorTitleUiState.onTitleChange,
            onTypeChange = richEditorUiState.onSpanTypeChange,
        )
    }

}