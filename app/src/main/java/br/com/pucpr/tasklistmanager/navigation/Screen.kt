package br.com.pucpr.tasklistmanager.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object TaskDetail : Screen("taskDetail/{taskId}") {
        fun createRoute(taskId: Long) = "taskDetail/${taskId}"
    }

    object Statistics : Screen("statistics")
}