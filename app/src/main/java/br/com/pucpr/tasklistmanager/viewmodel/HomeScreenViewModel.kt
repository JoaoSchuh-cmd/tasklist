package br.com.pucpr.tasklistmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.pucpr.tasklistmanager.model.entity.TaskEntity
import br.com.pucpr.tasklistmanager.model.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    val taskList = repository.tasks
    fun addTask(name: String, description: String = "") {
        if (name.isNotBlank() && name.isNotEmpty()) {
            viewModelScope.launch {
                repository.insert(
                    TaskEntity(
                        name = name,
                        description = description,
                        isCompleted = false
                    )
                )
            }
        }
    }

    fun editTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun removeTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun toggleTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.update(
                task.copy(isCompleted = !task.isCompleted)
            )
        }
    }
}