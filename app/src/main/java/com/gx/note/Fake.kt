package com.gx.note

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectDrag
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import kotlin.math.roundToInt

fun main() {
//    convert<String, Int>(null)
    null as Int
}

fun <T, O> convert(string: T?): O {
    return string as O
}

fun composeAge(newAge: Int) {
//    newAge ++
//    print("composeAge $age")
}

@Composable
fun CustomView() {
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    val points = remember {
        mutableStateListOf(Offset.Zero)
    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        /* Respond to drag start */
                    },
                    onDragEnd = {
                        /* Respond to drag end */
                    },
                    onDragCancel = {
                        /* Respond to drag cancel */
                    },
                    onDrag = { change, dragAmount ->
                        points.add(dragAmount)
                        Log.e("Canvas", "dragAmount points${dragAmount.x} _ ${dragAmount.y}")
                        offsetX.value += dragAmount.x
                        offsetY.value += dragAmount.y
                        points.add(Offset(offsetX.value,offsetY.value))
//                        change.consumeAllChanges()
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            Log.e("Canvas", "Canvas points${points.toString()}")
            drawPoints(points, PointMode.Points, Color.White)
        }

//                Text (
//                text = "Drag me!",
//            modifier = Modifier.offset {
//                IntOffset(
//                    offsetX.value.roundToInt(),
//                    offsetY.value.roundToInt()
//                )
//            }
//        )
    }
}


@Composable
fun drawPain() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit)
            {
                val down = awaitPointerEventScope {
                    Log.e(
                        "onDrag",
                        "changes ${currentEvent.changes} "
                    )
                }
                awaitPointerEventScope {
//                    drag(down.id) {
//                        Log.e("onDrag", "onDrag $it  dragAmount${it.positionChange()}")
//                        it.consume()
//                    }
                }
            }) {

    }
}

//@Composable
//private fun HorizontalReorderList(
//    vm: ReorderListViewModel,
//    modifier: Modifier = Modifier,
//) {
//    val state = rememberReorderableLazyListState(onMove = vm::moveCat)
//    LazyRow(
//        state = state.listState,
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        modifier = modifier
//            .then(Modifier.reorderable(state))
//            .detectReorderAfterLongPress(state),
//    ) {
//        items(vm.cats, { item -> item.key }) { item ->
//            ReorderableItem(state, item.key) { dragging ->
//                val scale = animateFloatAsState(if (dragging) 1.1f else 1.0f)
//                val elevation = if (dragging) 8.dp else 0.dp
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier
//                        .size(100.dp)
//                        .scale(scale.value)
//                        .shadow(elevation, RoundedCornerShape(24.dp))
//                        .clip(RoundedCornerShape(24.dp))
//                        .background(Color.Red)
//                ) {
//                    Text(item.title)
//                }
//            }
//        }
//    }
//}
