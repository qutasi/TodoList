package com.example.todolist.ui.screens.activeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.converters.DateConverter
import com.example.todolist.data.models.Todo
import com.example.todolist.data.repositories.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ActiveScreenViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    init {
        getActiveTodos()
    }

    var todos = MutableStateFlow<List<Todo>>(emptyList())
        private set

    var title = MutableStateFlow("")
        private set

    var description = MutableStateFlow("")
        private set

    var deadline = MutableStateFlow(LocalDate.now())
        private set

    var selectedTodo = MutableStateFlow<Todo?>(null)
        private set

    var showTodoDialog = MutableStateFlow(false)
    private set

    var showTodoDatePicker = MutableStateFlow(false)
        private set

    fun setTitle(text : String) {
        title.value = text
    }

    fun setDescription(text : String) {
        description.value = text
    }

    fun setDeadline(timestamp : Long) {
        deadline.value = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    fun showDialog() {
        showTodoDialog.value = true
    }

    fun hideDialog() {
        showTodoDialog.value = false
    }

    fun getDeadLine() : String {
        return DateConverter().dateToText(deadline.value)
    }

    fun showDatePicker() {
        showTodoDatePicker.value = true
    }

    fun hideDatePicker() {
        showTodoDatePicker.value = false
    }

    private fun copyTodo() {
        selectedTodo.value?.let {
            setTitle(it.title)
            setDescription(it.description)
            deadline.value = it.deadline
        }
    }

    fun selectTodo(todo : Todo) {
        selectedTodo.value = todo
        copyTodo()
    }

    fun reset() {
        setTitle("")
        setDescription("")
        deadline.value = LocalDate.now()
        selectedTodo.value = null
    }

    private fun getActiveTodos() {
        viewModelScope.launch {
            todoRepository.getActiveTodos()
                .distinctUntilChanged()
                .collect { todos.value = it }
        }
    }

    fun upsertTodo(todo : Todo) {
        viewModelScope.launch {
            todoRepository.upsertTodo(todo)
        }
    }

    fun setTodoCompleted(todo : Todo) {
        viewModelScope.launch {
            todoRepository.setTodoCompleted(todo)
        }
    }

    fun deleteTodo(todo : Todo) {
        viewModelScope.launch {
            todoRepository.deleteTodo(todo)
        }
    }



}
