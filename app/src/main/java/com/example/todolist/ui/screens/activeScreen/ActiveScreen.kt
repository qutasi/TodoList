package com.example.todolist.ui.screens.activeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.todolist.data.converters.DateConverter
import com.example.todolist.ui.components.TodoButton
import com.example.todolist.ui.components.TodoCard
import com.example.todolist.ui.components.TodoDatePicker
import com.example.todolist.ui.components.TodoDialog
import com.example.todolist.ui.navigation.Screen

@Composable
fun ActiveScreen(
    viewModel: ActiveScreenViewModel,
    navController: NavController
) {
    val todos by viewModel.todos.collectAsStateWithLifecycle()
    val showDialog by viewModel.showTodoDialog.collectAsStateWithLifecycle()
    val showDatePicker by viewModel.showTodoDatePicker.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showDialog() }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            BottomAppBar {
                Row {
                    TodoButton(
                        buttonText = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null
                            )
                        }
                    ) {
                        //TODO navigate home:
                        navController.navigate(Screen.ActiveScreen)
                    }
                    TodoButton(
                        buttonText = {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Completed Screen"
                            )
                        }
                    ) {
                        //TODO navigate to done
                        navController.navigate(Screen.DoneScreen)
                    }
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(items = todos) { todo ->
                TodoCard(
                    todo = todo,
                    deadline = DateConverter().dateToText(todo.deadline),
                    onEdit = {
                        viewModel.selectTodo(todo)
                        viewModel.showDialog()
                    },
                    onDelete = { viewModel.deleteTodo(todo) }
                ) {
                    viewModel.setTodoCompleted(todo)
                }
            }
        }

        AnimatedVisibility(showDialog) {
            TodoDialog(viewModel)
        }

        if (showDatePicker) {
            TodoDatePicker(
                dismiss = {viewModel.hideDatePicker()}
            ) {
                viewModel.setDeadline(it)
            }
        }
    }
}