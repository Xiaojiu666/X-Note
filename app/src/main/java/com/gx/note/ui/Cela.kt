package com.gx.note.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun ScaffoldMenuSamples() {
    val app = LocalContext.current.applicationContext
    //设置scaffold的状态
    val scaffoldState = rememberScaffoldState()
    //设置可以开启线程的对象
    val scope = rememberCoroutineScope()
    Divider(thickness = 0.5.dp, color = Color.Red)
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(navigationIcon = {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        //点击滑出菜单选项
                        Toast.makeText(app, "弹出菜单栏", Toast.LENGTH_SHORT).show()
                        scope.launch { //打开策划菜单的方法
                            scaffoldState.drawerState.open()
                        }
                    })
            }, title = {
                Text(text = "点击左边滑出菜单")
            }, actions = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "删除",
                    Modifier
                        .padding(10.dp, 0.dp, 10.dp, 0.dp)
                        .clickable {
                            Toast
                                .makeText(app, "关闭菜单栏", Toast.LENGTH_SHORT)
                                .show()
                            scope.launch {
                                //关闭策划菜单的方法
//                                scaffoldState.drawerState.close()
                                //显示snackBar类似Toast
                                scaffoldState.snackbarHostState.showSnackbar(
                                    "删除",
                                    actionLabel = "完成",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }, tint = Color.White
                )
            })
        },
        modifier = Modifier.height(150.dp),
        drawerContent = {
            Text(text = "我是侧滑菜单内容")
        },
        //整个侧滑菜单背景色
        drawerBackgroundColor = Color.Blue,
        //侧滑菜单里面的内容的颜色，包括文字颜色等
        drawerContentColor = Color.Red,
//        drawerScrimColor = Color.Transparent
        //侧滑菜单未充满屏幕的颜色，一般默认是透明色，也可以自己设置其它颜色
        drawerScrimColor = Color.Green
    ) {

    }
}

@Preview
@Composable
fun ScaffoldMenuSamplesPreview() {
    ScaffoldMenuSamples()
}
