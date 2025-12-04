/**
 * Components For Line
 * */
package com.example.todoapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Lines(
    thickness: Dp = 1.dp,
    length: Dp? = null,
    color: Color = Color.LightGray
) {
    Box(
        modifier = Modifier
            .then(
                if (length == null) Modifier.fillMaxWidth()
                else Modifier.width(length)
            )
            .height(thickness)
            .background(color)
    )
}