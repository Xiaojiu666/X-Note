package com.gx.note.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension.Companion.preferredWrapContent
import androidx.constraintlayout.compose.Dimension.Companion.value
import com.gx.note.ui.utils.Keyboard
import com.gx.note.R
import com.gx.note.ui.theme.body2
import com.gx.note.ui.utils.keyboardAsState


@Composable
fun RichEditor() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        val hint = stringResource(id = R.string.editor_hint)

        var value by remember {
            mutableStateOf(TextFieldValue())
        }

        var editorTexts = remember {
            mutableListOf(EditorText("", 0, 0, SpanStyleNormal))
        }

        var currentEditorType by remember {
            mutableStateOf<SpanStyleType>(SpanStyleNormal)
        }
        val (editorContent, editorBottom) = createRefs()

        EditorBottomView(
            modifier = Modifier.constrainAs(editorBottom) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }, onBoldClick = {
                currentEditorType = SpanStyleBold
            })
        Log.e("editorTexts", "editorTexts$editorTexts")
        EditorContentView(
            hint = hint,
            value = value.withStyle(editorTexts),
            modifier = Modifier.constrainAs(editorContent) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(editorBottom.top)
                height = preferredWrapContent
            }) {
            value = it
            val lastEditorText = editorTexts.last()
            if (lastEditorText.spanStyleType
                == currentEditorType
            ) {
                val copy = editorTexts.last()
                    .copy(
                        text = it.text,
                        startPosition = lastEditorText.startPosition,
                        endPosition = it.selection.end
                    )
                editorTexts.removeAt(editorTexts.lastIndex)
                editorTexts.add(copy)
//                editorTexts = editorTexts.updateLastText(it)
                Log.e("editorTexts", "updateLastText$editorTexts")
            } else {
                val text =
                    it.text.substring(lastEditorText.endPosition, it.selection.end)
                editorTexts.add(
                    EditorText(
                        text,
                        lastEditorText.endPosition,
                        it.selection.end,
                        currentEditorType
                    )
                )
            }
        }
    }
}


fun List<EditorText>.updateLastText(value: TextFieldValue): MutableList<EditorText> =
    mapIndexed { index, editorText ->
        if (index == lastIndex) {
            editorText.copy(text = value.text, endPosition = value.selection.end)
        } else {
            editorText
        }
    }.toMutableList()

fun TextFieldValue.withStyle(editorTexts: List<EditorText>) =
    copy(annotatedString = buildAnnotatedString {
        editorTexts.forEach { editorText ->
            append(editorText.text)
            addStyle(
                SpanStyle(
                    color = Color.Blue
                ),
                editorText.startPosition, editorText.endPosition
            )
        }
    })


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditorContentView(
    hint: String = "",
    value: TextFieldValue,
    modifier: Modifier,
    onValueChange: (TextFieldValue) -> Unit,
) {
    val (focusRequester) = FocusRequester.createRefs()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxSize(),
            value = value,
            textStyle = body2,
            onValueChange = onValueChange,
            decorationBox = {
                if (value.text.isEmpty()) {
                    Text(
                        text = hint, color = Color.Gray
                    )
                }
                it()
            },
        )
    }
}


@Composable
fun EditorBottomView(modifier: Modifier = Modifier, onBoldClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .height(1.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .height(32.dp)
        ) {
            Image(
                modifier = Modifier
                    .align(CenterVertically)
                    .size(16.dp)
                    .clickable {
                        onBoldClick()
                    },
                painter = painterResource(R.mipmap.ic_editor_blod),
                contentDescription = ""
            )
        }

        val keyboardState = keyboardAsState()
        if (keyboardState.value == Keyboard.Closed) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .navigationBarsPadding()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            )
        }
    }
}


@Preview(device = Devices.NEXUS_5)
@Composable
fun preEditorContentView() {
//    RichEditor()
}

@Preview(device = Devices.NEXUS_5, backgroundColor = 0xFFFFFF)
@Composable
fun preEditorBottomView() {
    EditorBottomView() {

    }
}