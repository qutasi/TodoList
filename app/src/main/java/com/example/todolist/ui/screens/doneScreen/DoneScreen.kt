package com.example.todolist.ui.screens.doneScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.todolist.data.converters.DateConverter
import com.example.todolist.data.models.Todo
import com.example.todolist.ui.components.TodoButton
import com.example.todolist.ui.components.TodoCard
import com.example.todolist.ui.navigation.Screen
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoneScreen(
    viewModel: DoneScreenViewModel,
    navController: NavController,
    name: String
) {

    val todos by viewModel.todos.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = name)
            })
        },

        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TodoButton(
                        buttonText = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home Screen"
                            )
                        }
                    ) {
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
            items(items = todos){ todo : Todo ->
                TodoCard(
                    todo = todo,
                    deadline = DateConverter().dateToText(todo.deadline),
                    onEdit = {
                        viewModel.setTodoToActive(todo)
                    },
                    onDelete = {
                        viewModel.deleteTodos(todo)
                    }
                ) {
                    viewModel.setTodoToActive(todo)
                }
            }
        }
    }
}