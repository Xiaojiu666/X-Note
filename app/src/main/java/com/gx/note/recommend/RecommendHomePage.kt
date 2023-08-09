package com.gx.note.recommend

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.painter.ColorPainter

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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavGraph
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.gx.note.NoteType
import com.gx.note.R
import com.gx.note.entity.RecommendType
import com.gx.note.ui.LocalGlobalNavController
import com.gx.note.ui.RouteConfig
import com.gx.note.ui.RouteConfig.ROUTE_DIARY_LIST_PAGE
import com.gx.note.ui.theme.colorPrimary
import com.gx.note.ui.theme.colorSecondary
import com.gx.note.ui.theme.colorTertiary
import com.gx.note.ui.utils.screenWidth
import kotlinx.coroutines.delay
import java.io.File


@Composable
fun DiaryHomeRoute(diaryHomeViewModel: RecommendHomeViewModel) {
    val current = LocalGlobalNavController.current
    val uiState by diaryHomeViewModel.uiState.collectAsState()
    val colorMatrix = ColorMatrix()
    colorMatrix.setToSaturation(0f)
    val colorFilter = ColorFilter.colorMatrix(colorMatrix)
    val painter = ColorPainter(Color.Red)
    Surface(color =Color.Blue) {
        // 正常布局
        drawImage()
        // 黑白化
        Canvas(modifier = Modifier.fillMaxSize()){
            drawRect(
                color = Color.Yellow,
                colorFilter = colorFilter,
                alpha = 0f,
                blendMode = BlendMode.Saturation
            )
        }
    }
//https://blog.csdn.net/haojiagou/article/details/121907618

//    DiaryHomePage(uiState = uiState, clickable = { current?.navigate(ROUTE_DIARY_LIST_PAGE) })
//
//    LaunchedEffect(Unit) {
//        delay(500)
//        diaryHomeViewModel.initDiaryHomeUiState()
//    }
}

@Composable
fun drawImage(){
    Column {
        Image(painter = painterResource(R.mipmap.ic_launcher),contentDescription = null)
        Image(painter = painterResource(R.mipmap.ic_launcher),contentDescription = null)
        Image(painter = painterResource(R.mipmap.ic_launcher),contentDescription = null)
        Image(painter = painterResource(R.mipmap.ic_launcher),contentDescription = null)
    }
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun DiaryHomePage(uiState: RecommendHomeViewModel.DiaryHomeUiState, clickable: () -> Unit) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorPrimary())
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(colorPrimary())
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorPrimary())
            ) {
                val (title, list, create) = createRefs()
                Box(
                    Modifier
                        .fillMaxWidth()
                        .constrainAs(title) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 50.dp)
                            .clickable {
                                uiState.addNoteEntity()
                            },
                        color = colorSecondary(),
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Serif
                    )

                    Image(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        painter = if (isSystemInDarkTheme()) {
                            painterResource(id = R.drawable.ic_calendar_light)
                        } else {
                            painterResource(id = R.drawable.ic_calendar_night)
                        },
                        contentDescription = ""
                    )
                }
                uiState.recommendList?.let {
                    LazyVerticalStaggeredGrid(modifier = Modifier
                        .constrainAs(list) {
                            start.linkTo(parent.start)
                            top.linkTo(title.bottom)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            height = Dimension.fillToConstraints
                        }
                        .padding(vertical = 8.dp),
                        columns = StaggeredGridCells.Fixed(2),
                        content = {
                            items(it) { recommendEntity ->
                                when (recommendEntity.type) {
                                    RecommendType.DIARY ->
                                        itemDiaryRecommend(
                                            noteName = recommendEntity.type.title,
                                            noteCount = recommendEntity.size,
                                            clickable = clickable
                                        )
                                    RecommendType.PLAN ->
                                        itemPlanRecommend(
                                            noteName = recommendEntity.type.title,
                                            noteCount = recommendEntity.size,
                                            clickable = clickable
                                        )
                                    RecommendType.CLIPBOARD ->
                                        itemPlanRecommend(
                                            noteName = recommendEntity.type.title,
                                            noteCount = recommendEntity.size,
                                            clickable = clickable
                                        )
                                    else -> {
                                        itemDiaryRecommend(
                                            noteName = recommendEntity.type.title,
                                            noteCount = recommendEntity.size,
                                            clickable = clickable
                                        )
                                    }
                                }

                            }
                        })
                }

                var expanded by remember { mutableStateOf(false) }
                AnimatedContent(modifier = Modifier
                    .constrainAs(create) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(end = 40.dp, bottom = 40.dp)
                    .background(colorTertiary(), RoundedCornerShape(10.dp)),
                    targetState = expanded,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(150)) with fadeOut(animationSpec = tween(150)) using SizeTransform { initialSize, targetSize ->
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
                    }) { targetExpanded ->
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
}

