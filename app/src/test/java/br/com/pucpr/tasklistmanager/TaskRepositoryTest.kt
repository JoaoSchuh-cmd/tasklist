package br.com.pucpr.tasklistmanager

import br.com.pucpr.tasklistmanager.model.Task
import br.com.pucpr.tasklistmanager.model.dao.TaskDao
import br.com.pucpr.tasklistmanager.model.entity.TaskEntity
import br.com.pucpr.tasklistmanager.model.repository.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class TaskRepositoryTest {
    private lateinit var dao: TaskDao
    private lateinit var repository: TaskRepository

    @Before
    fun setup() {
        dao = mock()
        repository = TaskRepository(dao)
    }

    @Test
    fun `insert task calls dao insert`() = runTest {
        val task = TaskEntity(
            id = 1,
            name = "Teste",
            description = "Testando teste",
            isCompleted = true
        )

        repository.insert(task)

        verify(dao).insert(task)
    }

    @Test
    fun `update task calls dao update`() = runTest {
        val task = TaskEntity(
            id = 1,
            name = "Teste",
            description = "Testando teste teste",
            isCompleted = false
        )

        repository.update(task)

        verify(dao).update(task)
    }

    @Test
    fun `delete task calls dao delete`() = runTest {
        val task = TaskEntity(
            id = 1,
            name = "Teste",
            description = "Testando teste",
            isCompleted = true
        )

        repository.delete(task)

        verify(dao).delete(task)
    }

    @Test
    fun `getById returns flow with task`() = runTest {
        val task = TaskEntity(
            id = 1,
            name = "Teste",
            description = "Testando teste",
            isCompleted = true
        )

        whenever(dao.getTaskById(1)).thenReturn(flowOf(task))

        val resultFlow = repository.getById(1)
        val result = resultFlow.first()

        assertNotNull(result)
        assertEquals(task.id, result?.id)
        assertEquals(task.name, result?.name)
        assertEquals(task.description, result?.description)
    }

    @Test
    fun `getAll returns list of flow containing all tasks`() = runTest {
        val taskList = listOf(
            TaskEntity(1, "Teste 1", "Desc 1", true),
            TaskEntity(2, "Teste 2", "Desc 2", true),
            TaskEntity(3, "Teste 3", "Desc 3", true),
            TaskEntity(4, "Teste 4", "Desc 4", true)
        )

        whenever(dao.getAllTasks()).thenReturn(flowOf(taskList))

        val repository = TaskRepository(dao)

        val result = repository.tasks.first()

        assertNotNull(result)
        assertEquals(4, result.size)
        assertEquals("Teste 1", result[0].name)
    }

    @Test
    fun `getAllFinishedTasks returns list of flow containing all finished tasks`() = runTest {
        val taskList = listOf(
            TaskEntity(1, "Teste 1", "Desc 1", true),
            TaskEntity(2, "Teste 2", "Desc 2", true),
        )

        whenever(dao.getAllFinishedTasks()).thenReturn(flowOf(taskList))

        val repository = TaskRepository(dao)

        val result = repository.getAllFinishedTasks().first()

        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals(2, result[1].id)
    }

    @Test
    fun `getAllFinishedTasks returns list of flow containing all not finished tasks`() = runTest {
        val taskList = listOf(
            TaskEntity(1, "Teste 1", "Desc 1", false),
            TaskEntity(2, "Teste 2", "Desc 2", false),
        )

        whenever(dao.getAllNotFinishedTasks()).thenReturn(flowOf(taskList))

        val repository = TaskRepository(dao)

        val result = repository.getAllNotFinishedTasks().first()

        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals(2, result[1].id)
    }
}