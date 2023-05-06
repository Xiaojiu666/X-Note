package com.gx.note.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.gx.note.R


@Composable
fun RichEditor() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        EditorContentView(Modifier.align(Alignment.Center))

        EditorBottomView(Modifier.align(Alignment.BottomCenter))
    }
}


@Composable
fun EditorContentView(modifier: Modifier = Modifier) {
    val hint = stringResource(id = R.string.editor_hint)
    var value by remember {
        mutableStateOf(TextFieldValue(text = hint))
    }


    BasicTextField(modifier = modifier, value = value, onValueChange = {
        value = it
    })
}


@Composable
fun EditorBottomView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Magenta)
    )
}