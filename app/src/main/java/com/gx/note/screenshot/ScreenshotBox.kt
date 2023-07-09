package com.gx.note.screenshot

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.drawToBitmap
import java.lang.Exception

/**
 * A composable that gets screenshot of Composable that is in [content].
 * @param screenshotState state of screenshot that contains [Bitmap].
 * @param content Composable that will be captured to bitmap on action or periodically. content can't use hardware
 */
@Composable
fun ScreenshotBox(
    modifier: Modifier = Modifier,
    screenshotState: ScreenshotState,
    content: @Composable BoxScope.() -> Unit,
) {
    val context = LocalContext.current
    val view = LocalView.current

    var composeView by remember { mutableStateOf<Rect?>(null) }

    DisposableEffect(Unit) {
        screenshotState.callback = {
           runCatching {
                composeView?.let {
                    if (it.width == 0f || it.height == 0f){
                        return@let
                    }
                    view.screenshot(it){
                        screenshotState.imageState.value = it
                        if (it is ImageResult.Success){
                            println("screenshot imageState Success")
                            screenshotState.bitmapState.value = it.data
                        }
                    }
                }
            }.onFailure {
                screenshotState.imageState.value = ImageResult.Error(Exception(it.message))
            }
        }
        onDispose {
            println("screenshot onDispose ")
            val bmp = screenshotState.bitmapState.value
            bmp?.apply {
                if (!isRecycled) {
                    recycle()
                }
            }
            screenshotState.bitmapState.value = null
            screenshotState.callback = null
        }
    }

    Box(modifier = modifier.onGloballyPositioned {
        composeView =
            it.boundsInWindow()
    }){
        content()
    }

//    val density = LocalDensity.current

//    AndroidView(
//        modifier = modifier,
//        factory = {
//            ScrollView(it).apply {
//                overScrollMode = View.OVER_SCROLL_NEVER
//                (composeView.parent as? ViewGroup)?.removeView(composeView)
//                addView(composeView)
//            }
//        },
//        update = {
//            composeView.setContent {
//                CompositionLocalProvider(
//                    LocalDensity provides density
//                ) {
//                    content()
//                }
//            }
//        }
//    )
}
