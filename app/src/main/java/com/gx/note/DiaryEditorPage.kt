package com.gx.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.gx.note.ui.RichEditor

@Composable
fun DiaryEditorPage(diaryEditViewModel: DiaryEditViewModel) {
    val scaffoldState = rememberScaffoldState()
    val hint = stringResource(id = R.string.editor_hint)

    BaseScaffold(scaffoldState = scaffoldState,
        content = {
            RichEditor(hint = hint, value = TextFieldValue(), onTextFieldValueChange = {})
        })
}