package br.com.pucpr.tasklistmanager.model

data class Task(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val description: String,
    val isCompleted: Boolean = false,
)
