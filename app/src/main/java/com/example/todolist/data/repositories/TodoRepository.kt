package com.example.todolist.data.repositories

import com.example.todolist.data.models.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getActiveTodos() : Flow<List<Todo>>
    fun getDoneTodos() : Flow<List<Todo>>

    suspend fun upsertTodo(todo: Todo)
    suspend fun setTodoActive(todo: Todo)
    suspend fun setTodoCompleted(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
}