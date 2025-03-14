package com.example.todolist.data.di

import android.content.Context
import androidx.room.Room
import com.example.todolist.data.local.daos.TodoDao
import com.example.todolist.data.local.db.TodoDatabase
import com.example.todolist.data.repositories.TodoRepository
import com.example.todolist.data.repositories.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext context: Context) : TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(todoDatabase: TodoDatabase) = todoDatabase.todoDao()

    @Singleton
    @Provides
    fun provideTodoRepository(todoDao: TodoDao) : TodoRepository {
        return TodoRepositoryImpl(todoDao)
    }

}