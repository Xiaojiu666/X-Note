package com.mercedesbenz.core_ui.screenshot

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView
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
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    val composeView = remember {
        ComposeView(context)
    }

    DisposableEffect(Unit) {
        screenshotState.callback = {
            kotlin.runCatching {
                val result = composeView.drawToBitmap()
                screenshotState.bitmapState.value = result
                screenshotState.imageState.value = ImageResult.Success(result)
            }.onFailure {
                screenshotState.imageState.value = ImageResult.Error(Exception(it.message))
            }
        }
        onDispose {
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

    val density = LocalDensity.current

    AndroidView(
        modifier = modifier,
        factory = {
            ScrollView(it).apply {
                overScrollMode = View.OVER_SCROLL_NEVER
                (composeView.parent as? ViewGroup)?.removeView(composeView)
                addView(composeView)
            }
        },
        update = {
            composeView.setContent {
                CompositionLocalProvider(
                    LocalDensity provides density
                ) {
                    content()
                }
            }
        }
    )
}
