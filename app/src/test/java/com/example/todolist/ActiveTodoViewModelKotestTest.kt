package com.example.todolist

import app.cash.turbine.test
import com.example.todolist.data.models.Todo
import com.example.todolist.data.repositories.TodoRepository
import com.example.todolist.ui.screens.activeScreen.ActiveScreenViewModel
import io.kotest.core.spec.style.StringSpec
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
import kotlinx.coroutines.test.setMain
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class ActiveTodoViewModelKotestTest : StringSpec({

    lateinit var viewModel : ActiveScreenViewModel
    lateinit var todoRepository : TodoRepository
    val testDispatcher = StandardTestDispatcher()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
        todoRepository = mockk()
        viewModel = ActiveScreenViewModel(todoRepository)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "should set title to title" {
        viewModel.setTitle("title")
        viewModel.title.test {
            awaitItem() shouldBe "title"
            cancelAndConsumeRemainingEvents()
        }
    }

    "should invoke repository upsert" {
        val todo = Todo(
            title = "title",
            description = "description",
            deadline = LocalDate.now()
        )
        coEvery { todoRepository.upsertTodo(todo) } just Runs
        viewModel.upsertTodo(todo)
        coVerify { todoRepository.upsertTodo(todo) }
    }

    "should get all active todos" {
        val todo1 = Todo(
            todoId = 1,
            title = "title",
            description = "description",
            deadline = LocalDate.now(),
            isDone = false
        )
        val todo2 = Todo(
            todoId = 2,
            title = "title",
            description = "description",
            deadline = LocalDate.now(),
            isDone = false
        )
        val fakeTodos = listOf(todo1, todo2)
        coEvery { todoRepository.getActiveTodos() } returns flowOf(fakeTodos)
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.todos.test {
            awaitItem() shouldBe fakeTodos
            cancelAndConsumeRemainingEvents()
        }
    }

})