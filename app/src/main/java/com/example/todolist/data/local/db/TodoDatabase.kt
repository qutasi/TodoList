package com.example.todolist.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.data.converters.DateConverter
import com.example.todolist.data.local.daos.TodoDao
import com.example.todolist.data.models.Todo

@Database(
    entities = [Todo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao() : TodoDao
}