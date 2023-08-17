package com.gx.note.ui

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.value
import com.gx.note.R
import com.gx.note.baseBlack
import com.gx.note.baseWhite
import com.gx.note.entity.TextContent
import com.gx.note.ui.theme.body2
import com.gx.note.ui.theme.colorSecondary
import com.gx.note.ui.theme.headline6Sans
import com.gx.note.ui.utils.Keyboard
import com.gx.note.ui.utils.keyboardAsState


@Composable
fun RichEditor(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    titleValue: TextFieldValue,
    onTextFieldValueChange: (TextFieldValue) -> Unit,
    onTitleValueChange: (TextFieldValue) -> Unit,
    onTypeChange: (SpanStyleType) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .then(modifier)
            .fillMaxSize()
            .background(baseWhite())
    ) {
        val (editorTitle, editorContent, editorBottom) = createRefs()

        Row(
            modifier = Modifier
                .constrainAs(editorTitle) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ) {
            EditorTitle(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(horizontal = 16.dp)

                    .padding(vertical = 8.dp)
                    .height(72.dp),
                hint = stringResource(id = R.string.editor_title_hint),
                value = titleValue,
                onValueChange = onTitleValueChange
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .height(0.5.dp)
        )

        EditorContentView(
            hint = stringResource(id = R.string.editor_hint),
            value = value,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(editorContent) {
                    start.linkTo(parent.start)
                    top.linkTo(editorTitle.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            onValueChange = onTextFieldValueChange
        )



        EditorBottomView(
            modifier = Modifier.constrainAs(editorBottom) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }, onBoldClick = onTypeChange
        )


    }
}


fun TextFieldValue.withStyle(editorTexts: List<TextContent>) =
    copy(annotatedString = buildAnnotatedString {
        editorTexts.forEach { editorText ->
            append(editorText.text)
            addStyle(
                getSpan(editorText.spanStyleType),
                editorText.startPosition, editorText.endPosition
            )
        }
    })

fun getSpan(id: Int) = when (id) {
    0 -> SpanStyle(fontWeight = FontWeight.Bold)
    1 -> SpanStyle(color = Color.Red)
    else -> {
        SpanStyle()
    }
}

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
            textStyle = body2 + TextStyle(color = baseBlack()),
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditorBottomView(modifier: Modifier = Modifier, onBoldClick: (SpanStyleType) -> Unit) {

    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    softwareKeyboardController?.let {
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .height(0.5.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .horizontalScroll(rememberScrollState())
                .height(46.dp)
        ) {

            Image(
                modifier = Modifier
                    .align(CenterVertically)
                    .size(18.dp)
                    .clickable {
                        onBoldClick(SpanStyleNormal)
                    },
                painter = painterResource(R.mipmap.ic_editor_blod),
                contentDescription = ""
            )


            Image(
                modifier = Modifier
                    .align(CenterVertically)
                    .size(18.dp)
                    .clickable {
                        onBoldClick(SpanStyleBold)
                    },
                painter = painterResource(R.mipmap.ic_editor_blod),
                contentDescription = ""
            )

            Image(
                modifier = Modifier
                    .align(CenterVertically)
                    .size(18.dp)
                    .clickable {
                        onBoldClick(SpanStyleColor(Color.Blue))
                    },
                painter = painterResource(R.mipmap.ic_editor_blod),
                contentDescription = ""
            )
        }

//        val keyboardState = keyboardAsState()
//        if (keyboardState.value == Keyboard.Closed) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.White)
//                    .navigationBarsPadding()
//            )
//        } else {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.White)
//            )
//        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditorTitle(
    modifier: Modifier = Modifier,
    hint: String = "",
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    val (focusRequester) = FocusRequester.createRefs()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    BasicTextField(
        modifier = Modifier
            .then(modifier)
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        singleLine = true,
        value = value,
        textStyle = headline6Sans + TextStyle(color = baseBlack()),
        onValueChange = onValueChange,
        cursorBrush = SolidColor(Color.Gray),
        decorationBox = {
            if (value.text.isEmpty()) {
                Text(
                    text = hint,
                    color = Color.Gray,
                    style = headline6Sans + TextStyle(color = Color.Gray),
                )
            }
            it()
        },
    )
}


@Preview(device = Devices.NEXUS_5)
@Composable
fun preEditorContentView() {
    EditorTitle(value = TextFieldValue("你好")) {

    }
}

@Preview(device = Devices.NEXUS_5, backgroundColor = 0xFFFFFF)
@Composable
fun preEditorBottomView() {
    EditorBottomView() {

    }
}