@Composable
fun Expanded(clickable: () -> Unit) {
    val navController = LocalGlobalNavController.current!!
    Column(
        modifier = Modifier
            .background(colorTertiary(), RoundedCornerShape(10.dp))
    ) {
        itemNoteType(NoteType.DIARY) {
            navController.navigate(RouteConfig.ROUTE_DIARY_HOME)
        }
        itemNoteType(NoteType.ACCOUNT) {

        }
        itemNoteType(NoteType.TODO) {

        }
    }
}


@Composable
fun itemNoteType(noteType: NoteType, clickable: (NoteType) -> Unit = {}) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                clickable(noteType)
            }
    ) {
        Image(
            modifier = Modifier
                .align(CenterVertically)
                .size(16.dp),
            painter = painterResource(id = noteType.resId),
            contentDescription = ""
        )
        Text(text = noteType.name, modifier = Modifier.padding(4.dp), color = colorSecondary())
    }
}

@Composable
fun AddButton(modifier: Modifier = Modifier, clickable: () -> Unit) {
    Box(modifier = Modifier
        .then(modifier)
        .background(colorTertiary(), RoundedCornerShape(10.dp))
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
fun itemDiaryRecommend(noteName: String, noteCount: Int, clickable: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(8.dp)
            .background(
                colorTertiary(), RoundedCornerShape(10.dp)
            )
            .clickable { clickable() }
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = noteName,
                color = colorSecondary(),
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
                .padding(horizontal = 8.dp),
            targetState = noteCount
        ) { targetCount ->
            Text(
                text = targetCount.toString(), color = colorSecondary(), fontFamily = FontFamily(
                    Font(
                        resId = R.font.number, weight = FontWeight.Bold, style = FontStyle.Italic
                    )
                ), fontSize = 55.sp
            )
        }
    }
}

@Composable
fun itemPlanRecommend(noteName: String, noteCount: Int, clickable: () -> Unit) {
    Box(
        modifier = Modifier
            .width(screenWidth() / 2)
            .height(140.dp)
            .padding(8.dp)
            .background(
                colorTertiary(), RoundedCornerShape(10.dp)
            )
            .clickable { clickable() }
    ) {
        LazyColumn {
            item {
                itemPlanCheckBox()
                itemPlanCheckBox()
            }
        }
    }
}
@Composable
fun itemClipboardRecommend(noteName: String, noteCount: Int, clickable: () -> Unit) {
    Box(
        modifier = Modifier
            .width(screenWidth() / 2)
            .height(140.dp)
            .padding(8.dp)
            .background(
                colorTertiary(), RoundedCornerShape(10.dp)
            )
            .clickable { clickable() }
    ) {
        val imageFile = File("/image.jpg")

        Image(painter = rememberAsyncImagePainter(imageFile),contentDescription = "   ,")
    }
}

@Preview
@Composable
fun itemDiaryRecommendPrw() {
    itemDiaryRecommend("你好", 10) {

    }
}

@Preview
@Composable
fun itemPlanRecommendPrw() {
    itemPlanRecommend("你好", 10) {

    }
}

@Preview
@Composable
fun itemPlanCheckBox() {
    Row(verticalAlignment = CenterVertically, modifier = Modifier.padding(horizontal = 8.dp)) {
        Checkbox(checked = false, onCheckedChange = {})
        Text(text = "Plan A")
    }
}

@Preview
@Composable
fun itemNoteTypePre() {
    itemNoteType(NoteType.DIARY)
}

@Preview()
@Composable
fun AddButtonPrw() {
    AddButton() {

    }
}

