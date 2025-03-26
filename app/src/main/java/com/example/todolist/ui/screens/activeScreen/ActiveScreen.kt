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
import com.example.todolist.ui.components.TodoButton
import com.example.todolist.ui.components.TodoCard
import com.example.todolist.ui.components.TodoDialog

@Composable
fun ActiveScreen(
    viewModel: ActiveScreenViewModel,
) {
    val todos by viewModel.todos.collectAsStateWithLifecycle()
    val showDialog by viewModel.showTodoDialog.collectAsStateWithLifecycle()

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
                        //TODO navigate home
                    }
                    TodoButton(
                        buttonText = {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Completed Screen"
                            )
                        }
                    ) {
                        //TODO xyz
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
                    getDeadLine = { viewModel.getDeadLine() },
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
    }
}