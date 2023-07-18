package com.gx.note

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gx.note.ui.theme.body2
import com.gx.note.ui.theme.caption

@Composable
fun BaseCircleLoading(modifier: Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        )
    )
    Box(
        modifier = modifier
            .rotate(angle)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.mipmap.ic_loading),
            contentDescription = null
        )
    }
}


@Composable
@Preview(backgroundColor = 0xffffff, showBackground = true)
fun LoadMorePlaceholder() =
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BaseCircleLoading(modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                stringResource(R.string.loading_more),
                style = caption,
                color = baseWhite()
            )
        }
    }

@Composable
@Preview(backgroundColor = 0xffffff, showBackground = true)
fun LoadCompletePlaceholder() =
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                stringResource(R.string.loading_more),
                style = caption,
                color = baseWhite()
            )
        }
    }

@Composable
@Preview(backgroundColor = 0xffffff, showBackground = true)
fun PageLoadingIndicator(
    modifier: Modifier = Modifier,
    messageId: Int = R.string.loading_more
) {
    Column(modifier) {
        BaseCircleLoading(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = messageId),
            style = body2,
            color = baseWhite().copy(alpha = 0.6f)
        )
    }
}
