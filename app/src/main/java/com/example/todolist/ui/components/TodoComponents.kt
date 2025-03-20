package com.example.todolist.ui.components

import android.app.Dialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todolist.data.models.Todo
import com.example.todolist.ui.screens.activeScreen.ActiveScreenViewModel

@Composable
fun TodoText(
    text : String,
    fontSize : Int = 16,
    fontWeight : FontWeight = FontWeight.Normal,
    color: Color = Color.Black,
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize.sp,
            color = color,
            fontWeight = fontWeight
        )
    )
}

@Composable
fun TodoButton(
    buttonText : @Composable () -> Unit,
    onClick : () -> Unit,
) {
    TextButton(
        onClick = onClick,
    ) {
        buttonText()
    }
}

@Composable
fun TodoInput(
    value : String,
    onValueChange : (String) -> Unit,
    label : @Composable () -> Unit,
) {
    var text by remember { mutableStateOf(value) }
    val keyboard = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        label = label,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboard?.hide()
            }
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth(.7f)
    )
}

@Composable
fun TodoDialog(
    todo : Todo?,
    viewModel: ActiveScreenViewModel,
) {
    val title by viewModel.title.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()
    val deadLine by viewModel.deadline.collectAsStateWithLifecycle()

    Dialog(
        onDismissRequest = { viewModel.hideDialog() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TodoInput(
                    value = title,
                    onValueChange = { viewModel.setTitle(it) },
                ) {
                    TodoText(text ="Todo Title")
                }
                TodoInput(
                    value = description,
                    onValueChange = {
                        viewModel.setDescription(it)
                    }
                ) {
                    TodoText(text = "Todo Description")
                }
                TodoButton(
                    buttonText = {
                        TodoText(
                            text = viewModel.getDeadLine()
                        )
                    }
                ) {
                    viewModel.showDatePicker()
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TodoButton(
                        buttonText = {
                            TodoText(
                                text = "Cancel"
                            )
                        }
                    ) {
                        viewModel.reset()
                        viewModel.hideDialog()
                    }
                    TodoButton(
                        buttonText = {
                            TodoText(
                                text = "Save"
                            )
                        }
                    ) {
                        val upsertTodo = Todo(
                            id = todo?.id,
                            title = title,
                            description = description,
                            deadline = deadLine
                        )
                        viewModel.upsertTodo(upsertTodo)
                    }
                }
            }
        }
    }
}















