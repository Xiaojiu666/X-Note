package com.gx.note.plan.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun TodoCheckBox(text: String, checked: Boolean) {
    val textDecoration = if (checked) {
        TextDecoration.LineThrough
    } else {
        TextDecoration.None
    }
    val textColor = if (checked) {
        Color.Blue
    } else {
        Color.Red
    }

    Row {
        Checkbox(checked = true, onCheckedChange = {})
        Text(text = text, textDecoration = textDecoration, color = textColor)
    }
}


@Preview
@Composable
fun PreCheckBox() {
    Column() {
        TodoCheckBox("这是一个账本", true)
        TodoCheckBox("这是一个账本", false)
    }

}