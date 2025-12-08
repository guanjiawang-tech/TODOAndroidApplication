package com.example.todoapplication.ui.home.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("提醒") },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("知道了")
            }
        }
    )
}