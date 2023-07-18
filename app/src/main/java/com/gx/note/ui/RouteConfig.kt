package com.gx.note.ui

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalGlobalNavController = staticCompositionLocalOf<NavHostController?> {
    null
}
object RouteConfig {
    const val ROUTE_HOME_PAGE = "HOME_PAGE"
    const val ROUTE_DIARY_HOME = "DIARY_HOME"
    const val ROUTE_DIARY_LIST_PAGE = "ROUTE_DIARY_LIST_PAGE"
}