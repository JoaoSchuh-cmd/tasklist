package br.com.pucpr.tasklistmanager.model.repository

import br.com.pucpr.tasklistmanager.model.dao.TaskDao
import br.com.pucpr.tasklistmanager.model.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val dao: TaskDao
) {
    val tasks: Flow<List<TaskEntity>>
        get() = dao.getAllTasks()

    suspend fun insert(task: TaskEntity) {
        dao.insert(task)
    }

    suspend fun update(task: TaskEntity) {
        dao.update(task)
    }

    fun getById(id: Long): Flow<TaskEntity?> {
        return dao.getTaskById(id)
    }

    fun getAllFinishedTasks(): Flow<List<TaskEntity>> {
        return dao.getAllFinishedTasks()
    }

    fun getAllNotFinishedTasks(): Flow<List<TaskEntity>> {
        return dao.getAllNotFinishedTasks()
    }

    suspend fun delete(task: TaskEntity) {
        dao.delete(task)
    }
}