package br.com.pucpr.tasklistmanager.model.repository

import br.com.pucpr.tasklistmanager.model.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao) : TaskRepository {
        return TaskRepository(taskDao)
    }
}