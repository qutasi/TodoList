package com.example.todolist

import app.cash.turbine.test
import com.example.todolist.data.models.Todo
import com.example.todolist.data.repositories.TodoRepository
import com.example.todolist.ui.screens.doneScreen.DoneScreenViewModel
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class DoneScreenViewModelKotestTest {

    lateinit var viewModel : DoneScreenViewModel
    lateinit var todoRepository : TodoRepository
    val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        todoRepository = mockk()
        coEvery { todoRepository.getDoneTodos() } returns flowOf(emptyList())
        viewModel = DoneScreenViewModel(todoRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should collect done todos on init`() = runTest {
        val fakeTodo1 = Todo(
            todoId = 1,
            title = "title",
            description = "description",
            deadline = LocalDate.now(),
            isDone = true
        )
        val fakeTodo2 = Todo(
            todoId = 2,
            title = "Done 2",
            description = "Desc",
            deadline = LocalDate.now(),
            isDone = true
        )
        val doneTodos = listOf(fakeTodo1, fakeTodo2)
        coEvery { todoRepository.getDoneTodos() } returns flowOf(doneTodos)

        viewModel = DoneScreenViewModel(todoRepository)

        viewModel.todos.test {
            awaitItem() shouldBe doneTodos
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `should delete a done todo`() = runTest {
        val todo = Todo(
            title = "Trash Me",
            description = "Delete this",
            deadline = LocalDate.now(),
            isDone = true
        )

        coEvery { todoRepository.deleteTodo(todo) } just Runs

        viewModel.deleteTodos(todo)

        coVerify { todoRepository.deleteTodo(todo) }
    }

    @Test
    fun `should set a done todo back to active`() = runTest {
        val todo = Todo(
            title = "Bring Me Back",
            description = "Active again",
            deadline = LocalDate.now(),
            isDone = true
        )

        coEvery { todoRepository.setTodoActive(todo) } just Runs

        viewModel.setTodoToActive(todo)

        coVerify { todoRepository.setTodoActive(todo) }
    }

    @Test
    fun `should emit empty list if no done todos exist`() = runTest {
        coEvery { todoRepository.getDoneTodos() } returns flowOf(emptyList())

        viewModel = DoneScreenViewModel(todoRepository)

        viewModel.todos.test {
            awaitItem() shouldBe emptyList()
            cancelAndConsumeRemainingEvents()
        }
    }
}