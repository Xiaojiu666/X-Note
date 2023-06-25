package com.gx.note.ui

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalGlobalNavController = staticCompositionLocalOf<NavHostController?> {
    null
}
object RouteConfig {
    /**
     * 页面1路由
     */
    const val ROUTE_HOME_PAGE = "HOME_PAGE"


    /**
     * 页面1路由
     */
    const val ROUTE_DIARY_HOME = "DIARY_HOME"
}