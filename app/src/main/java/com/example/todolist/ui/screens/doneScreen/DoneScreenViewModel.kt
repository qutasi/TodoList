package com.example.todolist.ui.screens.doneScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.models.Todo
import com.example.todolist.data.repositories.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DoneScreenViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    init {
        getDoneTodos()
    }

    var todos = MutableStateFlow<List<Todo>>(emptyList())
        private set

    private fun getDoneTodos() {
        viewModelScope.launch {
            todoRepository.getDoneTodos()
                .distinctUntilChanged()
                .collect { todos.value = it }
        }
    }

    fun deleteTodos(todo : Todo) {
        viewModelScope.launch {
            todoRepository.deleteTodo(todo)
        }
    }

    fun setTodoToActive(todo : Todo) {
        viewModelScope.launch {
            todoRepository.setTodoActive(todo)
        }
    }

}