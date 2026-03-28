package br.com.pucpr.tasklistmanager.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.pucpr.tasklistmanager.ui.theme.DarkCyan
import br.com.pucpr.tasklistmanager.viewmodel.StaticsScreenViewModel

@Composable
fun StatisticsScreen(
    onBackPage: () -> Unit
) {
    val viewModel: StaticsScreenViewModel = hiltViewModel()

    val countFinishedTasks by viewModel.finishedTasksCount.collectAsState()
    val countNotFinishedTasks by viewModel.notFinishedTasksCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkCyan)
            .padding(vertical = 40.dp, horizontal = 16.dp)
    ) {
        StaticHeader(onBackPage)
        Spacer(modifier = Modifier.size(16.dp))
        StaticInfo(
            countFinishedTasks,
            countNotFinishedTasks
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticHeader(
    onBackPage: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackPage, colors = IconButtonDefaults.iconButtonColors(
            contentColor = Color.Black
        )) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Voltar página")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            "Estatísticas",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StaticInfo(
    countFinished: Int,
    countNotFinished: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row {
            Text(
               text = countFinished.toString(),
               style = MaterialTheme.typography.bodyLarge,
               fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                "tarefas finalizadas...",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row {
            Text(
                text = countNotFinished.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                "tarefas pendentes...",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}