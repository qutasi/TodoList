package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.todolist.ui.navigation.TodoNavigation
import com.example.todolist.ui.screens.activeScreen.ActiveScreen
import com.example.todolist.ui.screens.activeScreen.ActiveScreenViewModel
import com.example.todolist.ui.theme.TodoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel : ActiveScreenViewModel by viewModels()
            TodoListTheme {
                TodoNavigation()
            }
        }
    }
}
