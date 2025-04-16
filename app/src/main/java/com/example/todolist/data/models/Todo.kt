package com.example.todolist.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "todos")
data class Todo(
    val title: String,
    val description: String,
    val deadline: LocalDate,
    @PrimaryKey(autoGenerate = true)
    val todoId : Int? = null,
    val isDone: Boolean = false,
    val createdAt: LocalDate = LocalDate.now(),
)
