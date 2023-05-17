package com.gx.note.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun MyAnimatedVisibility() {

    var editable by remember { mutableStateOf(true) }

    Column {

        AnimatedVisibility(
            visible = editable, modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Box {
                Text(text = "Edit", modifier = Modifier.align(Alignment.BottomStart))
            }
        }

        Text(text = "点我", modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .clickable {
                editable = !editable
            })
    }

}
