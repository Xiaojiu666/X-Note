package com.gx.note.plan.create

import android.icu.util.Measure
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material3.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Switch
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gx.note.BaseBackToolbar
import com.gx.note.R
import com.gx.note.TOOLBAR_HEIGHT
import com.gx.note.ui.theme.body1
import com.gx.note.ui.theme.body2
import com.gx.note.ui.theme.body3
import com.gx.note.ui.theme.colorPrimary
import com.gx.note.ui.theme.colorSecondary
import com.gx.note.ui.theme.colorTertiary
import com.gx.note.ui.timeWheel.TimeWheelPicker
import kotlinx.coroutines.launch


@Composable
fun CreateTodoRoute() {

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateTodoPage(onBackClick: () -> Unit) {
    var type by remember {
        mutableStateOf(1)
    }
    val sheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding()
        .background(colorPrimary()),
        topBar = {
            BaseBackToolbar(
                title = stringResource(id = R.string.todo_create),
                onLeftIconClick = onBackClick,
            )
        },
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        sheetContent = {
            sheetContent(type = type)
        }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 12.dp)
        ) {
            var name by remember {
                mutableStateOf(TextFieldValue("准备做什么？"))
            }
            TodoName(name) {
                name = it
            }

            TodoPriority()

            ItemTodoSetting("开始", "2023年8月30日 11:00", false) {

            }

            ItemTodoSetting("结束", "2023年8月30日 11:00", false) {

            }

            ItemTodoSetting("重复周期", "一次性", true) {
                scope.launch {
                    type = 1
                    sheetState.expand()
                }
            }
            ItemSettingAdd(leftName = "次数") {

            }

            ItemTodoSetting("提醒", "11:00", true, Modifier.padding(top = 32.dp)) {
                scope.launch {
                    type = 2
                    sheetState.expand()
                }
            }

            ItemTodoSetting("提醒方式", "闹铃提醒", true) {

            }


        }
    }
}

@Composable
fun sheetContent(type: Int) = when (type) {
    1 -> {
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        Column(
            Modifier
                .fillMaxWidth()
                .height(screenHeight - TOOLBAR_HEIGHT)
        ) {
            SheetTitle("")

            Text(
                text = "重复周期",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp),
                style = body1,
            )
            ItemSettingRadio(
                "一次性日程", modifier = Modifier.padding(horizontal = 8.dp)
            ) {

            }

            ItemSettingRadio(
                "每天", modifier = Modifier.padding(horizontal = 8.dp)
            ) {

            }
            ItemSettingRadio(
                "周期选择", modifier = Modifier.padding(horizontal = 8.dp)
            ) {

            }
            AnimatedVisibility(visible = true) {
                Column() {
                    ItemSettingCheckBox(leftName = "周一") {

                    }
                    ItemSettingCheckBox(leftName = "周二") {

                    }
                    ItemSettingCheckBox(leftName = "周三") {

                    }
                    ItemSettingCheckBox(leftName = "周四") {

                    }
                    ItemSettingCheckBox(leftName = "周五") {

                    }
                    ItemSettingCheckBox(leftName = "周六") {

                    }
                    ItemSettingCheckBox(leftName = "周日") {

                    }

                }
            }
            Text(
                text = "重复次数", modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )

            ItemSettingAdd(
                "周期选择", modifier = Modifier.padding(horizontal = 8.dp)
            ) {

            }
        }
    }
    else -> {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {

        }
    }
}

@Composable
fun TodoName(textFieldValue: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 4.dp)
            .background(shape = RoundedCornerShape(10.dp), color = colorTertiary())
            .padding(8.dp),
        value = textFieldValue,
        onValueChange = onValueChange
    )
}

@Composable
fun TodoStartTime() {
    var visible by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(
        visible = visible
    ) {
        TimeWheelPicker()
    }
}

@Composable
fun TodoPriority() {
    RadioGroup() {
        println("select $it")
    }
}

@Composable
fun RadioGroup(
    selected: (Int) -> Unit
) {
    var checkBox by remember {
        mutableStateOf(0)
    }
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        TodoLevel.values().forEachIndexed { index, option ->
            Row(modifier = Modifier.padding(8.dp)) {
                RoundCheckBox(
                    Modifier.align(CenterVertically),
                    option.checkBox,
                    option.unCheckBox,
                    checkBox == index
                ) {
                    if (it) {
                        checkBox = index
                        selected(index)
                    }
                }
                Text(text = option.levelName)
            }
        }
    }
}

