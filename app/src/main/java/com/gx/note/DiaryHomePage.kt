package com.gx.note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryHomePage(diaryHomeViewModel: DiaryHomeViewModel) {
    val uiState by diaryHomeViewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .navigationBarsPadding()
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
                items(uiState.diaryList.size) {
                    itemHome(uiState.diaryList[it])
                }
            }
        }
    }
}

@Composable
fun itemHome(name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp)
            .background(
                Color(0xff1c1e1f),
                RoundedCornerShape(10.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = name,
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
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp),
            text = "66",
            color = Color.White,
            fontFamily = FontFamily(
                Font(
                    resId = R.font.number,
                    weight = FontWeight.Bold,
                    style = FontStyle.Italic
                )
            ),
            fontSize = 66.sp
        )
    }
}

@Preview
@Composable
fun itemHomePrw() {
    itemHome("你好")
}