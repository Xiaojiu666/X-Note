package com.gx.note

import android.R
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gx.note.ui.LocalGlobalNavController
import com.gx.note.ui.RouteConfig
import com.gx.note.ui.utils.KeyboardHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeNav()
//            XNoteTheme {
//                val viewModel: DiaryHomeViewModel by viewModels()
//                DiaryHomePage(viewModel)
////                val viewModel: DiaryEditViewModel by viewModels()
////                DiaryEditorPage(viewModel)
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
                    DiaryHomePage(viewModel)
                }

                composable(RouteConfig.ROUTE_DIARY_HOME) {
                    val viewModel: DiaryEditViewModel = hiltViewModel()
                    DiaryEditorPage(viewModel, onBackClick = {
                        navController.popBackStack()
                    })
                }

            }
        }
    }
}