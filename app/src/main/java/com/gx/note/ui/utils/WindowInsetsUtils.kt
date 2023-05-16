package com.gx.note.ui.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

@Composable
fun navigationBarHeight() = with(LocalDensity.current) {
    WindowInsets.navigationBars.getBottom(LocalDensity.current).toDp()
}

@Composable
fun statusBarHeight() = with(LocalDensity.current) {
    WindowInsets.statusBars.getTop(LocalDensity.current).toDp()
}
