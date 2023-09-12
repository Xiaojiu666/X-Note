package com.gx.note

import android.R
import android.app.Activity
import android.content.ContextWrapper
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gx.note.diary.editor.DiaryEditorViewModel
import com.gx.note.diary.editor.DiaryEditorPage
import com.gx.note.diary.editor.diaryEditorViewModel
import com.gx.note.diary.list.DiaryListRoute
import com.gx.note.diary.list.DiaryListViewModel
import com.gx.note.plan.PlanHomePage
import com.gx.note.plan.PlanHomeRoute
import com.gx.note.plan.PlanHomeViewModel
import com.gx.note.plan.create.CreateTodoPage
import com.gx.note.plan.create.CreateTodoRoute
import com.gx.note.plan.create.CreateTodoViewModel
import com.gx.note.recommend.DiaryHomeRoute
import com.gx.note.recommend.RecommendHomeViewModel
import com.gx.note.ui.LocalGlobalNavController
import com.gx.note.ui.RouteConfig
import com.gx.note.ui.theme.XNoteTheme
import com.gx.note.ui.timeWheel.WheelPicker
import com.gx.note.ui.utils.KeyboardHandler
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import java.util.jar.Attributes.Name


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XNoteTheme {
                HomeNav()
            }
        }
        KeyboardHandler(findViewById(R.id.content)).handleKeyboard()
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    @Composable
    fun OnResumeView() {
        var time by remember {
            mutableStateOf(System.currentTimeMillis())
        }
        TextName(time.toString()) {
            time = System.currentTimeMillis()
        }
//        println("OnResumeView init")
//        LaunchedEffect(Unit) {
//            println("OnResumeView LaunchedEffect onResume")
//        }
//        val lifecycle = LocalLifecycleOwner.current.lifecycle
//        DisposableEffect(lifecycle) {
//            val observer = LifecycleEventObserver { _, event ->
//                if (event == Lifecycle.Event.ON_RESUME) {
//                    println("OnResumeView Lifecycle onResume")
//                }
//            }
//            lifecycle.addObserver(observer)
//
//            onDispose {
//                lifecycle.removeObserver(observer)
//            }
//        }
    }

    @Composable
    fun TextName(name: String, onClick: () -> Unit) {
        LaunchedEffect(name) {
            Log.e("TextName", "LaunchedEffect  ${System.currentTimeMillis()}")
        }

        Text(
            modifier = Modifier.clickable { onClick() },
            text = name,
            fontSize = 60.sp,
            color = Color.Red
        )
    }

    @Composable
    fun HomeNav() {
        val navController = rememberNavController()
        CompositionLocalProvider(LocalGlobalNavController provides navController) {
            NavHost(navController = navController, startDestination = RouteConfig.ROUTE_HOME_PAGE) {

                composable(route = RouteConfig.ROUTE_HOME_PAGE) {
                    val viewModel: RecommendHomeViewModel = hiltViewModel()
                    DiaryHomeRoute(viewModel)
                }

                composable(
                    RouteConfig.ROUTE_DIARY_HOME + "?diaryId={diaryId}", arguments = listOf(
                        navArgument("diaryId") { defaultValue = -1 },
                    )
                ) {
                    val diaryId = it.arguments?.getInt("diaryId") ?: -1
                    val viewModel: DiaryEditorViewModel = diaryEditorViewModel(diaryId, it)
                    DiaryEditorPage(viewModel, onBackClick = {
                        navController.popBackStack()
                    })
                }

                composable(RouteConfig.ROUTE_DIARY_LIST_PAGE) {
                    val viewModel: DiaryListViewModel = hiltViewModel()
                    DiaryListRoute(viewModel)
                }
                composable(RouteConfig.ROUTE_PLAN_HOME_PAGE) {
                    val viewModel: PlanHomeViewModel = hiltViewModel()
                    PlanHomeRoute(viewModel)
                }
                composable(RouteConfig.ROUTE_TODO_CREATE_PAGE) {
                    val createTodoViewModel: CreateTodoViewModel = hiltViewModel()
                    CreateTodoRoute(createTodoViewModel,navController)
                }
            }
        }
    }
}
