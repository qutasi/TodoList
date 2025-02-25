package com.example.todolist.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.todolist.data.models.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos WHERE isDone = 0 ORDER BY deadline ASC")
    fun getActiveTodos() : Flow<List<Todo>>

    @Query("SELECT * FROM todos WHERE isDone = 1")
    fun getDoneTodos() : Flow<List<Todo>>

    @Upsert
    fun upsertTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)
}