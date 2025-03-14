package com.example.todolist.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val description: String,
    val isDone: Boolean = false,
    val deadline: LocalDate,
    val createdAt: LocalDate = LocalDate.now()
)
