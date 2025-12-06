package com.example.todoapplication.ui.home.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DeleteTodoDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    androidx.compose.material.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("删除确认") },
        text = { Text("确定要删除这个待办事项吗？") },
        confirmButton = {
            androidx.compose.material.TextButton(onClick = onConfirm) {
                Text("删除", color = Color.Red)
            }
        },
        dismissButton = {
            androidx.compose.material.TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}