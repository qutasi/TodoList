package com.example.todolist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todolist.ui.screens.activeScreen.ActiveScreen
import com.example.todolist.ui.screens.activeScreen.ActiveScreenViewModel
import com.example.todolist.ui.screens.doneScreen.DoneScreen
import com.example.todolist.ui.screens.doneScreen.DoneScreenViewModel

@Composable
fun TodoNavigation() {
    val navController = rememberNavController()
    val activeTodoViewModel : ActiveScreenViewModel = hiltViewModel()
    val doneTodoViewModel : DoneScreenViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.ActiveScreen
    ) {
        composable<Screen.ActiveScreen> {
            ActiveScreen(activeTodoViewModel, navController)
        }
        composable<Screen.DoneScreen> {
            val args = it.toRoute<Screen.DoneScreen>()
            DoneScreen(doneTodoViewModel, navController, args.name)
        }
    }
}