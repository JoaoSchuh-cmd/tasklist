package br.com.pucpr.tasklistmanager.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.pucpr.tasklistmanager.model.entity.TaskEntity
import br.com.pucpr.tasklistmanager.model.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val taskId: Long = checkNotNull(savedStateHandle["taskId"])
    val task: Flow<TaskEntity?> = repository.getById(taskId)

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
}