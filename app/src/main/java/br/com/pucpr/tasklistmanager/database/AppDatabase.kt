package br.com.pucpr.tasklistmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.pucpr.tasklistmanager.model.dao.TaskDao
import br.com.pucpr.tasklistmanager.model.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}