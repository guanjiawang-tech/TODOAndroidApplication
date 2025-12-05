package com.example.todoapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun EditTodoDialog(
    defaultText: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val textState = remember { mutableStateOf(defaultText) }

    androidx.compose.material.AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "编辑待办事项")
        },
        text = {
            androidx.compose.material.TextField(
                modifier = Modifier
                    .background(Color.White),
                value = textState.value,
                onValueChange = { textState.value = it }
            )
        },
        confirmButton = {
            androidx.compose.material.TextButton(onClick = { onConfirm(textState.value) }) {
                Text("确定")
            }
        },
        dismissButton = {
            androidx.compose.material.TextButton(onClick = { onDismiss() }) {
                Text("取消")
            }
        }
    )
}
