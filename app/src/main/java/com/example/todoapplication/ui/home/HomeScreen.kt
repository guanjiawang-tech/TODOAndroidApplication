/**
 * Home Page Screen
 * */
package com.example.todoapplication.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoapplication.ui.home.components.Todo
import com.example.todoapplication.ui.theme.BlueNormal
import com.example.todoapplication.ui.theme.LightBlue
import com.example.todoapplication.ui.theme.SkyBlue

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        // TODO: To Show Select Calendar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(BlueNormal),
            contentAlignment = Alignment.Center
        ) {
            Text("Select Calendar", color = Color.White)
        }

        // Main Part
        //  TODO: To Show TODO List
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f)
                .background(SkyBlue),
            contentAlignment = Alignment.Center
        ) {
            TodoList()
        }
    }
}

/**
 * Todo List
 * */
@Composable
fun TodoList() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        repeat(20) { index ->
            Todo()
        }
    }

}