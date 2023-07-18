package com.gx.note

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gx.note.diary.DiaryListRoute
import com.gx.note.diary.DiaryListViewModel
import com.gx.note.home.HomePage
import com.gx.note.ui.LocalGlobalNavController
import com.gx.note.ui.RouteConfig
import com.gx.note.ui.theme.XNoteTheme
import com.gx.note.ui.utils.KeyboardHandler
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeNav()
//            XNoteTheme {
//
//            }

        }
        KeyboardHandler(findViewById(R.id.content)).handleKeyboard()
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }


    @Composable
    fun HomeNav() {
        val navController = rememberNavController()
        CompositionLocalProvider(LocalGlobalNavController provides navController) {
            NavHost(navController = navController, startDestination = RouteConfig.ROUTE_HOME_PAGE) {

                composable(RouteConfig.ROUTE_HOME_PAGE) {
                    val viewModel: DiaryHomeViewModel = hiltViewModel()
                    DiaryHomeRoute(viewModel)
                }

                composable(RouteConfig.ROUTE_DIARY_HOME) {
                    val viewModel: DiaryEditViewModel = hiltViewModel()
                    DiaryEditorPage(viewModel, onBackClick = {
                        navController.popBackStack()
                    })
                }

                composable(RouteConfig.ROUTE_DIARY_LIST_PAGE) {
                    val viewModel: DiaryListViewModel = hiltViewModel()
                    DiaryListRoute(viewModel)
                }

            }
        }
    }
}