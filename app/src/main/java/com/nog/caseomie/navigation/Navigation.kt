package com.nog.caseomie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nog.caseomie.ui.screens.HomeScreen
import com.nog.caseomie.ui.screens.ReportSalesScreen
import com.nog.caseomie.ui.screens.SalesScreen


object NavigationRoutes {
    const val HOME_SCREEN: String = "home_screen"
    const val SALE_SCREEN: String = "sale_screen"
    const val REPORT_SALE_SCREEN: String = "report_sales_screen"
}

@Composable
fun CaseOmieApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoutes.HOME_SCREEN) {
        composable(NavigationRoutes.HOME_SCREEN) {
            HomeScreen(navController)
        }
        composable(NavigationRoutes.SALE_SCREEN) {
            SalesScreen(navController)
        }
        composable(NavigationRoutes.REPORT_SALE_SCREEN) {
            ReportSalesScreen(navController)
        }
    }
}
