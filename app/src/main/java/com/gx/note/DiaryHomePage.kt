package com.gx.note

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun DiaryHomePage(diaryHomeViewModel: DiaryHomeViewModel) {
    val uiState by diaryHomeViewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .navigationBarsPadding()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {

                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 50.dp),
                        color = Color.White,
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Serif
                    )

                    Image(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = ""
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .padding(it)
                        .fillMaxSize()
                ) {
                    items(uiState.homeNoteList?.size ?: 0) {
                        val note = uiState.homeNoteList!![it]
                        itemHome(note.noteName, note.noteCount)
                    }
                }
            }
            var expanded by remember { mutableStateOf(false) }
            AnimatedContent(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp)
                    .padding(end = 40.dp)
                    .background(Color(0xFFCC4F4F), RoundedCornerShape(10.dp)),
                targetState = expanded,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150)) with
                            fadeOut(animationSpec = tween(150)) using
                            SizeTransform { initialSize, targetSize ->
                                if (targetState) {
                                    keyframes {
                                        IntSize(targetSize.width, initialSize.height) at 150
                                        durationMillis = 300
                                    }
                                } else {
                                    keyframes {
                                        IntSize(initialSize.width, targetSize.height) at 150
                                        durationMillis = 300
                                    }
                                }
                            }
                }
            ) { targetExpanded ->
                if (targetExpanded) {
                    Expanded {
                        expanded = false
                    }
                } else {
                    AddButton {
                        expanded = true
                    }
                }
            }
        }
    }
}

@Composable
fun Expanded(clickable: () -> Unit) {
    Column(modifier = Modifier
        .background(Color(0xFFCC4F4F), RoundedCornerShape(10.dp))
        .width(120.dp)
        .height(120.dp)
        .clickable {
            clickable()
        }) {
        Text(text = "日记本")
        Text(text = "账本")
        Text(text = "TODO")
        Text(text = "随笔")
    }
}

@Composable
fun AddButton(modifier: Modifier = Modifier, clickable: () -> Unit) {
    Box(modifier = Modifier
        .then(modifier)
        .background(Color(0xFFCC4F4F), RoundedCornerShape(10.dp))
        .width(60.dp)
        .height(60.dp)
        .clickable {
            clickable()
        }) {
        Image(
            painter = painterResource(id = R.drawable.ic_add),
            modifier = Modifier.align(Alignment.Center),
            contentDescription = ""
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun itemHome(noteName: String, noteCount: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp)
            .background(
                Color(0xff1c1e1f), RoundedCornerShape(10.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = noteName,
                color = Color(0xffa4a5a6),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterStart)
            )
            Image(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = ""
            )
        }

        AnimatedContent(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp), targetState = noteCount
        ) { targetCount ->
            Text(
                text = targetCount.toString(),
                color = Color.White,
                fontFamily = FontFamily(
                    Font(
                        resId = R.font.number, weight = FontWeight.Bold, style = FontStyle.Italic
                    )
                ),
                fontSize = 66.sp
            )
        }
    }
}


@Preview
@Composable
fun itemHomePrw() {
    itemHome("你好", 10)
}

@Preview
@Composable
fun AddButtonPrw() {
    AddButton() {

    }
}

