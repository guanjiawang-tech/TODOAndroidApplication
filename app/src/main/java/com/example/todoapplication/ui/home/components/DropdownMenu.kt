package com.example.todoapplication.ui.home.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable

@Composable
fun AppDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() }
    ) {
        options.forEach { item ->
            DropdownMenuItem(
                text = { Text(item) },
                onClick = {
                    onSelect(item)
                    onDismiss()
                }
            )
        }
    }
}