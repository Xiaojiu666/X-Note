package com.gx.note.ui

import android.util.Log
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.gx.note.R


@Composable
fun RichEditor() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        EditorContentView(Modifier.align(Alignment.Center))

        EditorBottomView()
    }

}


@Composable
fun EditorContentView(modifier: Modifier = Modifier) {
    val hint = stringResource(id = R.string.editor_hint)
    var value by remember {
        mutableStateOf(TextFieldValue(text = hint))
    }
    BasicTextField(modifier = modifier, value = value, onValueChange = {
        value = it
    })
}


@Composable
fun BoxScope.EditorBottomView() {
    val anim = remember {
        TargetBasedAnimation(
            animationSpec = tween(10000),
            typeConverter = Float.VectorConverter,
            initialValue = 0f,
            targetValue = 1000f
        )
    }
    var paddingBottom by remember { mutableStateOf(0L) }
    var animationValue by remember { mutableStateOf(0F) }

    LaunchedEffect(anim) {
        val startTime = withFrameNanos { it }
        do {
            paddingBottom = withFrameNanos { it } - startTime
            animationValue = anim.getValueFromNanos(paddingBottom)
//            Log.e("LaunchedEffect", "paddingBottom $paddingBottom , animationValue$animationValue")
        } while (true)
    }
    Box(
        modifier = Modifier
            .padding(bottom = animationValue.dp)
            .align(Alignment.Center)
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Magenta)
    )
}