package com.gx.note.screenshot

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Create a State of screenshot of composable that is used with that is kept on each recomposition.
 * @param delayInMillis delay before each screenshot
 * if [ScreenshotState.liveScreenshotFlow] is collected.
 */
@Composable
fun rememberScreenshotState(delayInMillis: Long = 20) = remember {
    ScreenshotState(delayInMillis)
}

/**
 * State of screenshot of composable that is used with.
 * @param timeInMillis delay before each screenshot if [liveScreenshotFlow] is collected.
 */
open class ScreenshotState internal constructor(
    private val timeInMillis: Long = 20,
) {
    val imageState = mutableStateOf<ImageResult>(ImageResult.Initial)

    val bitmapState = mutableStateOf<Bitmap?>(null)

    internal var callback: (suspend () -> Unit)? = null

    /**
     * Captures current state of Composables inside [ScreenshotBox]
     */
    suspend fun capture() {
        callback?.invoke()
    }

    val liveScreenshotFlow = flow {
        while (true) {
            println("liveScreenshotFlow ${bitmapState.value}"  )
            callback?.invoke()
            delay(timeInMillis)
            println("bitmapState emit ${bitmapState.value}"  )
            bitmapState.value?.let {
                println("bitmapState emit $it"  )
                emit(it)
            }
        }
    }.map {
            it.asImageBitmap()
        }
        .flowOn(Dispatchers.Default)

    val bitmap: Bitmap?
        get() = bitmapState.value
//
    val imageBitmap: ImageBitmap?
        get() = bitmap?.asImageBitmap()
}
