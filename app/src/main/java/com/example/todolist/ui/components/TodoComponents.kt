package com.example.todolist.ui.components

import android.app.Dialog
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
    viewModel: ActiveScreenViewModel,
) {
    val title by viewModel.title.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()
    val deadLine by viewModel.deadline.collectAsStateWithLifecycle()
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
                        if(title.isNotBlank() && description.isNotBlank()) {
                            val upsertTodo = Todo(
                                id = selectedTodo?.id,
                                title = title.trim(),
                                description = description.trim(),
                                deadline = deadLine
                            )
                            viewModel.upsertTodo(upsertTodo)
                            viewModel.reset()
                            viewModel.hideDialog()
                        } else {
                            error("failed to save todo")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodoCard(
    todo : Todo,
    getDeadLine : () -> String,
    onEdit : () -> Unit,
    onDelete : () -> Unit,
    setToCompleted : () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        shadowElevation = 5.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                TodoText(
                    text = todo.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20
                )
                TodoText(
                    text = todo.description
                )
                TodoText(
                    text = getDeadLine(),
                    color = Color.Gray
                )
            }
            ActionIcons(
                onEdit = onEdit,
                onDelete = onDelete,
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
        Button(
            onClick = { setToCompleted() },
            colors = ButtonDefaults.buttonColors(Color.Green),
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "complete todo")
        }
    }
}
