@Composable
fun RoundCheckBox(
    modifier: Modifier = Modifier,
    checkRes: Int,
    unCheckRes: Int,
    checked: Boolean,
    size: Dp = 16.dp,
    onClickable: (Boolean) -> Unit
) {
    val res = if (checked) checkRes else unCheckRes
    Image(modifier = Modifier
        .clickable {
            onClickable(true)
        }
        .then(modifier)
        .size(size),
        painter = painterResource(id = res),
        contentDescription = "")
}

@Composable
fun ItemSettingAdd(
    leftName: String, modifier: Modifier = Modifier, onClickable: () -> Unit
) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = "1"))
    }
    Box(
        modifier = Modifier
            .then(modifier)
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = leftName,
            color = Color.Black,
            style = body1,
        )

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            BasicTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .width(16.dp),
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                }
            )
            Text(style = body3, text = "次", color = colorTertiary())
        }

    }
}


@Composable
fun ItemSettingRadio(
    leftName: String, modifier: Modifier = Modifier, onClickable: () -> Unit
) {
    var selected by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier
        .then(modifier)
        .fillMaxWidth()
        .clickable {
            selected = !selected
            onClickable()
        }) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = leftName,
            color = Color.Black,
            style = body2,
        )
        RadioButton(modifier = Modifier.align(Alignment.CenterEnd),
            selected = selected,
            onClick = {
                selected = !selected
            })
    }
}


@Composable
fun ItemSettingCheckBox(
    leftName: String, modifier: Modifier = Modifier, onClickable: () -> Unit
) {
    var checked by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier
        .then(modifier)
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
        .clickable {
            onClickable()
            checked = !checked
        }) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            fontSize = 14.sp,
            text = leftName,
            color = Color.Black
        )
        Checkbox(modifier = Modifier.align(Alignment.CenterEnd),
            checked = checked,
            onCheckedChange = {
                checked = it
            })

    }
}


@Composable
fun ItemTodoSetting(
    leftName: String,
    rightName: String,
    rightIcon: Boolean = true,
    modifier: Modifier = Modifier,
    onClickable: () -> Unit
) {
    Box(modifier = Modifier
        .then(modifier)
        .fillMaxWidth()
        .clickable { onClickable() }
        .padding(8.dp)) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            fontSize = 16.sp,
            text = leftName,
            color = Color.Black
        )

        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            val paddingRight = if (rightIcon) 0.dp else 8.dp
            Text(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(end = paddingRight),
                fontSize = 14.sp,
                text = rightName,
                color = Color.Gray
            )
            if (rightIcon) {
                Image(
                    modifier = Modifier.align(CenterVertically),
                    painter = painterResource(id = R.drawable.ic_right_arrow),
                    contentDescription = null
                )
            }
        }
    }
}


@Preview
@Composable
fun preCreateTodoPage() {
//    ItemTodoSetting("开始", "2023年8月30日 11:00", true) {
//
//    }
}

enum class TodoLevel(val levelName: String, val checkBox: Int, val unCheckBox: Int, val id: Int) {
    EMERGENT("紧急", R.mipmap.check_circle_emergent, R.mipmap.check_circle_emergent_un, 1), IMPORTANT(
        "重要", R.mipmap.check_circle_important, R.mipmap.check_circle_important_un, 2
    ),
    NORMAL("普通", R.mipmap.check_circle_normal, R.mipmap.check_circle_normal_un, 3), LOW(
        "低级", R.mipmap.check_circle_low, R.mipmap.check_circle_low_un, 4
    ),
}


@Composable
fun SheetTitle(titleName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Image(
            painterResource(id = R.mipmap.icon_drag_handler),
            contentDescription = "drag handler",
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(3.dp)
                .width(25.dp)
                .align(Alignment.TopCenter)
        )

        Text(
            text = titleName,
            modifier = Modifier.align(Alignment.Center),
            style = body1,
            color = colorSecondary()
        )
    }
}