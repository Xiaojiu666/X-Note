package com.gx.note

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun BaseScaffold(
    scaffoldState: ScaffoldState,
    baseToolbar: @Composable () -> Unit = { BaseBackToolbar(title = "你好") },
    content: @Composable () -> Unit = { }
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .systemBarsPadding(),
        snackbarHost = { snackbarHostState ->

        },
        scaffoldState = scaffoldState,
        topBar = { baseToolbar() },
    ) {
        content()
    }
}