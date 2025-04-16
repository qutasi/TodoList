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
import java.time.ZoneId

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

    "should set description to description" {
        viewModel.setDescription("description")
        viewModel.description.test {
            awaitItem() shouldBe "description"
            cancelAndConsumeRemainingEvents()
        }
    }

    "should set deadline to deadline" {
        val dateNow = LocalDate.now()
        val timestamp = dateNow.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        viewModel.setDeadline(timestamp)
        viewModel.deadline.test {
            awaitItem() shouldBe dateNow
            cancelAndConsumeRemainingEvents()
        }
    }

    "should select todo and copy fields" {
        val todo = Todo(
            title = "copied title",
            description = "copied description",
            deadline = LocalDate.now()
        )
        viewModel.selectTodo(todo)

        viewModel.selectedTodo.value shouldBe todo
        viewModel.title.value shouldBe todo.title
        viewModel.description.value shouldBe todo.description
        viewModel.deadline.value shouldBe todo.deadline
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

    "should show and hide todo dialog" {
        viewModel.showDialog()
        viewModel.showTodoDialog.value shouldBe true
        viewModel.hideDialog()
        viewModel.showTodoDialog.value shouldBe false
    }

    "should show and hide date picker" {
        viewModel.showDatePicker()
        viewModel.showTodoDatePicker.value shouldBe true
        viewModel.hideDatePicker()
        viewModel.showTodoDatePicker.value shouldBe false
    }

    "should reset fields to default" {
        val todo = Todo(
            title = "title",
            description = "description",
            deadline = LocalDate.of(2023, 1, 1)
        )
        viewModel.selectTodo(todo)
        viewModel.reset()

        viewModel.selectedTodo.value shouldBe null
        viewModel.title.value shouldBe ""
        viewModel.description.value shouldBe ""
        viewModel.deadline.value shouldBe LocalDate.now()
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

    "should invoke repository setTodoCompleted" {
        val todo = Todo(
            title = "complete me title",
            description = "complete me description",
            deadline = LocalDate.now()
        )
        coEvery { todoRepository.setTodoCompleted(todo) } just Runs
        viewModel.setTodoCompleted(todo)
        coVerify { todoRepository.setTodoCompleted(todo) }
    }

    "should invoke repository deleteTodo" {
        val todo = Todo(
            title = "delete me title",
            description = "delete me description",
            deadline = LocalDate.now()
        )
        coEvery { todoRepository.deleteTodo(todo) } just Runs
        viewModel.deleteTodo(todo)
        coVerify { todoRepository.deleteTodo(todo) }
    }

})





