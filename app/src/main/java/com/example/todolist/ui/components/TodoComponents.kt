package com.example.todolist.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todolist.data.converters.DateConverter
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
    value: String,
    onValueChanged: (String) -> Unit,
    label: @Composable () -> Unit
) {

    var text by remember { mutableStateOf(value) }
    val keyboard = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChanged(it)
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
fun TodoDialog (
    viewModel: ActiveScreenViewModel
) {
    val title by viewModel.title.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()
    val deadline by viewModel.deadline.collectAsStateWithLifecycle()
    val selectedTodo by viewModel.selectedTodo.collectAsStateWithLifecycle()

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
                    onValueChanged = {
                        viewModel.setTitle(it)
                    }
                ) {
                    TodoText(text = "Todo Title")
                }
                TodoInput(
                    value = description,
                    onValueChanged = {
                        viewModel.setDescription(it)
                    }
                ) {
                    TodoText(text = "Todo Description")
                }
                TodoButton(
                    buttonText = {
                        TodoText(
                            text = DateConverter().dateToText(deadline)
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
                        val todo = Todo(
                            todoId = selectedTodo?.todoId,
                            title = title,
                            description = description,
                            deadline = deadline
                        )
                        viewModel.upsertTodo(todo)
                        viewModel.hideDialog()
                        viewModel.reset()
                    }
                }
            }
        }
    }
}

@Composable
fun TodoCard(
    todo: Todo,
    deadline: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    setToCompleted: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 5.dp)
        ,
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                TodoText(
                    text = todo.title,
                    fontSize = 20,
                    fontWeight = FontWeight.Bold
                )
                TodoText(
                    text = todo.description
                )
                TodoText(
                    text = deadline
                )
            }
            ActionIcons(
                onEdit = onEdit,
                onDelete = onDelete
            ) {
                setToCompleted()
            }
        }
    }
}

@Composable
fun ActionIcons(
    onEdit : () -> Unit,
    onDelete : () -> Unit,
    setToCompleted : () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TodoButton(
            buttonText = {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "editButton")
            }
        ) {
            onEdit()
        }
        TodoButton(
            buttonText = {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "editButton")
            }
        ) {
            onDelete()
        }
        TodoButton(
            buttonText = {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Set To completed")
            }
        ) {
            setToCompleted()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDatePicker(
    dismiss: () -> Unit,
    selectDate: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState(

        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return System.currentTimeMillis() < utcTimeMillis
            }
        }
    )
    DatePickerDialog(
        onDismissRequest = dismiss,
        confirmButton = {
            TodoButton(
                buttonText = {
                    TodoText(text = "Select")
                }
            ) {
                datePickerState.selectedDateMillis?.let {
                    selectDate(it)
                }
                dismiss()
            }
        },
        dismissButton = {
            TodoButton(
                buttonText = {
                    TodoText(text = "Cancel")
                }
            ){
                dismiss()
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}













