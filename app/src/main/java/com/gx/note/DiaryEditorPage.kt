package com.gx.note

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gx.note.ui.RichEditor
import com.gx.note.ui.SpanStyleType
import com.gx.note.ui.withStyle

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiaryEditorPage(diaryEditViewModel: DiaryEditViewModel, onBackClick: () -> Unit) {
    val uiState by diaryEditViewModel.uiState.collectAsStateWithLifecycle()
    val richEditorUiState = uiState.richEditorContentUiState
    val richEditorTitleUiState = uiState.richEditorTitleUiState
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        snackbarHost = {

        },
        topBar = {
            BaseBackToolbar(
                title = "Diary",
                rightIconId = R.drawable.ic_save,
                onLeftIconClick = onBackClick,
                onRightIconClick = uiState.onSaveDiary,
            )
        }
    ) {

        RichEditor(
            modifier = Modifier.padding(it),
            value = richEditorUiState.textFieldValue.withStyle(richEditorUiState.textContent),
            titleValue = richEditorTitleUiState.titleValue,
            onTextFieldValueChange = richEditorUiState.onContentChange,
            onTitleValueChange = richEditorTitleUiState.onTitleChange,
            onTypeChange = richEditorUiState.onSpanTypeChange,
        )
    }

}