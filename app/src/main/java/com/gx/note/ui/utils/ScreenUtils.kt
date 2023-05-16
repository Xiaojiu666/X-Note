package com.gx.note.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun screenHeight() = LocalConfiguration.current.screenHeightDp.dp

@Composable
fun screenWidth() = LocalConfiguration.current.screenWidthDp.dp