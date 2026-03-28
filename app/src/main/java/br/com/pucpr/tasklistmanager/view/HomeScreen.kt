package br.com.pucpr.tasklistmanager.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.pucpr.tasklistmanager.model.entity.TaskEntity
import br.com.pucpr.tasklistmanager.ui.theme.Crimson
import br.com.pucpr.tasklistmanager.ui.theme.DarkCyan
import br.com.pucpr.tasklistmanager.ui.theme.LightCyan
import br.com.pucpr.tasklistmanager.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(
    onTaskClick: (TaskEntity) -> Unit,
    onStatisticsClick: () -> Unit
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()

    val tasks by viewModel.taskList.collectAsState(initial = emptyList())

    var taskItemValue by remember { mutableStateOf("") }
    var taskBeingEdited by remember { mutableStateOf<TaskEntity?>(null) }
    var taskBeingRemoved by remember { mutableStateOf<TaskEntity?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkCyan)
            .padding(vertical = 40.dp, horizontal = 16.dp)
    ) {
        HomeHeader(onStatisticsClick)

        Spacer(modifier = Modifier.size(16.dp))

        HomeContent(
            onTaskClick = { task ->
                onTaskClick(task)
            },
            onTaskToggle = { task ->
                viewModel.toggleTask(task)
            },
            onInputInfoAddClick = {
                viewModel.addTask(taskItemValue)
            },
            onInputValueChange = { taskItemValue = it },
            onTaskEdit = {
                taskBeingEdited = it
            },
            onTaskRemove = {
                taskBeingRemoved = it
            },
            taskItemValue = taskItemValue,
            tasks = tasks,
        )

        taskBeingEdited?.let { task ->
            EditTaskDialog(
                task = task,
                onDismiss = { taskBeingEdited = null },
                onConfirm = { updatedTask ->
                    viewModel.editTask(updatedTask)
                    taskBeingEdited = null
                }
            )
        }

        taskBeingRemoved?.let { task ->
            RemoveTaskDialog(
                task = task,
                onDismiss = { taskBeingRemoved = null },
                onConfirm = { task ->
                    viewModel.removeTask(task)
                    taskBeingRemoved = null
                },
            )
        }
    }
}

@Composable
fun HomeHeader(
    onStatisticsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "TaskListApp",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            onClick = onStatisticsClick,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color.Black
            )
        ) {
            Icon(
                Icons.Default.Info,
                contentDescription = "Estatísticas",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun HomeContent(
    onInputValueChange: (String) -> Unit,
    onInputInfoAddClick: () -> Unit,
    onTaskToggle: (TaskEntity) -> Unit,
    onTaskClick: (TaskEntity) -> Unit,
    onTaskEdit: (TaskEntity) -> Unit,
    onTaskRemove: (TaskEntity) -> Unit,
    taskItemValue: String,
    tasks: List<TaskEntity>
) {
    InputArea(
        taskItemValue = taskItemValue,
        onValueChange = { onInputValueChange(it) },
        onAddClick = onInputInfoAddClick
    )

    Spacer(modifier = Modifier.size(16.dp))

    LazyColumn {
        items(tasks, key = { it.id }) { task ->
            TaskItem(
                task = task,
                onEdit = { onTaskEdit(it) },
                onRemove = { onTaskRemove(it) },
                onClick = { onTaskClick(task) },
                onToggle = onTaskToggle
            )
        }
    }
}

@Composable
fun InputArea(
    taskItemValue: String,
    onValueChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    val inputValue: String = taskItemValue

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = inputValue,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedIndicatorColor = Color.White,
                selectionColors = TextSelectionColors(
                    handleColor = Color.Black,
                    backgroundColor = Color.Black
                )
            )
        )

        IconButton(
            onClick = onAddClick,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = LightCyan
            )
        ) {
            Icon(
                Icons.Default.AddCircle,
                contentDescription = "Adicionar tarefa",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun TaskItem(
    task: TaskEntity,
    onToggle: (TaskEntity) -> Unit,
    onEdit: (TaskEntity) -> Unit,
    onRemove: (TaskEntity) -> Unit,
    onClick: (TaskEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(task) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.isCompleted,
                    colors = CheckboxDefaults.colors(
                        checkedColor = LightCyan
                    ),
                    onCheckedChange = {
                        onToggle(task)
                    }
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = task.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textDecoration = if (task.isCompleted) {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        }
                    ),
                    fontWeight = if (task.isCompleted) {
                        FontWeight.Normal
                    } else {
                        FontWeight.Bold
                    }
                )
            }

            if (!task.isCompleted) {
                IconButton(
                    onClick = { onEdit(task) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar tarefa"
                    )
                }
            }
            IconButton(
                onClick = { onRemove(task) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "remover tarefa"
                )
            }
        }
    }
}

@Composable
fun EditTaskDialog(
    task: TaskEntity,
    onDismiss: () -> Unit,
    onConfirm: (TaskEntity) -> Unit
) {

    var name by remember { mutableStateOf(task.name) }
    var description by remember { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Editar Tarefa")
        },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome") }
                )

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(
                            task.copy(
                                name = name,
                                description = description
                            )
                        )
                    }
                }
            ) {
                Text("Salvar", color = LightCyan)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar", color = Crimson)
            }
        }
    )
}

@Composable
fun RemoveTaskDialog(
    task: TaskEntity,
    onDismiss: () -> Unit,
    onConfirm: (TaskEntity) -> Unit,
) {
    var task by remember { mutableStateOf(task) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Remover Tarefa")
        },
        text = {
            Column {
                Text(text = task.name)
                Text(
                    text = task.description,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (task.name.isNotBlank()) {
                        onConfirm(task)
                    }
                }
            ) {
                Text("Remover", color = LightCyan)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar", color = Crimson)
            }
        }
    )
}