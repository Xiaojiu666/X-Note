package com.gx.note

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType.Companion.Move
import androidx.compose.ui.input.pointer.PointerEventType.Companion.Press
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gx.note.screenshot.ScreenshotBox
import com.gx.note.screenshot.rememberScreenshotState
import kotlinx.coroutines.launch


@Composable
fun DrawTablet() {
    // 记录每一次move的路线
    var linepath by remember { mutableStateOf(Offset.Zero) }
    // 记录path对象
    val path by remember { mutableStateOf(Path()) }
    val screenshotState = rememberScreenshotState()
    val context =  LocalContext.current
    val scope = rememberCoroutineScope()
    ScreenshotBox(screenshotState = screenshotState,
        modifier =   Modifier
            .statusBarsPadding().
            navigationBarsPadding()
            .fillMaxSize()
            .pointerInput(Unit) {
            awaitEachGesture{
                while (true) {
                    val event = awaitPointerEvent()
                    when (event.type) {
                        //按住时，更新起始点
                        Press -> {
                            path.moveTo(event.changes.first().position.x, event.changes.first().position.y)
                        }
                        //移动时，更新起始点 移动时，记录路径path
                        Move->{
                            linepath = event.changes.first().position
                        }
                    }
                }
            }
        }){
            Canvas(modifier = Modifier.fillMaxSize()) {
                //重组新路线
                path.lineTo(linepath.x, linepath.y)
                drawPath(  color = Color.Red,
                    path = path,
                    style = Stroke(width = 10F))
            }
            Row(modifier = Modifier.align(Alignment.BottomCenter)) {
                Button(modifier = Modifier.weight(1f).padding(8.dp), onClick = {
                    linepath = Offset.Zero
                    path.reset()
                }){
                    Text(text = "Clear")
                }
                Button(modifier = Modifier.weight(1f).padding(8.dp), onClick = {
                    scope.launch {
                        screenshotState.capture()
                        println("liveScreenshotFlow value "  )
                        screenshotState.bitmap?.insertImageToImage(context)
                    }
                }){
                    Text(text = "Screenshot")
                }
            }
        }
    }


fun Bitmap.insertImageToImage(context:Context):Boolean{
    println("saveUri ${this.toString()}"  )
    val bitmap = this.copy(this.config,true)
    val saveUri  = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        ContentValues()
    ) ?: return false
    println("saveUri $saveUri"  )
    val outPutStream = context.contentResolver.openOutputStream(saveUri!!)
    outPutStream?.use {
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG,90,it)
        }catch (e: java.lang.Exception){
            println("Exception $e"  )
            return false
        }
    }
    return true
}

