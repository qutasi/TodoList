package com.example.todolist.data.repositories

import android.util.Log
import com.example.todolist.data.local.daos.TodoDao
import com.example.todolist.data.models.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {

    override fun getActiveTodos(): Flow<List<Todo>> {
        return todoDao.getActiveTodos().catch {
            Log.e("ActiveTodos", "getActiveTodos: ${it.message.toString()}")
            emit(emptyList())
        }
    }

    override fun getDoneTodos(): Flow<List<Todo>> {
        return todoDao.getDoneTodos().catch {
            Log.e("CompletedTodos", "getCompletedTodos: ${it.message.toString()}")
            emit(emptyList())
        }
    }

    override suspend fun upsertTodo(todo: Todo) {
        runCatching {
            todoDao.upsertTodo(todo)
        }.onFailure {
            Log.e("UpsertTodo", "upsertTodo: ${it.message.toString()}")
        }
    }

    override suspend fun setTodoActive(todo: Todo) {
        val updatedTodo = todo.copy(isDone = false)
        runCatching {
            todoDao.upsertTodo(updatedTodo)
        }.onFailure {
            Log.e("SetTodoActive", "setTodoActive: ${it.message.toString()}")
        }
    }

    override suspend fun setTodoCompleted(todo: Todo) {
        val updatedTodo = todo.copy(isDone = true)
        runCatching {
            todoDao.upsertTodo(updatedTodo)
        }.onFailure {
            Log.e("SetTodoCompleted", "setTodoCompleted: ${it.message.toString()}")
        }
    }

    override suspend fun deleteTodo(todo: Todo) {
        runCatching {
            todoDao.deleteTodo(todo)
        }.onFailure {
            Log.e("DeleteTodo", "deleteTodo: ${it.message.toString()}")
        }
    }

}