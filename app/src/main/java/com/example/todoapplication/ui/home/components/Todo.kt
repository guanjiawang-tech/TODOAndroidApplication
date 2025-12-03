package com.example.todoapplication.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoapplication.ui.theme.BlueNormal


@Composable
fun Todo(
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                clip = false
            )
            .background(Color.White)
    ) {
        Text(
            "Title",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}