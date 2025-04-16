package com.example.todolist.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    object ActiveScreen : Screen()

    @Serializable
    data class DoneScreen(val name : String) : Screen()

}