package br.com.pucpr.tasklistmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import br.com.pucpr.tasklistmanager.database.AppDatabase
import br.com.pucpr.tasklistmanager.model.repository.TaskRepository
import br.com.pucpr.tasklistmanager.navigation.AppNavigation
import br.com.pucpr.tasklistmanager.ui.theme.TaskListManagerTheme
import br.com.pucpr.tasklistmanager.view.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TaskListManagerTheme(
                dynamicColor = false
            ) {
                AppNavigation()
            }
        }
    }
}