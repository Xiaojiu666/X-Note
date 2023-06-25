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
import com.gx.note.ui.RichEditor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiaryEditorPage(diaryEditViewModel: DiaryEditViewModel, onBackClick: () -> Unit) {
    val hint = stringResource(id = R.string.editor_hint)
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        snackbarHost = {

        },
        topBar = {
            BaseBackToolbar(title = "Diary", onLeftIconClick = onBackClick)
        }
    ) {
        RichEditor(
            modifier = Modifier.padding(it),
            hint = hint,
            value = textFieldValue,
            onTextFieldValueChange = {
                textFieldValue = it
            })
    }

//    BaseScaffold(scaffoldState = scaffoldState,
//        baseToolbar = {
//            BaseBackToolbar(title = "")
//        },
//        content = {
//            RichEditor(hint = hint,
//                value = textFieldValue,
//                onTextFieldValueChange = {
//                    textFieldValue = it
//                })
//        })
}