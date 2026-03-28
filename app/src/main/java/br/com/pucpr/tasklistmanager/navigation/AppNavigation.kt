package br.com.pucpr.tasklistmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.pucpr.tasklistmanager.model.repository.TaskRepository
import br.com.pucpr.tasklistmanager.view.HomeScreen
import br.com.pucpr.tasklistmanager.view.StatisticsScreen
import br.com.pucpr.tasklistmanager.view.TaskDetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(
                onTaskClick = { task ->
                    navController.navigate(
                        Screen.TaskDetail.createRoute(task.id)
                    )
                },
                onStatisticsClick = {
                    navController.navigate(
                        Screen.Statistics.route
                    )
                }
            )
        }
        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.LongType })
        ) { backStackEntry ->
            TaskDetailScreen(
                onBackPage = { navController.popBackStack() }
            )
        }
        composable(Screen.Statistics.route) {
            StatisticsScreen(
                onBackPage = { navController.popBackStack() },
            )
        }
    }
}