package com.mercedesbenz.core_ui.screenshot

import android.graphics.Bitmap

sealed class ImageResult {
    object Initial : ImageResult()
    data class Error(val exception: Exception) : ImageResult()
    data class Success(val data: Bitmap) : ImageResult()
}
