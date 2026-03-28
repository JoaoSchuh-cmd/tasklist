package br.com.pucpr.tasklistmanager.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.pucpr.tasklistmanager.model.entity.TaskEntity
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.pucpr.tasklistmanager.ui.theme.Crimson
import br.com.pucpr.tasklistmanager.ui.theme.DarkCyan
import br.com.pucpr.tasklistmanager.ui.theme.LightCyan
import br.com.pucpr.tasklistmanager.viewmodel.TaskDetailViewModel

@Composable
fun TaskDetailScreen(
    onBackPage: () -> Unit,
    viewModel: TaskDetailViewModel = hiltViewModel()
) {
    val task by viewModel.task.collectAsState(initial = null)

    if (task == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Carregando tarefa...")
        }
        return
    }
    val currentTask = task!!
    var name by remember(currentTask) { mutableStateOf(currentTask.name) }
    var description by remember(currentTask) { mutableStateOf(currentTask.description) }

    var showEditDialog by remember { mutableStateOf(false) }
    var showRemoveDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkCyan)
            .padding(vertical = 40.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TaskDetailHeader(
            onBackPage = onBackPage
        )

        TaskInfo(
            name = name,
            description = description,
            onNameChange = { name = it },
            onDescriptionChange = { description = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        TaskDetailFooterButtons(
            currentTask,
            onRemove = { showRemoveDialog = true },
            onEdit = { showEditDialog = true }
        )

        if (showEditDialog) {
            EditDialog(
                onDismiss = { showEditDialog = false },
                onConfirm = {
                    viewModel.editTask(
                        currentTask.copy(
                            name = name,
                            description = description
                        )
                    )
                    showEditDialog = false
                    onBackPage()
                }
            )
        }

        if (showRemoveDialog) {
            RemoveDialog(
                onDismiss = { showRemoveDialog = false },
                onConfirm = {
                    viewModel.removeTask(task!!)
                    showRemoveDialog = false
                    onBackPage()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailHeader(
    onBackPage: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackPage) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Voltar página")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            "Detalhes da tarefa",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TaskInfo(
    name: String,
    description: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = onNameChange,
            label = { Text("Nome") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedIndicatorColor = Color.White,
                unfocusedTextColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black,
                selectionColors = TextSelectionColors(handleColor = Color.Black, backgroundColor = Color.Black)
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Descrição") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedIndicatorColor = Color.White,
                unfocusedTextColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black,
                selectionColors = TextSelectionColors(handleColor = Color.Black, backgroundColor = Color.Black)
            )
        )
    }
}

@Composable
fun TaskDetailFooterButtons(
    task: TaskEntity,
    onRemove: (TaskEntity) -> Unit,
    onEdit: (TaskEntity) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            onClick = { onRemove(task) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Crimson,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Deletar tarefa",
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    "Deletar",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
        OutlinedButton(
            onClick = { onEdit(task) },
            colors = ButtonDefaults.buttonColors(
                containerColor = LightCyan,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Editar tarefa",
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    "Editar",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }

        }
    }
}

@Composable
fun EditDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Editar Tarefa")
        },
        text = {
            Text(
                text = "Deseja editar essa tarefa com os dados informados?",
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun RemoveDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Remover Tarefa")
        },
        text = {
            Text(
                text = "Deseja remover essa tarefa?",
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text("Remover")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}