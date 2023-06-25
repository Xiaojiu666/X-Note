package com.gx.note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gx.note.ui.LocalToolbarColor
import com.gx.note.ui.theme.subtitle2Bold

val TOOLBAR_HEIGHT = 56.dp

@Composable
fun BaseToolbar(
    startContent: @Composable BoxScope.() -> Unit = {},
    centerContent: @Composable BoxScope.() -> Unit = {},
    endContent: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        Modifier
            .background(toolBarColor())
            .statusBarsPadding()
            .padding(11.dp, 0.dp, 16.dp, 0.dp)
            .fillMaxWidth()
            .height(TOOLBAR_HEIGHT)
    ) {
        Box(
            Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight()
        ) {
            startContent(this)
        }
        Box(
            Modifier
                .align(Alignment.Center)
                .fillMaxHeight()
        ) {
            centerContent(this)
        }
        Box(
            Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
        ) {
            endContent(this)
        }
    }
}

//}

@Composable
fun BaseBackToolbar(
    title: String,
    leftIconId: Int? = R.drawable.ic_back_arrow,
    rightIconId: Int? = null,
    onLeftIconClick: () -> Unit = {},
    onRightIconClick: () -> Unit = {}
) {
    BaseToolbar({
        if (leftIconId != null) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterStart)
                    .clickable {
                        onLeftIconClick()
                    },
                painter = painterResource(leftIconId),
                contentDescription = null,
            )
        }
    }, {
        Text(
            modifier = Modifier
                .padding(start = 35.dp, end = 35.dp)
                .align(Alignment.Center),
            text = title,
            maxLines = 1,
            textAlign = TextAlign.Center,
            style = subtitle2Bold,
            color = baseWhite()
        )
    }, {
        if (rightIconId != null) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        onRightIconClick()
                    },
                painter = painterResource(rightIconId),
                contentDescription = null,
            )
        }
    })
}

@Composable
@Preview
fun ToolbarPreview() {
    BaseBackToolbar("这是一个标题")
}
