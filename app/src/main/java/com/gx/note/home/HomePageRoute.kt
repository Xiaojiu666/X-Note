package com.gx.note.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.G
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.gx.note.R
import com.gx.note.ui.utils.screenHeight
import com.gx.note.ui.utils.screenWidth

@Composable
fun HomePageRoute() {
//    HomePage()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomePage() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2), content = {
            item {
                HomeItem2x2Container {

                }
            }
            item {
                HomeItem2x2Container {

                }
            }
            item {
                HomeItem2x2Container {

                }
            }
            item {
                HomeItem2x2Container {

                }
            }
        })
    }
}

@Composable
fun HomeItem2x1Container(container: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(screenWidth() / 2)
            .height(screenWidth() / 4)
            .background(
                Color(0xff1c1e1f), RoundedCornerShape(10.dp)
            )
    ) {
        container()
    }
}


@Composable
fun HomeItem2x2Container(container: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(screenWidth() / 2)
            .height(screenWidth() / 2)
            .background(
                Color(0xff1c1e1f), RoundedCornerShape(10.dp)
            )
    ) {
        container()
    }
}

@Composable
fun HomeItem2x3Container(container: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(screenWidth() / 2)
            .height(screenWidth() / 4 * 3)
            .background(
                Color(0xff1c1e1f), RoundedCornerShape(10.dp)
            )
    ) {
        container()
    }
}

@Composable
fun HomeItem4x2Container(container: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(screenWidth())
            .height(screenWidth() / 2)
            .background(
                Color(0xff1c1e1f), RoundedCornerShape(10.dp)
            )
    ) {
        container()
    }
}

@Composable
fun FlowLayout(
    modifier: Modifier = Modifier,
    horizontalSpace: Int = 0,
    verticalSpace: Int = 0,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        //measurables是需要测量的子项列表  constraints是来自父布局的测量约束
        //下文中的measurable和placeables都可以理解为父布局的子项（它们只是子项的不同形态）
        val placeables = measurables.map { measurable ->
            //1、可测量子项根据父布局约束进行测量，得到可放置项
            val placeable = measurable.measure(constraints)
            placeable
        }
        //2、记录子项每次放置的位置
        var xPosition = 0
        var yPosition = 0
        var maxHeight = 0
        val totalY = mutableMapOf<Int, Int>()
        layout(constraints.maxWidth, constraints.maxHeight) {
            //3、使用layout函数对每一个子项进行布局
            placeables.forEach {
                if (xPosition + it.width + horizontalSpace > constraints.maxWidth) {
                    //一行已经放不下，需要换行
                    totalY[yPosition] = maxHeight
                    yPosition++
                    maxHeight = 0
                    xPosition = 0
                }

                val y = if (totalY.values.toList().isEmpty()) {
                    0
                } else {
                    totalY.values.toList().reduce { acc, box -> acc + box }
                }
                it.placeRelative(
                    x = xPosition,
                    y = y
                )
                //4、每次放置子项后，更新下一个子项放置的位置
                xPosition += it.width + horizontalSpace
                maxHeight = Math.max(maxHeight, it.height)
            }
        }
    }
}


